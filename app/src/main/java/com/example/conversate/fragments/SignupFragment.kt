package com.example.conversate.fragments

import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.conversate.AuthenticationActivity
import com.example.conversate.BaseActivity
import com.example.conversate.R
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment(R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginFragment = LoginFragment()

        login_btn_s.setOnClickListener{
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.auth_fragment_area, loginFragment)
                addToBackStack(null)
                commit()
            }
        }

        signup_btn_s.setOnClickListener{
            var email: String = signup_email.text.toString()
            var password: String = signup_password.text.toString()

            (activity as AuthenticationActivity).registerUser(email, password)
        }
    }
}