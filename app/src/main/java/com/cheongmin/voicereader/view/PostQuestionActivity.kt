package com.cheongmin.voicereader.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.QuestionAPI
import com.cheongmin.voicereader.view.dialog.RecordDialog
import com.cheongmin.voicereader.view.dialog.RecordDialogListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_post_question.*
import kotlinx.android.synthetic.main.include_toolbar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PostQuestionActivity : AppCompatActivity() {
  private val requestRecordAduioCode = 200

  private var recordFileName: String? = null
  private val compositeDisposable = CompositeDisposable()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_post_question)

    setupActionBar()
    setupRecord()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_post_question, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.menu_item_post_question_post -> {
        handleSubmit()
        return true
      }
    }

    return super.onOptionsItemSelected(item)
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    val permissionToRecordAccepted = if (requestCode == requestRecordAduioCode) {
      grantResults[0] == PackageManager.PERMISSION_GRANTED
    } else {
      false
    }

    if(!permissionToRecordAccepted) {
      Toast.makeText(applicationContext, "녹음 권한을 허용하지 않으면 음성을 녹음 할 수 없습니다.", Toast.LENGTH_LONG).show()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    compositeDisposable.dispose()
  }

  private fun setupActionBar() {
    setSupportActionBar(toolbar)

    supportActionBar?.run {
      title = "질문하기"
      setDisplayShowTitleEnabled(true)
      setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun setupRecord() {
    btn_show_record_dialog.setOnClickListener {
      val hasPermission = ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
      if (!hasPermission) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), requestRecordAduioCode)
        return@setOnClickListener
      }

      showRecordDialog()
    }
  }

  private fun showRecordDialog() {
    val argument = Bundle()
    argument.putString("path", externalCacheDir.absolutePath)

    val dialog = RecordDialog()
    dialog.arguments = argument
    dialog.listener = object : RecordDialogListener {
      override fun onSuccessful(fileName: String) {
        recordFileName = fileName
      }
      override fun onCancel() {
      }
    }

    dialog.show(supportFragmentManager, dialog.tag)
  }

  private fun handleSubmit() {
    val title = edit_title.text.toString()
    val contents = edit_content.text.toString()
    val subtitles = "입력되지 않은 자막"

    if(title.isBlank() || contents.isBlank()) {
      Toast.makeText(applicationContext, "제목과 내용을 입력해주세요.", Toast.LENGTH_LONG).show()
      return
    }

    if(recordFileName.isNullOrBlank()) {
      Toast.makeText(applicationContext, "음성을 녹음해주세요.", Toast.LENGTH_LONG).show()
      return
    }

    val file = File(recordFileName)

    compositeDisposable.add(QuestionAPI.newQuestion(
      MultipartBody.Part.createFormData(
        "sound",
        file.name,
        RequestBody.create(
          MediaType.parse("audio/mp3"),
          file
        )
      ),
      MultipartBody.Part.createFormData("title", title),
      MultipartBody.Part.createFormData("contents", contents),
      MultipartBody.Part.createFormData("subtitles", subtitles)
    ).subscribe({
      val intent = Intent(applicationContext, QuestionActivity::class.java)
      intent.putExtra("dataSource", it)
      startActivity(intent)
      finish()
    }, {
      throw it
    }))
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
