package com.cheongmin.voicereader.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.Answer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_answer.view.*

class AnswerAdapter: BaseAdapter<Answer, AnswerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.layout_answer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(answer: Answer) = with(itemView) {
            //TODO: writer_id로부터 사용자 정보를 가져와야함
            //iv_user_profile
            //tv_user_name.text
            tv_location.text = "경기도 의정부시"

            tv_submit_date.text = answer.createdDate.toString()
            tv_content.text = answer.contents
        }
    }
}