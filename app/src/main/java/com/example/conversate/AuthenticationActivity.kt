package com.example.conversate

import android.content.Context
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

        //Prepare variable for signup fragment
        val signupFragment = SignupFragment()

        //Apply signup fragment by default
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.auth_fragment_area, signupFragment)
            commit()
        }

        //Prepare firebase authentication
        auth = Firebase.auth
    }

    private lateinit var auth : FirebaseAuth

    //On app start determine if there is an active user
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //Add new user to firestore and set active user ID
    fun registerUser(email: String, password: String, phone: String){
        //Prepare SharedPreferences
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //Check no fields are empty
        if(email == "" || password == ""){
            showErrorSnackBar("Please enter your Email & Password", true)
        }else{
            //Create user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful){
                        //Set active user information
                        val firebaseUser : FirebaseUser = task.result!!.user!!
                        val user = User(
                            firebaseUser.uid,
                                "",
                            email,
                            phone
                        )

                        //Store active user ID in SharedPreferences
                        editor.apply{
                            putString(Constants.LOGGED_IN_ID, firebaseUser.uid)
                            apply()
                        }

                        //Run firestore register method
                        Firestore().registerUsers(this, user)
                    }else{
                        //Catch error
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }
            )
        }
    }

    //Log into existing user and set active user ID
    fun loginUser(email: String, password: String){
        //Prepare SharedPreferences
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        if(email == "" || password == ""){
            showErrorSnackBar("Please enter your Email & Password", true)
        }else{
            //Log in user with email and password
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    OnCompleteListener { task ->
                        if(task.isSuccessful){
                            //Set active user information
                            val firebaseUser : FirebaseUser = task.result!!.user!!
                            val user = auth.currentUser
                            val intent = Intent(this, ConversationsActivity::class.java)
                            startActivity(intent)
                            finish()

                            //Store active user ID in SharedPreferences
                            editor.apply{
                                putString(Constants.LOGGED_IN_ID, firebaseUser.uid)
                                apply()
                            }
                        }else{
                            //Catch error
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            )
        }
    }

    //Deal with successful user registration
    fun userRegisteredSuccess(uid: String){
        showErrorSnackBar("Registered Successfully", false)

        val intent = Intent(this, OnboardingActivity::class.java)
        intent.putExtra(Constants.LOGGED_IN_ID, uid)
        startActivity(intent)
        finish()
    }
}