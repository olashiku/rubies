package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qucoon.rubiesnigeria.base.BaseScarletSocketViewModel
import com.qucoon.rubiesnigeria.repository.socket.ScarletSocketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScarletSocketViewModel(val scarletSocketRepository: ScarletSocketRepository): BaseScarletSocketViewModel() {

    fun openConnection(){
        viewModelScope.launch(Dispatchers.IO) {
            scarletSocketRepository.startSocket()
        }
    }

     fun checkSocketConnection():Boolean{
     return scarletSocketRepository.isSocketAlive()
     }

}