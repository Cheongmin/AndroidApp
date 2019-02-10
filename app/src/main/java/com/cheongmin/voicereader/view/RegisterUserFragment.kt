package com.cheongmin.voicereader.view


import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.text.BoringLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.androidhuman.rxfirebase2.auth.rxCreateUserWithEmailAndPassword
import com.androidhuman.rxfirebase2.auth.rxGetIdToken
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.model.request.UserRequest
import com.cheongmin.voicereader.model.response.AccessToken
import com.cheongmin.voicereader.model.response.User
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.UserManager
import com.cheongmin.voicereader.network.client.ApiClient
import com.cheongmin.voicereader.utils.toFile
import com.cheongmin.voicereader.utils.validate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register_user.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.regex.Pattern


class RegisterUserFragment : Fragment() {
  private val ImagePickRequestCode = 100

  private var profileBitmap: Bitmap? = null

  private val compositeDisposable = CompositeDisposable()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_user, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    Picasso.get()
      .load(R.drawable.ic_add_photo)
      .into(iv_user_profile)

    iv_user_profile.setOnClickListener {
      val intent = Intent(Intent.ACTION_PICK)
      intent.type = "image/*"
      intent.putExtra("crop", "true")
      intent.putExtra("scale", true)
      intent.putExtra("aspectX", 1)
      intent.putExtra("aspectY", 1)
      intent.putExtra("outputX", 128)
      intent.putExtra("outputY", 128)
      intent.putExtra("return-data", true)

      startActivityForResult(intent, ImagePickRequestCode)
    }

    btn_next.setOnClickListener {
      if(profileBitmap == null) {
        handleNext()
      } else {
        handleUploadProfile()
      }
    }

    tv_pass.setOnClickListener {
      handleNext()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if(resultCode != RESULT_OK || requestCode != ImagePickRequestCode)
      return

    val uri = data?.data
    val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, uri)
    iv_user_profile.setImageBitmap(bitmap)
    profileBitmap = bitmap
  }

  fun setupAccount() {
    val activity = activity as RegisterActivity
    tv_user_name.text = activity.name
  }

  override fun onDestroy() {
    compositeDisposable.clear()
    super.onDestroy()
  }

  private fun handleUploadProfile() {
    btn_next.isEnabled = false

    profileBitmap?.let { bitmap ->
      val path = context?.externalCacheDir?.absolutePath
      val file = bitmap.toFile(path!!, "upload_profile.jpg")

      UserManager.user?.let { user ->
        compositeDisposable.add(UserAPI.uploadUserPhoto(user.id, MultipartBody.Part.createFormData(
          "photo",
          file.name,
          RequestBody.create(
            MediaType.parse("image/jpg"),
            file
          )
        )).subscribe({
          user.profileUri = it.uri
          handleNext()
        }, {
          btn_next.isEnabled = true
          throw it
        }))
      }
    }
  }

  private fun handleNext() {
    val activity = (activity as RegisterActivity)
    activity.viewPager.currentItem += 1
  }

}
