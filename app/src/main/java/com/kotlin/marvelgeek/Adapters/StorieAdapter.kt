package com.kotlin.marvelgeek.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.marvelgeek.Entities.StoryC
import com.kotlin.marvelgeek.model.Storie
import com.kotlin.marvelgeek.R
import com.kotlin.marvelgeek.models.Character
import com.squareup.picasso.Picasso

class StorieAdapter(
    val listener: onClickListenerStorie
) : RecyclerView.Adapter<StorieAdapter.ViewHolderStorie>() {

    var listStory = ArrayList<StoryC>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolderStorie {
        return ViewHolderStorie(
            LayoutInflater.from(parent.context).inflate(R.layout.item_stories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderStorie, position: Int) {
        var story: StoryC = listStory[position]

        Picasso.get().load("${story.thumbnail.path}.${story.thumbnail.extension}")
            .fit()
            .into(holder.storyIvImage)
        holder.storyTvTitle.text = story.title
    }

    override fun getItemCount(): Int = listStory.size

    fun addStoryList(list: ArrayList<StoryC>){
        listStory.addAll(list)
        notifyAdapter()
    }

    fun notifyAdapter(){
        notifyDataSetChanged()
    }

    interface onClickListenerStorie {
        fun onClickStorie(position: Int)
    }

    inner class ViewHolderStorie(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var storyIvImage: ImageView = itemView.findViewById(R.id.storieIvImage)
        var storyTvTitle: TextView = itemView.findViewById(R.id.storieTvTitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition

            if (position != RecyclerView.NO_POSITION) {
                listener.onClickStorie(position)
            }
        }
    }
}