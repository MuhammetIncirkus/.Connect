package com.incirkus.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.incirkus.connect.DATA.Repository

class ViewModel(application: Application) : AndroidViewModel(application) {

    val repository = Repository()
}