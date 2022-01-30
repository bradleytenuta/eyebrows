package com.ddairy.eyebrows.util.helper

import android.content.Context
import java.util.Locale

class LocaleHelper {
    companion object {
        // the method is used to set the language at runtime
        fun setLocale(context: Context, language: String) {
            return updateResources(context, language)
        }

        // the method is used update the language of application by creating
        // object of inbuilt Locale class and passing language argument to it
        private fun updateResources(context: Context, language: String) {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
            // TODO: Find a way to make this rebuild compose, it works but only after changing page.
        }
    }
}