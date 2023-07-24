package com.qucoon.rubiesnigeria.utils

import com.qucoon.rubiesnigeria.model.contacts.Contactslist


fun String.getInitials(): String {
    val sb = StringBuilder()
    if(this.contains(" ")){
        val mylist= this.split(" ")
        for(i in mylist){
            sb.append( i.get(0).uppercaseChar())
        }
    }else{
        sb.append( this.get(0).uppercaseChar())
    }
    return sb.toString()
}


fun cleanContact(list: List<Contactslist>): MutableList<Contactslist> {
    val filteredList = mutableListOf<Contactslist>()
    val myset = listOf("#", "*")
    for (i in list.indices) {
        for (j in myset.indices) {
            if (!list[i].phonenumber.contains(myset.get(j)) && list[i].phonenumber.length > 8) {
                filteredList.add(list[i])
            }
        }
    }
    return filteredList
}

