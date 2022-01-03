package com.ddairy.eyebrows.util.helper

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class FirebaseUtil {

    companion object {

        lateinit var firebaseAnalytics: FirebaseAnalytics

        /**
         * Obtain the FirebaseAnalytics instance.
         */
        fun initialiseAnalytics() {
            firebaseAnalytics = Firebase.analytics
        }
    }
}