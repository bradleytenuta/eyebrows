package com.ddairy.eyebrows.util.storage

import android.content.Context
import android.util.Log
import com.ddairy.eyebrows.data.Eyebrow
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

        /**
         * Writes the list of Eyebrows to a json file.
         * This overwrites what already exists within the file.
         */
        fun writeEyebrows(context: Context, eyebrows: List<Eyebrow>) {
            try {
                val fileOutputStream: FileOutputStream =
                    context.openFileOutput(EYEBROWS_FILE_NAME, Context.MODE_PRIVATE)
                fileOutputStream.write(InstanceMapper.mapper.writeValueAsString(eyebrows).toByteArray())
            }catch (e: Exception){
                Log.e(ErrorTag.FileWriteFailure.name, "Failed to write Eyebrows to file: $EYEBROWS_FILE_NAME")
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
    }
}