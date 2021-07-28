package com.example.ey.ui.main.fragments.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ey.ui.main.fragments.service.model.ServiceList

class ServiceViewModel : ViewModel() {
    var userLiveData: MutableLiveData<ArrayList<ServiceList>?>
    var userArrayList: ArrayList<ServiceList>? = null
    val userMutableLiveData: MutableLiveData<ArrayList<ServiceList>?>
        get() = userLiveData

    fun init() {
        populateList()
        userLiveData.value = userArrayList
    }

    fun populateList() {
        val user = ServiceList()
        user.setTitle("Oxford")
        user.setDescription("Best rating university")
        userArrayList = ArrayList<ServiceList>()
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
        userArrayList!!.add(user)
    }

    init {
        userLiveData = MutableLiveData<ArrayList<ServiceList>?>()
        init()
    }
}