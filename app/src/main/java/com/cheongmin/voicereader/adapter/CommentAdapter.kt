package com.cheongmin.voicereader.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.model.Comment
import com.cheongmin.voicereader.utils.DateUtils
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class CommentAdapter : BaseAdapter<Comment, CommentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.layout_answer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.apply {
            holder.bind(this)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(comment: Comment) {
            itemView.findViewById<TextView>(R.id.tv_user_name).text = comment.author.name
            itemView.findViewById<TextView>(R.id.tv_location).text = comment.author.location
            itemView.findViewById<TextView>(R.id.tv_submit_date).text = DateUtils.toDayText(comment.date)

            itemView.findViewById<TextView>(R.id.tv_content).text = comment.content

            Picasso.get().load(comment.author.profileUri)
                    .placeholder(R.drawable.ic_profile_placeholder)
                    .into(itemView.findViewById<CircleImageView>(R.id.iv_user_profile))
        }
    }
}