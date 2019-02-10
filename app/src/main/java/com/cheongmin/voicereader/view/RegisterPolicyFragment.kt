package com.cheongmin.voicereader.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatCheckBox
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.cheongmin.voicereader.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterPolicyFragment : Fragment() {
  private var isCheckedPolicy1 = false
  private var isCheckedPolicy2 = false

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_register_policy, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val btnNext = view.findViewById<Button>(R.id.btn_next)
    btnNext.isEnabled = false
    btnNext.setOnClickListener {
      val viewPager = (activity as RegisterActivity).viewPager
      viewPager.currentItem += 1
    }

    view.findViewById<TextView>(R.id.tv_cancel).setOnClickListener {
      activity?.finish()
    }

    view.findViewById<EditText>(R.id.edit_policy1).keyListener = null
    view.findViewById<EditText>(R.id.edit_policy2).keyListener = null

    view.findViewById<AppCompatCheckBox>(R.id.cb_policy1).setOnCheckedChangeListener { _, b ->
      isCheckedPolicy1 = b
      handleChange()
    }

    view.findViewById<AppCompatCheckBox>(R.id.cb_policy2).setOnCheckedChangeListener { _, b ->
      isCheckedPolicy2 = b
      handleChange()
    }
  }

  private fun handleChange() {
    val validate = isCheckedPolicy1 && isCheckedPolicy2

    val button = view?.findViewById<Button>(R.id.btn_next)
    button?.isEnabled = validate
  }
}
