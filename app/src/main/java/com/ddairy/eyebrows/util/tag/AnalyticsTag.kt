package com.ddairy.eyebrows.util.tag

// Used to identity the event is custom and not generic from Google Analytics.
const val applicationTag = "custom"

// Used to identity the event area.
const val eyebrowTag = "eyebrow"

enum class AnalyticsEventName(val eventName: String) {
    EYEBROW_CREATED("${applicationTag}_${eyebrowTag}_created"),
    EYEBROW_UPDATED("${applicationTag}_${eyebrowTag}_updated"),
    EYEBROW_DELETED("${applicationTag}_${eyebrowTag}_deleted");
}

enum class AnalyticsParamName(val paramName: String) {
    NUMBER_OF_PARTICIPANTS("${applicationTag}_${eyebrowTag}_number_of_participants");
}