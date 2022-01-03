package com.ddairy.eyebrows.util

import android.content.Context
import com.ddairy.eyebrows.data.Eyebrow
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader

class InternalStorage {

    companion object {

        private const val EYEBROWS_FILE_NAME: String = "eyebrows.json"

        fun writeEyebrows(context: Context, eyebrows: List<Eyebrow>) {
            try {
                val fileOutputStream: FileOutputStream =
                    context.openFileOutput(EYEBROWS_FILE_NAME, Context.MODE_PRIVATE)
                fileOutputStream.write(InstanceMapper.mapper.writeValueAsString(eyebrows).toByteArray())
            }catch (e: Exception){
                e.printStackTrace()
                // TODO: Add logging.
            }
        }

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