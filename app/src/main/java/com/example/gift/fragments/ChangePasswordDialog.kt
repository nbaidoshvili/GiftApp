package com.example.gift.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordDialog(context: Context) : Dialog(context) {

    init {
        setCanceledOnTouchOutside(false)
    }

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val passwordPattern: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,100}\$")

    private lateinit var change: TextView
    private lateinit var cancel: TextView
    private lateinit var passwordField: EditText
    private lateinit var repeatPasswordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_change_password)

        change = findViewById(R.id.dialogOk)
        cancel = findViewById(R.id.dialogCancel)
        passwordField = findViewById(R.id.dialogPassword)
        repeatPasswordField = findViewById(R.id.dialogRepeatPassword)


        cancel.setOnClickListener {
            dismiss()
        }
        change.setOnClickListener {
            val pas1 = passwordField.text.toString()
            val pas2 = repeatPasswordField.text.toString()
            var errors = 0

            if(!pas1.matches(passwordPattern)){
                passwordField.setError("password must be at least 8 characters long and contain at least one upper case letter, one lower case letter and one number")
                errors++
            }
            if(pas1 != pas2){
                repeatPasswordField.setError("passwords don't match")
                errors++
            }
            if(errors==0){
                mAuth.currentUser!!.updatePassword(pas1).addOnSuccessListener {
                    Toast.makeText(context,"Your password was changed", Toast.LENGTH_SHORT).show()
                    dismiss()
                }.addOnFailureListener {
                    Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}