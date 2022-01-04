package com.ddairy.eyebrows.util.storage

import android.content.Context
import android.util.Log
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.data.Preferences
import com.ddairy.eyebrows.util.InstanceMapper
import com.ddairy.eyebrows.util.tag.ErrorTag
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader

class InternalStorage {

    companion object {

        /**
         * File names.
         */
        private const val EYEBROWS_FILE_NAME: String = "eyebrows.json"
        private const val PREFERENCES_FILE_NAME: String = "preferences.json"


        fun writeEyebrows(context: Context, eyebrows: List<Eyebrow>) {
            writeToFile(context, EYEBROWS_FILE_NAME, eyebrows)
        }

        fun writePreferences(context: Context, preferences: Preferences) {
            writeToFile(context, PREFERENCES_FILE_NAME, preferences)
        }

        /**
         * Writes the object to the specified file.
         * This overwrites what already exists within the file.
         */
        private fun <T> writeToFile(context: Context, fileName: String, t: T) {
            try {
                val fileOutputStream: FileOutputStream =
                    context.openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutputStream.write(InstanceMapper.mapper.writeValueAsString(t).toByteArray())
            }catch (e: Exception){
                Log.e(ErrorTag.FileWriteFailure.name, "Failed to write to file: $fileName")
            }
        }

        /**
         * Reads in a list of eyebrows from json into objects.
         * If it fails to read the file for any reason,
         * then an empty list of eyebrow objects is returned.
         */
        fun readEyebrows(context: Context): List<Eyebrow> {
            return try {
                val fileInputStream: FileInputStream = context.openFileInput(EYEBROWS_FILE_NAME)
                    ?: throw FileNotFoundException()

                val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
                val json = bufferedReader.readText()
                bufferedReader.close()

                InstanceMapper.mapper.readValue(json)
            } catch (e: Exception) {
                emptyList()
            }
        }

        /**
         * Reads in a Preference Object from json.
         * If it fails to read the file for any reason,
         * then a new object is returned.
         */
        fun readPreferences(context: Context): Preferences {
            return try {
                val fileInputStream: FileInputStream = context.openFileInput(PREFERENCES_FILE_NAME)
                    ?: throw FileNotFoundException()

                val bufferedReader = BufferedReader(InputStreamReader(fileInputStream))
                val json = bufferedReader.readText()
                bufferedReader.close()

                InstanceMapper.mapper.readValue(json)
            } catch (e: Exception) {
                Preferences()
            }
        }
    }
}