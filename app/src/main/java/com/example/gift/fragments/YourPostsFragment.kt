package com.example.gift.fragments

import RecyclerAdapterPosts
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gift.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import org.w3c.dom.Document

class YourPostsFragment : Fragment(R.layout.fragment_your_posts) {

    private lateinit var fab: FloatingActionButton
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recycler : RecyclerView
    private lateinit var adapter : RecyclerAdapterPosts
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab = view.findViewById(R.id.fab)
        recycler = view.findViewById(R.id.recyclerYourPosts)
        recycler.layoutManager = LinearLayoutManager(activity)
        progressBar = view.findViewById(R.id.yourPostsProgressBar)



        fab.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_pagerFragment_to_addPostFragment)
        }

        loading()
        fStore.collection("users").document(mAuth.currentUser!!.uid)
            .collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result->

                var list : MutableList<QueryDocumentSnapshot> = mutableListOf()

                for(document in result){
                    list.add(document)
                }

                adapter = RecyclerAdapterPosts(list)
                adapter.notifyDataSetChanged()
                recycler.adapter = adapter
                loaded()
            }.addOnFailureListener {
                loaded()
                Toast.makeText(activity,"something went wrong",Toast.LENGTH_SHORT).show()
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