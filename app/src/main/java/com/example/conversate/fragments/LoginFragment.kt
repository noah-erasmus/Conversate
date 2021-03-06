package com.example.conversate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.conversate.AuthenticationActivity
import com.example.conversate.BaseActivity
import com.example.conversate.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signupFragment = SignupFragment()

        signup_btn_l.setOnClickListener{
            fragmentManager!!.beginTransaction().apply {
                replace(R.id.auth_fragment_area, signupFragment)
                addToBackStack(null)
                commit()
            }
        }

        login_btn_l.setOnClickListener{
            var email : String = login_email.text.toString()
            var password : String = login_password.text.toString()

            (activity as AuthenticationActivity).loginUser(email, password)

        }
    }
}