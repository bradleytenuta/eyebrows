package com.ddairy.eyebrows.model;

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.util.storage.InternalStorage

/**
 * The model that holds information about the eyebrows within the application.
 */
class ModelEyebrow : ViewModel() {

    var eyebrows = mutableStateListOf<Eyebrow>()
    private set // By specifying private set, we're restricting writes to this state object to a private setter only visible inside the ViewModel.

    /**
     * Adds an eyebrow to the list. If the eyebrow already exists then its updated instead.
     */
    fun addEyebrow(context: Context, eyebrow: Eyebrow) {
        // If an eyebrow with the same id already exists, then update it.
        val index = this.eyebrows.indexOf(eyebrow)
        if (index == -1) {
            eyebrows.add(eyebrow)
            updateInternalStorage(context)
        } else {
            updateEyebrow(context, eyebrow)
        }
    }

    /**
     * Removes the eyebrow from the list.
     */
    fun removeEyebrow(context: Context, eyebrow: Eyebrow) {
        this.eyebrows.remove(eyebrow)
        updateInternalStorage(context)
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
        updateInternalStorage(context)
    }

    /**
     * This method is used to overwrite the current list of eyebrows with a new list.
     * This should only be used when the application is started.
     */
    fun initialiseWithLocalEyebrows(context: Context) {
        // TODO: Make this run on another thread?
        this.eyebrows = InternalStorage.readEyebrows(context).toMutableStateList()
    }

    /**
     * Writes the list of eyebrows it holds to internal storage.
     * This method should be called whenever the list in this model is updated.
     */
    private fun updateInternalStorage(context: Context) {
        // TODO: Make this run on another thread?
        InternalStorage.writeEyebrows(context, this.eyebrows)
    }
}