package com.example.gift.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gift.AccountActivity
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment(R.layout.fragment_register) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val emailPattern: Regex = Regex("^([a-zA-Z0-9]+\\.)*[a-zA-Z0-9]+@([a-zA-Z0-9]+\\.)+[a-zA-Z0-9]+\$")
    val passwordPattern: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,100}\$")
    val namePattern: Regex = Regex("^[a-zA-z]{1,20}\$")

    private lateinit var firstnameField : EditText
    private lateinit var lastnameField : EditText
    private lateinit var emailField : EditText
    private lateinit var passwordField : EditText
    private lateinit var repeatPasswordField : EditText
    private lateinit var registerButton : Button
    private lateinit var progressBar : ProgressBar
    private lateinit var failure : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstnameField = view.findViewById(R.id.registerFirstname)
        lastnameField = view.findViewById(R.id.registerLastname)
        emailField = view.findViewById(R.id.registerEmail)
        passwordField = view.findViewById(R.id.registerPassword)
        repeatPasswordField = view.findViewById(R.id.registerRepeatPassword)
        registerButton = view.findViewById(R.id.registerButton)
        progressBar = view.findViewById(R.id.registerProgressBar)
        failure = view.findViewById(R.id.registerFailure)

        registerButton.setOnClickListener {
            registerUser()
        }

    }

    fun registerUser(){
        val firstname = firstnameField.text.toString()
        val lastname = lastnameField.text.toString()
        val email = emailField.text.toString()
        val pas1 = passwordField.text.toString()
        val pas2 = repeatPasswordField.text.toString()
        var errors = 0


        if(!firstname.matches(namePattern)){
            firstnameField.setError("invalid name")
            errors++
        }
        if(!lastname.matches(namePattern)){
            lastnameField.setError("invalid name")
            errors++
        }
        if(!email.matches(emailPattern)){
            emailField.setError("invalid email")
            errors++
        }
        if(!pas1.matches(passwordPattern)){
            passwordField.setError("password must be at least 8 characters long and contain at least one upper case letter, one lower case letter and one number")
            errors++
        }
        if(pas1 != pas2){
            repeatPasswordField.setError("passwords don't match")
            errors++
        }


        if(errors==0){

            loading()

            mAuth.createUserWithEmailAndPassword(email, pas1).addOnSuccessListener {
                val userID = mAuth.currentUser?.uid
                val user : MutableMap<String, Any> = HashMap()
                user["firstname"] = firstname
                user["lastname"] = lastname
                user["email"] = email

                fStore.collection("users").document(userID!!).set(user)

                startActivity(Intent(activity, AccountActivity::class.java))
                activity?.finish()

            }.addOnFailureListener { e->
                failure.text = e.localizedMessage
                loaded()
            }
        }
    }

    fun loading(){
        failure.visibility = View.INVISIBLE
        firstnameField.visibility = View.INVISIBLE
        lastnameField.visibility = View.INVISIBLE
        emailField.visibility = View.INVISIBLE
        passwordField.visibility = View.INVISIBLE
        repeatPasswordField.visibility = View.INVISIBLE
        registerButton.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun loaded(){
        failure.visibility = View.VISIBLE
        firstnameField.visibility = View.VISIBLE
        lastnameField.visibility = View.VISIBLE
        emailField.visibility = View.VISIBLE
        passwordField.visibility = View.VISIBLE
        repeatPasswordField.visibility = View.VISIBLE
        registerButton.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }

}