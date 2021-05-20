package com.example.conversate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.example.conversate.model.User
import com.example.conversate.utils.Constants
import com.example.conversate.utils.Firestore
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        //Prepare SharedPreferences
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //Get active userID from SharedPrefs
        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        //Use userID to update App with user object
        Firestore().getUserInfoById(this, userId!!)

        //Back button to Conversations Activity
        profile_appbar.setNavigationOnClickListener {
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Prepare material dialog builder
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)

        //Set edit buttons by ID
        editPhoneButton = findViewById(R.id.edit_phone)
        editEmailButton = findViewById(R.id.edit_email)

        //Edit phone listener
        editPhoneButton.setOnClickListener(View.OnClickListener {
            customAlertDialogView = LayoutInflater.from(this).inflate(R.layout.edit_phone_dialog, null, false)

            launchEditPhoneDialog()
        })

        //Edit email listener
        editEmailButton.setOnClickListener(View.OnClickListener {
            customAlertDialogView = LayoutInflater.from(this).inflate(R.layout.edit_email_dialog, null, false)

            launchEditEmailDialog()
        })
    }

    //Method updates view with user info
    fun setUserInfo(userInfo: User){
        profile_name.text = userInfo.name
        profile_email.text = userInfo.email
        profile_number.text = userInfo.phone
    }

    //Updates phone information after edit
    fun updatePhone(number: String){
        profile_number.text = number
    }

    //Updates email information after edit
    fun updateEmail(email: String){
        profile_email.text = email
    }

    //Prepare variables for edit dialogs
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var editPhoneButton: ImageButton
    private lateinit var editEmailButton: ImageButton
    private lateinit var customAlertDialogView: View
    private lateinit var numberTextField: TextInputLayout
    private lateinit var emailTextField: TextInputLayout

    //Inflates edit phone dialog
    private fun launchEditPhoneDialog(){
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        numberTextField = customAlertDialogView.findViewById(R.id.edit_phone_field)

        materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Edit Phone")
                .setMessage("Enter your new phone number.")
                .setPositiveButton("Change"){dialog, _ ->
                    val number = numberTextField.editText?.text.toString()
                    Firestore().setPhone(this, userId!!, number)
                    updatePhone(number)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){dialog, _ ->
                    displayMessage("Operation cancelled.")
                    dialog.dismiss()
                }
                .show()
    }

    //Inflates edit email dialog
    private fun launchEditEmailDialog(){
        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        emailTextField = customAlertDialogView.findViewById(R.id.edit_email_field)

        materialAlertDialogBuilder.setView(customAlertDialogView)
                .setTitle("Edit Email")
                .setMessage("Enter your new email.")
                .setPositiveButton("Change"){dialog, _ ->
                    val email = emailTextField.editText?.text.toString()
                    Firestore().setEmail(this, userId!!, email)
                    updateEmail(email)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){dialog, _ ->
                    displayMessage("Operation cancelled.")
                    dialog.dismiss()
                }
                .show()
    }

    //Displays short toast
    private fun displayMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}