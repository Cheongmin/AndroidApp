package com.cheongmin.voicereader

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cheongmin.voicereader.Adapters.LectureAdapter
import com.cheongmin.voicereader.Models.Lecture
import kotlinx.android.synthetic.main.fragment_main_home.*

class MainHomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = mutableListOf<Lecture>()
        for (i in 1..10) {
            items.add(Lecture(i, "강의" + i, "설명" + i, listOf()))
        }

        lectures_list.setHasFixedSize(true)
        lectures_list.adapter = LectureAdapter(items) {
            Toast.makeText(context, it.title, Toast.LENGTH_SHORT).show()
        }
        lectures_list.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        @JvmStatic fun getInstance(): MainHomeFragment = MainHomeFragment()
    }
}
