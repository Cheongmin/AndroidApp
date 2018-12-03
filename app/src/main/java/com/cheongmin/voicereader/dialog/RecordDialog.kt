package com.cheongmin.voicereader.dialog

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.dialog_record.*
import java.lang.Exception
import java.io.File
import android.content.DialogInterface
import java.nio.file.Files
import java.nio.file.Files.readAllBytes
import java.nio.file.Paths


class RecordDialog : BottomSheetDialogFragment() {

    lateinit var listener: RecordDialogListener
    lateinit var recorder: MediaRecorder
    lateinit var recordFile: File

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "ABBA", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
        } else {
            btn_record_action.setOnClickListener {
                if (btn_record_action.isChecked) {
                    startRecord()
                } else {
                    stopRecord()
                }
            }
        }
    }

    private fun startRecord(){
        Toast.makeText(context, "AA", Toast.LENGTH_SHORT).show()
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

        recordFile = File.createTempFile("temp", ".mp3", File(getFilePath()))
        recorder.setOutputFile(recordFile.absolutePath)

        try {
            Toast.makeText(context, "Start Record", Toast.LENGTH_SHORT).show()
            recorder.prepare()
            recorder.start()
        } catch (e: Exception) {
            Log.e("Recorder", "Exception : $e")
        }
    }

    private fun stopRecord(){
        Toast.makeText(context, "BB", Toast.LENGTH_SHORT).show()
        recorder.stop()
        recorder.release()

        Toast.makeText(context, "Stop Record", Toast.LENGTH_SHORT).show()
    }

    private fun getFilePath(): String {
        val filepath = Environment.getExternalStorageDirectory().path
        val file = File(filepath, "MediaRecorderSample")

        if (!file.exists())
            file.mkdirs()

        return file.absolutePath + "/" + "record.mp3"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startRecord()
            } else {
                //User denied Permission.
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}