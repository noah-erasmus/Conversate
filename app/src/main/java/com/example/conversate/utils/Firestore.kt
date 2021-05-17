package com.example.conversate.utils

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.example.conversate.AuthenticationActivity
import com.example.conversate.BaseActivity
import com.example.conversate.OnboardingActivity
import com.example.conversate.ProfileActivity
import com.example.conversate.model.User
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {
    private val db = FirebaseFirestore.getInstance()

    fun registerUsers(activity: AuthenticationActivity, userInfo: User){

        db.collection(Constants.USERS)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess(userInfo.id)
            }
            .addOnFailureListener {
                activity.showErrorSnackBar("Error while registering the user", true)
            }
    }

    fun setUsername(activity: OnboardingActivity, userId: String, username: String){
        db.collection(Constants.USERS).document(userId).update("name", username).addOnSuccessListener {
            println("usn set")
        }.addOnFailureListener {
            println("usn not set")
        }
    }

    fun getUserInfoById(activity: ProfileActivity, userId: String){
        db.collection(Constants.USERS).document(userId).get().addOnSuccessListener { document ->
            if(document != null){
                val user = document.toObject(User::class.java)!!
                activity.setUserInfo(user)
            }else{
                Toast.makeText(activity, "The user info is empty.", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "Get failed with ", exception)
        }
    }
}