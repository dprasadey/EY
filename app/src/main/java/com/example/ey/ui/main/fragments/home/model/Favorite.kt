package com.example.ey.ui.main.fragments.home.model

class Favorite {

    private var title: String? = null
    private var description: String? = null
    private var imgIcon: String? = null

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getDescription(): String? {
        return description
    }

    fun setDescription(description: String?) {
        this.description = description
    }

    fun getImgIcon(): String? {
        return imgIcon
    }

    fun setImgIcon(imgIcon: String?) {
        this.imgIcon = imgIcon
    }
}