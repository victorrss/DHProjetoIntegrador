package com.kotlin.marvelgeek.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.R

class EventAdapter(
    private val eventList: ArrayList<Event>,
    val listener: onClickListenerEvent
) : RecyclerView.Adapter<EventAdapter.ViewHolderEvent>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventAdapter.ViewHolderEvent {
        return ViewHolderEvent(
            LayoutInflater.from(parent.context).inflate(R.layout.item_events, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolderEvent, position: Int) {
        var event: Event = eventList[position]

        holder.eventIvImage.setImageResource(event.image)
        holder.eventTvTitle.text = event.title
    }

    override fun getItemCount(): Int = eventList.size

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