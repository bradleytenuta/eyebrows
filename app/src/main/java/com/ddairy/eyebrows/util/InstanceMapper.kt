package com.ddairy.eyebrows.util

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule

class InstanceMapper {
    companion object {

        /**
         * A json mapper that works with Kotlin and can also handle date objects.
         */
        val mapper = jsonMapper {
            addModule(kotlinModule())
            addModule(JavaTimeModule())
            addModule(JodaModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }
    }
}