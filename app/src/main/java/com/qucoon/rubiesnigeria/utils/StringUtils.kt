package com.qucoon.rubiesnigeria.utils

import com.qucoon.rubiesnigeria.model.contacts.Contactslist


fun String.getInitials(): String {
    val regex = Regex("(^|\\s+)(\\w)")
    val matches = regex.findAll(this)
    val initials = matches.joinToString("") { it.groups[2]!!.value.uppercase() }
    return initials
}



 fun String.cleanString():String{
   return  this.replace("\\s".toRegex(), "")
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

