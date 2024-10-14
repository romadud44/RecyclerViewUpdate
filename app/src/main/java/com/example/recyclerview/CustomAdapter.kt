package com.example.recyclerview

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val things: MutableList<Thing>) :
    RecyclerView.Adapter<CustomAdapter.ThingViewHolder>() {

    private var onThingClickListener: OnThingClickListener? = null

    interface OnThingClickListener {
        fun onThingClick(thing: Thing, position: Int)
    }

    class ThingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameTV: TextView = itemView.findViewById(R.id.nameTV)

        val imageIV: ImageView = itemView.findViewById(R.id.imageViewIV)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThingViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ThingViewHolder(itemView)
    }

    override fun getItemCount(): Int = things.size

    override fun onBindViewHolder(holder: ThingViewHolder, position: Int) {
        val thing = things[position]
        holder.nameTV.text = thing.name
        val path = "android.resource://com.example.recyclerview/"
        val uri = Uri.parse(path + thing.image)
        holder.imageIV.setImageURI(uri)
        holder.itemView.setOnClickListener{
            if(onThingClickListener != null) {
                onThingClickListener!!.onThingClick(thing, position)
            }
        }
    }
    fun setOnThingClickListener(onThingClickListener: OnThingClickListener){
        this.onThingClickListener = onThingClickListener
    }
}