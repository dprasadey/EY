package com.example.ey.ui.main.fragments.home.adapter


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ey.R
import com.example.ey.ui.main.fragments.home.model.Favorite


class FavAdapter(var context: Activity, userArrayList: ArrayList<Favorite>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userArrayList: ArrayList<Favorite>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rootView: View = LayoutInflater.from(context).inflate(R.layout.row_fav_adapter, parent, false)
        return RecyclerViewViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user: Favorite = userArrayList[position]
        val viewHolder = holder as RecyclerViewViewHolder
        viewHolder.txtView_title.setText(user.getTitle())
        viewHolder.txtView_description.setText(user.getDescription())
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }

    internal inner class RecyclerViewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imgView_icon: ImageView
        var txtView_title: TextView
        var txtView_description: TextView

        init {
            imgView_icon = itemView.findViewById(R.id.imgView_icon)
            txtView_title = itemView.findViewById(R.id.txtView_title)
            txtView_description = itemView.findViewById(R.id.txtView_description)
        }
    }

    init {
        this.userArrayList = userArrayList
    }
}