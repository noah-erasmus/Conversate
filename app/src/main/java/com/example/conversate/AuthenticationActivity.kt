package com.example.conversate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversate.fragments.LoginFragment
import com.example.conversate.fragments.SignupFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_signup.*

class AuthenticationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        val signupFragment = SignupFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.auth_fragment_area, signupFragment)
            commit()
        }
    }

    fun registerUser(email: String, password: String){
        if(email == "" || password == ""){
            showErrorSnackBar("Please enter your Email & Password", true)
        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful){
                        val firebaseUser : FirebaseUser = task.result!!.user!!

                        showErrorSnackBar("Succesfully registered user id ${firebaseUser.uid}", false)
                    }else{
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
            )
        }
    }
}