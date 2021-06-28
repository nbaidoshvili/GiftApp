package com.example.gift.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class AddPostFragment : Fragment(R.layout.fragment_add_post) {

    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var person: TextView
    private lateinit var desc: TextView
    private lateinit var button: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        person = view.findViewById(R.id.addPostPerson)
        desc = view.findViewById(R.id.addPostDescription)
        button = view.findViewById(R.id.addPostButton)

        button.setOnClickListener {
            var errors = 0
            if (person.text.isEmpty()){
                person.setError("title must not be empty")
                errors++
            }
            if(desc.text.isEmpty()){
                desc.setError("description must not be empty")
                errors++
            }
            if(errors==0){
                val post : MutableMap<String, Any> = HashMap()
                post["person"] = person.text.toString()
                post["description"] = desc.text.toString()
                post["userId"] = mAuth.currentUser!!.uid
                post["timestamp"] = FieldValue.serverTimestamp()

                fStore.collection("users").document(mAuth.currentUser!!.uid).collection("posts").add(post)

                Navigation.findNavController(view).popBackStack()
            }
        }
    }
}