package com.cheongmin.voicereader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cheongmin.voicereader.dialog.RecordDialog
import com.cheongmin.voicereader.dialog.RecordDialogListener
import kotlinx.android.synthetic.main.activity_post_question.*
import kotlinx.android.synthetic.main.include_toolbar.*

class PostQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_question)

        setupActionBar()

        //TODO: dialog 결과(음성 파일, STT를 통해 인식된 내용)을 받아와야함
        btn_show_record_dialog.setOnClickListener {
            val dialog = RecordDialog()
            dialog.listener = object: RecordDialogListener {
                override fun onSuccessful() {
                }

                override fun onCancel() {
                }
            }

            dialog.show(supportFragmentManager, dialog.tag)
        }

        sound_player.subtitles = "안녕하세요\n자막입니다"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post_question, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.menu_item_post_question_post -> {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        supportActionBar?.run {
            title = "질문하기"
            setDisplayShowTitleEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
