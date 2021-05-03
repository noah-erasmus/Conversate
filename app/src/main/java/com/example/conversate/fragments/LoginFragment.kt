package com.example.conversate.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }
}