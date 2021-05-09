package com.example.conversate.utils

import com.example.conversate.AuthenticationActivity
import com.example.conversate.BaseActivity
import com.example.conversate.model.User
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Firestore {
    private val db = FirebaseFirestore.getInstance()

    fun registerUsers(activity: AuthenticationActivity, userInfo: User){
        val user = hashMapOf(
            "name" to "",
            "phone" to userInfo.phone,
            "email" to userInfo.email,
            "id" to userInfo.id
        )

        db.collection(Constants.USERS)
            .document(userInfo.id)
            .set(user, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess(userInfo.id)
            }
            .addOnFailureListener {
                activity.showErrorSnackBar("Error while registering the user", true)
            }
    }

//    fun setUsername(activity: AuthenticationActivity, username: String){
//        db.collection(())
//    }

}