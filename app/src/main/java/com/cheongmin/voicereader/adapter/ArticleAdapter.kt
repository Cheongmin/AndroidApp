package com.cheongmin.voicereader.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.Article

class ArticleAdapter(
        private val clickListener: (article: Article)->Unit)
    : BaseAdapter<Article, ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_question, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.apply {
            holder.bind(this, clickListener)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(article: Article, clickListener: (article: Article)->Unit) {
            itemView.findViewById<TextView>(R.id.tv_question_title).text = article.title
            itemView.findViewById<TextView>(R.id.tv_question_description).text = article.author.name
            itemView.setOnClickListener {
                clickListener(article)
            }
        }
    }
}