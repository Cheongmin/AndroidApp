package com.cheongmin.voicereader.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheongmin.voicereader.Models.Lecture
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.item_lecture.view.*

class LectureAdapter(val items: MutableList<Lecture> = arrayListOf(), val onClick: (Lecture) -> Unit) : RecyclerView.Adapter<LectureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_lecture, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
        = holder.bind(items[position], onClick)

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(lecture: Lecture, onClick: (Lecture) -> Unit) = with(itemView) {
            title.text = lecture.title
            description.text = lecture.description

            card_view.setOnClickListener {
                onClick(lecture)
            }
        }
    }
}