package com.incirkus.connect

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.incirkus.connect.DATA.Repository
import com.incirkus.connect.DATA.local.getDataBase
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDataBase(application)
    val repository = Repository(database)


    fun preloadItems(){
        viewModelScope.launch {
            repository.preload()
        }
    }
}
