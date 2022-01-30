package com.ddairy.eyebrows.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ddairy.eyebrows.R
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.util.helper.EyebrowUtil
import com.ddairy.eyebrows.util.helper.FirebaseUtil
import com.ddairy.eyebrows.util.helper.LocaleHelper
import com.ddairy.eyebrows.util.notification.EyebrowBroadcastReceiver
import com.ddairy.eyebrows.util.notification.NotificationConstants
import com.ddairy.eyebrows.util.storage.InternalStorage
import com.ddairy.eyebrows.util.tag.AnalyticsEventName
import com.ddairy.eyebrows.util.tag.AnalyticsParamName
import com.ddairy.eyebrows.util.tag.HomeTab
import com.google.firebase.analytics.ktx.logEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import java.util.*

/**
 * The model that holds information about the eyebrows within the application.
 */
class EyebrowModel : ViewModel() {

    var eyebrows = mutableStateListOf<Eyebrow>()
        private set

    var selectedHomeTab by mutableStateOf(HomeTab.Open)
        private set

    var preferences by mutableStateOf(Preferences())
        private set

    /**
     * Adds an eyebrow to the list. If the eyebrow already exists then its updated instead.
     */
    fun addEyebrow(context: Context, eyebrow: Eyebrow) {
        // If an eyebrow with the same id already exists, then update it.
        val index = this.eyebrows.indexOf(eyebrow)
        if (index == -1) {
            eyebrows.add(eyebrow)
            postModelChange(
                context = context,
                eyebrow = eyebrow,
                analyticsMessage = AnalyticsEventName.EYEBROW_CREATED,
                toastMessage = context.resources.getString(R.string.eyebrow_toast_created)
            )
            addEyebrowNotification(context, eyebrow)
        } else {
            updateEyebrow(context, eyebrow)
        }
    }

    /**
     * Removes the eyebrow from the list.
     */
    fun removeEyebrow(context: Context, eyebrow: Eyebrow) {
        this.eyebrows.remove(eyebrow)
        postModelChange(
            context = context,
            eyebrow = eyebrow,
            analyticsMessage = AnalyticsEventName.EYEBROW_DELETED,
            toastMessage = context.resources.getString(R.string.eyebrow_toast_deleted)
        )
        removeEyebrowNotification(context, eyebrow)
    }

    /**
     * Updates the eyebrow in the list if it can be found, if eyebrow cannot be found then it does nothing.
     */
    fun updateEyebrow(context: Context, eyebrow: Eyebrow) {
        val index = eyebrows.indexOf(eyebrow)
        if (index == -1) {
            return
        }
        this.eyebrows.removeIf { eyebrowItem -> eyebrowItem.id == eyebrow.id }
        this.eyebrows.add(index, eyebrow)
        postModelChange(
            context = context,
            eyebrow = eyebrow,
            analyticsMessage = AnalyticsEventName.EYEBROW_UPDATED,
            toastMessage = context.resources.getString(R.string.eyebrow_toast_updated)
        )
        addEyebrowNotification(context, eyebrow)
    }

    /**
     * This method is used to overwrite the current list of eyebrows with a new list.
     * This should only be used when the application is started.
     * Calling this method instead of adding the eyebrows one by one will prevent extra analytics
     * events being created and ruining the validity of the data.
     */
    fun initialiseEyebrowsWithStorage(context: Context) {
        eyebrows = InternalStorage.readEyebrows(context).toMutableStateList()
    }

    /**
     * Loads in the preferences object.
     */
    fun initialisePreferencesWithStorage(context: Context) {
        preferences = InternalStorage.readPreferences(context)
        LocaleHelper.setLocale(context, preferences.localeCode)
    }

    /**
     * Updates the currently selected tab.
     */
    fun updateSelectedHomeTab(homeTab: HomeTab) {
        selectedHomeTab = homeTab
    }

    /**
     * Updates the preferences object.
     */
    fun updatePreferences(context: Context, preferences: Preferences) {
        this.preferences = preferences
        InternalStorage.writePreferences(context, preferences)
        LocaleHelper.setLocale(context, preferences.localeCode)
    }

    /**
     * This method runs all the required functions after the eyebrow model has changed in anyway.
     */
    private fun postModelChange(
        context: Context,
        eyebrow: Eyebrow,
        analyticsMessage: AnalyticsEventName,
        toastMessage: String
    ) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        updateInternalStorage(context)
        logEyebrowAnalytics(analyticsMessage, eyebrow)
    }

    /**
     * Writes the list of eyebrows it holds to internal storage.
     * This method should be called whenever the list in this model is updated.
     */
    private fun updateInternalStorage(context: Context) {
        GlobalScope.launch {
            InternalStorage.writeEyebrows(context, eyebrows)
        }
    }

    /**
     * Logs the event of the eyebrow event in google analytics.
     */
    private fun logEyebrowAnalytics(analyticsEventName: AnalyticsEventName, eyebrow: Eyebrow) {
        if (FirebaseUtil.firebaseAnalytics != null) {
            FirebaseUtil.firebaseAnalytics!!.logEvent(analyticsEventName.eventName) {
                if (analyticsEventName == AnalyticsEventName.EYEBROW_CREATED || analyticsEventName == AnalyticsEventName.EYEBROW_UPDATED) {
                    param(
                        AnalyticsParamName.NUMBER_OF_PARTICIPANTS.paramName,
                        eyebrow.participants.size.toString()
                    )
                }
            }
        }
    }

    /**
     * Adds or updates a notification that is scheduled to go off when the deadline for this eyebrow is reached.
     */
    private fun addEyebrowNotification(context: Context, eyebrow: Eyebrow) {
        // If the date is in the past, or its complete then we don't want to create a notification.
        if (!EyebrowUtil.isDateValid(LocalDate.now(), eyebrow.endDate) || Eyebrow.Status.Complete == eyebrow.status) {
            return
        }

        val pendingIntent = createPendingIntent(context, eyebrow)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Sets the exact time the notification should be sent.
        val calendar: Calendar = Calendar.getInstance()
        val dayAfterEndDate = eyebrow.endDate.plusDays(1)

        calendar.set(Calendar.YEAR, dayAfterEndDate.year) // Year value stays the same.
        calendar.set(Calendar.MONTH, dayAfterEndDate.monthOfYear - 1) // First month is 0 (Jan)
        calendar.set(Calendar.DAY_OF_MONTH, dayAfterEndDate.dayOfMonth) // First day of month is 1.

        // If the trigger time in milliseconds is in the past, then notification appears straight away.
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    /**
     * Removes an eyebrow notification. Should be used if the eyebrow is deleted.
     */
    private fun removeEyebrowNotification(context: Context, eyebrow: Eyebrow) {
        val pendingIntent = createPendingIntent(context, eyebrow)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    /**
     * Creates the Pending Intent for this eyebrow. Each eyebrow will have its own pending intent object.
     */
    private fun createPendingIntent(context: Context, eyebrow: Eyebrow): PendingIntent {
        val intent = Intent(context, EyebrowBroadcastReceiver::class.java)

        // Adds values about the eyebrow to the intent.
        val bundle = Bundle()
        bundle.putString(NotificationConstants.eyebrowDescriptionKey, eyebrow.description);
        intent.putExtras(bundle)

        // Pending Intent is reused if resource code is the same, otherwise a new pending intent is created.
        return PendingIntent.getBroadcast(context, eyebrow.id.hashCode(), intent, PendingIntent.FLAG_MUTABLE)
    }
}