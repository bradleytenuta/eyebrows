package com.ddairy.eyebrows.model;

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.ddairy.eyebrows.data.Eyebrow
import com.ddairy.eyebrows.util.InternalStorage

class ModelEyebrow : ViewModel() {

    var eyebrows = mutableStateListOf<Eyebrow>()
    private set // By specifying private set, we're restricting writes to this state object to a private setter only visible inside the ViewModel.

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

    fun removeEyebrow(context: Context, eyebrow: Eyebrow) {
        this.eyebrows.remove(eyebrow)
        updateInternalStorage(context)
    }

    fun updateEyebrow(context: Context, eyebrow: Eyebrow) {
        val index = eyebrows.indexOf(eyebrow)
        if (index == -1) {
            return
        }

        this.eyebrows.removeIf { eyebrowItem -> eyebrowItem.id == eyebrow.id }
        this.eyebrows.add(index, eyebrow)
        updateInternalStorage(context)
    }

    fun initialiseWithLocalEyebrows(localEyebrows: List<Eyebrow>) {
        this.eyebrows = localEyebrows.toMutableStateList()
    }

    private fun updateInternalStorage(context: Context) {
        // TODO: Make this run on another thread?
        InternalStorage.writeEyebrows(context, this.eyebrows)
    }
}