package com.example.conversate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversate.fragments.LoginFragment
import com.example.conversate.fragments.SignupFragment
import kotlinx.android.synthetic.main.fragment_signup.*

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val signupFragment = SignupFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.auth_fragment_area, signupFragment)
            commit()
        }
    }
}