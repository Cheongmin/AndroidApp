package com.cheongmin.voicereader.view


import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.androidhuman.rxfirebase2.auth.rxCreateUserWithEmailAndPassword
import com.androidhuman.rxfirebase2.auth.rxGetIdToken
import com.cheongmin.voicereader.R
import com.cheongmin.voicereader.api.AuthorizationAPI
import com.cheongmin.voicereader.api.UserAPI
import com.cheongmin.voicereader.model.request.UserRequest
import com.cheongmin.voicereader.network.TokenManager
import com.cheongmin.voicereader.network.UserManager
import com.cheongmin.voicereader.network.client.ApiClient
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register_account.*
import java.util.regex.Pattern
import com.cheongmin.voicereader.utils.validate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable


class RegisterAccountFragment : Fragment() {
  private val emailPattern = android.util.Patterns.EMAIL_ADDRESS
  private val namePattern = Pattern.compile("[가-힣]{2,4}|[a-zA-Z]{2,10}")
  private val locationPattern = Pattern.compile("(.{2,3}(특별시|광역시|도))(.{2,}(시|군|구))")

  private val compositeDisposable = CompositeDisposable()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_account, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    btn_next.setOnClickListener {
      handleRegister()
    }

    tv_cancel.setOnClickListener {
      activity?.finish()
    }

    layout_email.validate({ s -> emailPattern.matcher(s).matches() }, "잘못된 이메일 형태입니다")
    layout_email.isErrorEnabled = false

    layout_password.validate({ s -> s.length >= 6 }, "비밀번호는 6글자 이상이어야 합니다")
    layout_password.isErrorEnabled = false

    layout_password_check.validate({ s -> s == edit_password.text.toString() }, "비밀번호가 일치하지 않습니다")
    layout_password_check.isErrorEnabled = false

    layout_name.validate({ s -> namePattern.matcher(s).matches() }, "한글 혹은 영어로 2글자에서 10글자 이내로 입력해야합니다.")
    layout_name.isErrorEnabled = false

    layout_location.validate({ s -> locationPattern.matcher(s).matches()}, "시도와 시군구까지 입력해주세요")
    layout_location.isErrorEnabled = false
  }

  private fun handleRegister() {
    val hasError = arrayOf(layout_email, layout_password, layout_password, layout_name, layout_location)
      .any { it.error != null }

    val hasBlankField = arrayOf(layout_email, layout_password, layout_password, layout_name, layout_location)
      .any { it.editText?.text.toString().isBlank() }

    if (hasError || hasBlankField) {
      Toast.makeText(context!!, "입력 폼들을 채워주세요!", Toast.LENGTH_LONG).show()
      return
    }

    val email = edit_email.text.toString()
    val password = edit_password.text.toString()
    val name = edit_name.text.toString()
    val location = edit_location.text.toString()

    var idToken = ""

    val subscribe = FirebaseAuth.getInstance()
      .rxCreateUserWithEmailAndPassword(email, password)
      .flatMap { it.rxGetIdToken(true) }
      .flatMap {
        idToken = it
        return@flatMap UserAPI.newUser(it, UserRequest(name, location))
      }
      .flatMap {
        UserManager.user = it
        return@flatMap AuthorizationAPI.fetchAccessToken(idToken)
      }
      .subscribe({
        TokenManager.getInstance(context!!).setToken(it)
        ApiClient.init(it.token)
        handleNext()
      }, {
        when (it) {
          is FirebaseAuthInvalidCredentialsException -> {

          }
          is FirebaseAuthWeakPasswordException -> {

          }
          is FirebaseAuthUserCollisionException -> {
            layout_email.error = "이미 가입된 이메일입니다"
          }
          else -> {
            throw it
          }
        }
      })
  }

  private fun handleNext() {
    val email = edit_email.text.toString()
    val password = edit_password.text.toString()
    val name = edit_name.text.toString()
    val location = edit_location.text.toString()

    val activity = (activity as RegisterActivity)
    activity.email = email
    activity.password = password
    activity.name = name
    activity.location = location

    // passing account data to next fragment
    val viewPager = activity.viewPager
    val adapter = viewPager.adapter as FragmentStatePagerAdapter
    val nextFragment = adapter.getItem(viewPager.currentItem+1)

    if(nextFragment is RegisterUserFragment) {
      nextFragment.setupAccount()
      viewPager.currentItem += 1
    }
  }
}
