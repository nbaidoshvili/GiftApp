package com.example.gift.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val emailPattern: Regex = Regex("^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+\$")

    private lateinit var email : EditText
    private lateinit var button : Button
    private lateinit var progressBar : ProgressBar
    private lateinit var message : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.resetEmail)
        button = view.findViewById(R.id.resetButton)
        progressBar = view.findViewById(R.id.resetProgressBar)
        message = view.findViewById(R.id.resetMessage)

        button.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        if(!email.text.toString().matches(emailPattern)){
            email.setError("invalid email")
        }
        else{
            loading()
            mAuth.sendPasswordResetEmail(email.text.toString()).addOnSuccessListener {
                loaded()
                message.text = "An email has been sent. Check your inbox"
                message.setTextColor(Color.parseColor("#1f1f1f"))
            }.addOnFailureListener { e ->
                loaded()
                message.text = e.localizedMessage
                message.setTextColor(Color.parseColor("#ea3d53"))
            }
        }
    }

    fun loading(){
        message.visibility = View.INVISIBLE
        progressBar.visibility=View.VISIBLE
    }
    fun loaded(){
        message.visibility = View.VISIBLE
        progressBar.visibility=View.INVISIBLE
    }
}