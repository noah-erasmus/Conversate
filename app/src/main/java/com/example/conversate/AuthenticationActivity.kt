package com.example.conversate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.conversate.fragments.LoginFragment
import com.example.conversate.fragments.SignupFragment
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        auth = Firebase.auth
    }

    private lateinit var auth : FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun registerUser(email: String, password: String, phone: String){
        if(email == "" || password == ""){
            showErrorSnackBar("Please enter your Email & Password", true)
        }else{
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful){
                        val firebaseUser : FirebaseUser = task.result!!.user!!

                        val user = User(
                            firebaseUser.uid,
                            email,
                            phone
                        )

                        Firestore().registerUsers(this, user)

                        val intent = Intent(this, ConversationsActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
            )
        }
    }

    fun loginUser(email: String, password: String){
        if(email == "" || password == ""){
            showErrorSnackBar("Please enter your Email & Password", true)
        }else{
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener { task ->
                        if(task.isSuccessful){
                            val firebaseUser : FirebaseUser = task.result!!.user!!

                            val user = auth.currentUser

                            val intent = Intent(this, ConversationsActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            )
        }
    }

    fun userRegisteredSuccess(uid: String){
        showErrorSnackBar("Registered Successfully", false)

        val intent = Intent(this, ConversationsActivity::class.java)
        intent.putExtra(Constants.LOGGED_IN_ID, uid)
        startActivity(intent)
        finish()
    }
}