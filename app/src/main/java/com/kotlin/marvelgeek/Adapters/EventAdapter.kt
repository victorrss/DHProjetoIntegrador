package com.kotlin.marvelgeek.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.EventC
import com.kotlin.marvelgeek.model.Event
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.ComicC
import com.squareup.picasso.Picasso

class EventAdapter(val listener: onClickListenerEvent) : RecyclerView.Adapter<EventAdapter.ViewHolderEvent>() {

    var listEvent = ArrayList<EventC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderEvent {
        return ViewHolderEvent(
            LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderEvent, position: Int) {
        var event: EventC = listEvent[position]

        Picasso.get().load("${event.thumbnail.path}.${event.thumbnail.extension}")
            .fit()
            .into(holder.eventIvImage)
        holder.eventTvTitle.text = event.title
    }

    override fun getItemCount(): Int = listEvent.size

    fun addListEvent(list: ArrayList<EventC>){
        listEvent = list
        notifyDataSetChanged()
    }

    interface onClickListenerEvent {
        fun onClickEvent(position: Int)
    }

    inner class ViewHolderEvent(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var eventIvImage: ImageView = itemView.findViewById(R.id.eventIvImage)
        var eventTvTitle: TextView = itemView.findViewById(R.id.eventTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickEvent(position)
            }
        }
    }
}