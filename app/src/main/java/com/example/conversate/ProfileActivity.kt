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

        val sharedPref = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        val userId = sharedPref.getString(Constants.LOGGED_IN_ID, "uidHash")

        Firestore().getUserInfoById(this, userId!!)

        profile_appbar.setNavigationOnClickListener {
            val intent = Intent(this, ConversationsActivity::class.java)
            startActivity(intent)
            finish()
        }

        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
        editPhoneButton = findViewById(R.id.edit_phone)
        editEmailButton = findViewById(R.id.edit_email)

        editPhoneButton.setOnClickListener(View.OnClickListener {
            customAlertDialogView = LayoutInflater.from(this).inflate(R.layout.edit_phone_dialog, null, false)

            launchEditPhoneDialog()
        })

        editEmailButton.setOnClickListener(View.OnClickListener {
            customAlertDialogView = LayoutInflater.from(this).inflate(R.layout.edit_email_dialog, null, false)

            launchEditEmailDialog()
        })
    }

    fun setUserInfo(userInfo: User){
        profile_name.text = userInfo.name
        profile_email.text = userInfo.email
        profile_number.text = userInfo.phone
    }

    fun updatePhone(number: String){
        profile_number.text = number
    }

    fun updateEmail(email: String){
        profile_email.text = email
    }

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

    private fun displayMessage(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var editPhoneButton: ImageButton
    private lateinit var editEmailButton: ImageButton
    private lateinit var customAlertDialogView: View
    private lateinit var numberTextField: TextInputLayout
    private lateinit var emailTextField: TextInputLayout
}