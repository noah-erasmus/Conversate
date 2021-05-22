package com.example.conversate.utils

import android.content.ContentValues.TAG
import android.os.Message
import android.util.Log
import android.widget.Toast
import com.example.conversate.*
import com.example.conversate.model.Memo
import com.example.conversate.model.User
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class Firestore {
    //Get firestore db
    private val db = FirebaseFirestore.getInstance()

    //Adds new user document to firestore
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

    //Updates username in existing document
    fun setUsername(activity: OnboardingActivity, userId: String, username: String){
        db.collection(Constants.USERS).document(userId).update("name", username).addOnSuccessListener {
            println("usn set")
        }.addOnFailureListener {
            println("usn not set")
        }
    }

    //Updates phone in existing document
    fun setPhone(activity: ProfileActivity, userId: String, phone:String){
        db.collection(Constants.USERS).document(userId).update("phone", phone).addOnSuccessListener {
            Toast.makeText(activity, "Phone number updated.", Toast.LENGTH_SHORT).show()
        }
    }

    //Updates email in existing document
    fun setEmail(activity: ProfileActivity, userId: String, email:String){
        db.collection(Constants.USERS).document(userId).update("email", email).addOnSuccessListener {
            Toast.makeText(activity, "Email updated.", Toast.LENGTH_SHORT).show()
        }
    }

    //Get user info object by ID
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

    private val  messagesdb = Firebase.firestore.collection(Constants.MESSAGES)

    fun sendMessage(activity: ChatActivity, memo: Memo) = CoroutineScope(Dispatchers.IO).launch {
        try {
            messagesdb.add(memo).await()
        } catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
