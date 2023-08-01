package com.qucoon.rubiesnigeria.views.fragment.internal.contact.extension

import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.utils.Constant


fun List<Friend>.convertFriendsToContacts():MutableList<Contactslist>{
    var mutableContactList = mutableListOf<Contactslist>()
    this.forEach{
        mutableContactList.add(Contactslist(name = it.userName, phonenumber = it.userId, isFriend = Constant.yes))
    }
    return mutableContactList
}