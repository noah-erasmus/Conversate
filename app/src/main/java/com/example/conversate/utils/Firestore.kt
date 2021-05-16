package com.example.conversate.utils

import com.example.conversate.AuthenticationActivity
import com.example.conversate.BaseActivity
import com.example.conversate.OnboardingActivity
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
        db.collection(Constants.USERS).document(userId).update("name", username)
    }

//    fun getUserOnboarding(activity: OnboardingActivity, userId: String){
//        db.collection(Constants.USERS)
//                .document(userId)
//                .get()
//                .addOnSuccessListener {document ->
//                    if(document != null){
//                        val user = document.toObject(User::class.java)!!
//
//                        activity.
//                    }
//                }
//                .addOnFailureListener {
//                }
//    }

//    fun setUsername(activity: AuthenticationActivity, username: String){
//        db.collection(())
//    }

}