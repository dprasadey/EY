package com.example.ey.ui.main.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ey.ui.main.fragments.home.model.Favorite


class MainViewModel : ViewModel() {
    var userLiveData: MutableLiveData<ArrayList<Favorite>?>
    var userArrayList: ArrayList<Favorite>? = null
    val userMutableLiveData: MutableLiveData<ArrayList<Favorite>?>
        get() = userLiveData

    fun init() {
        populateList()
        userLiveData.value = userArrayList
    }
    fun populateList() {
        val user = Favorite()
        user.setTitle("Vitamin E-Commerce")
        user.setDescription("Pending Approval")
        userArrayList = ArrayList<Favorite>()
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
    }
    init {
        userLiveData = MutableLiveData<ArrayList<Favorite>?>()
        init()
    }
}