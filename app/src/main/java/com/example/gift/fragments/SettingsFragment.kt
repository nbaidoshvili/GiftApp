package com.example.gift.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.gift.MainActivity
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var changePassword : Button
    private lateinit var logout : TextView
    private lateinit var email : TextView
    private lateinit var firstname : TextView
    private lateinit var lastname : TextView
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePassword = view.findViewById(R.id.changePasswordButton)
        logout = view.findViewById(R.id.logoutButton)
        email = view.findViewById(R.id.settingsEmail)
        firstname =view.findViewById(R.id.settingsFirstname)
        lastname=view.findViewById(R.id.settingsLastname)

        email.text = mAuth.currentUser?.email

        fStore.collection("users").document(mAuth.currentUser?.uid.toString()).get().addOnSuccessListener { user->
            firstname.text = user["firstname"].toString()
            lastname.text = user["lastname"].toString()
        }

        changePassword.setOnClickListener {
            ChangePasswordDialog(requireContext()).show()
        }
        logout.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }
    }
}