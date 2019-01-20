package com.cheongmin.voicereader.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.response.Question
import kotlinx.android.synthetic.main.layout_question.view.*

class QuestionAdapter : BaseAdapter<Question, QuestionAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.layout_question, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = getItem(position)
    holder.bind(item!!)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(question: Question) = with(itemView) {
      //TODO: Fix to contents to title
      tv_question_title.text = question.contents
      tv_question_user_name.text = question.writerId
    }
  }
}