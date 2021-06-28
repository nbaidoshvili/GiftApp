package com.example.gift.fragments

import RecyclerAdapterPosts
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot

class OtherPostsFragment : Fragment(R.layout.fragment_other_posts) {


    private lateinit var recycler : RecyclerView
    private lateinit var adapter : RecyclerAdapterPosts
    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var progressBar : ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerOtherPosts)
        recycler.layoutManager = LinearLayoutManager(activity)
        val currentUserId = mAuth.currentUser?.uid
        progressBar = view.findViewById(R.id.otherPostsProgressBar)

        loading()

        fStore.collection("users").get()
            .addOnSuccessListener { users->

                var list : MutableList<QueryDocumentSnapshot> = mutableListOf()

                for(user in users){
                    if(user.id != currentUserId){
                        fStore.collection("users").document(user.id).collection("posts")
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener {  posts ->
                                for(post in posts){
                                    list.add(post)

                                }
                                adapter = RecyclerAdapterPosts(list)
                                adapter.notifyDataSetChanged()
                                recycler.adapter = adapter
                            }
                    }
                    loaded()

                }
            }.addOnFailureListener {
                loaded()
                Toast.makeText(activity,"something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    fun loading(){
        recycler.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }
    fun loaded(){
        recycler.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}