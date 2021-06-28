package com.example.gift.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.gift.AccountActivity
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login){

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var register : TextView
    private lateinit var reset : TextView
    private lateinit var login : Button
    private lateinit var progressBar : ProgressBar
    private lateinit var img: ImageView
    private lateinit var txt : TextView
    private lateinit var failure: TextView
    private lateinit var welcom : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.loginEmail)
        password = view.findViewById(R.id.loginPassword)
        register = view.findViewById(R.id.register)
        reset = view.findViewById(R.id.reset)
        login = view.findViewById(R.id.loginButton)
        progressBar = view.findViewById(R.id.loginProgressBar)
        img = view.findViewById(R.id.logo)
        txt = view.findViewById(R.id.loginText)
        failure = view.findViewById(R.id.loginFailure)
        welcom = view.findViewById(R.id.welcome)

        register.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        reset.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }
        login.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {

        var errors = 0
        if(email.text.isEmpty()){
            email.setError("email field is empty")
            errors++
        }
        if(password.text.isEmpty()){
            password.setError("password field is empty")
            errors++
        }

        if(errors==0){
            loading()
            mAuth.signInWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnSuccessListener {
                startActivity(Intent(activity, AccountActivity::class.java))
                activity?.finish()
            }.addOnFailureListener { e ->
                failure.text = e.localizedMessage
                loaded()
            }
        }
    }

    fun loading(){
        failure.visibility = View.INVISIBLE
        welcom.visibility = View.INVISIBLE
        img.visibility = View.INVISIBLE
        txt.visibility = View.INVISIBLE
        email.visibility = View.INVISIBLE
        password.visibility = View.INVISIBLE
        register.visibility = View.INVISIBLE
        reset.visibility = View.INVISIBLE
        login.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun loaded(){
        failure.visibility = View.VISIBLE
        welcom.visibility = View.VISIBLE
        img.visibility = View.VISIBLE
        txt.visibility = View.VISIBLE
        email.visibility = View.VISIBLE
        password.visibility = View.VISIBLE
        register.visibility = View.VISIBLE
        reset.visibility = View.VISIBLE
        login.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}