package com.example.gift.fragments

import RecyclerAdapterComments
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gift.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class CommentsFragment : Fragment(R.layout.fragment_comments) {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var recycler : RecyclerView
    private lateinit var adapter : RecyclerAdapterComments
    private lateinit var commentField : EditText
    private lateinit var send : TextView
    private lateinit var progressBar : ProgressBar


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerComments)
        recycler.layoutManager = LinearLayoutManager(activity)
        commentField = view.findViewById(R.id.writeComment)
        send = view.findViewById(R.id.writeCommentButton)
        progressBar = view.findViewById(R.id.commentsProgressBar)
        val currentUserReference = fStore.collection("users").document(mAuth.currentUser!!.uid)
        val postId = CommentsFragmentArgs.fromBundle(requireArguments()).pid
        val userId = CommentsFragmentArgs.fromBundle(requireArguments()).uid

        refresh(userId, postId)


        send.setOnClickListener {
            if (commentField.text.isNotEmpty()){

                currentUserReference.get().addOnSuccessListener { user ->
                    val comment : MutableMap<String, Any?> = HashMap()
//                    comment["firstname"] = user.data!!["firstname"]
//                    comment["lastname"] = user.data!!["lastname"]
//                    comment["email"] = user.data!!["email"]
                    comment["postId"] = postId
                    comment["userId"] = user.id
                    comment["body"] = commentField.text.toString()
                    comment["timestamp"] = FieldValue.serverTimestamp()


                    fStore.collection("users").document(userId)
                        .collection("posts").document(postId).collection("comments").add(comment)

                    commentField.setText("")
                    refresh(userId, postId)
                }
            }
        }

    }


    fun refresh(userId: String, postId:String){
        loading()
        fStore.collection("users").document(userId)
            .collection("posts").document(postId).collection("comments")
            .orderBy("timestamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener { list->

                adapter = RecyclerAdapterComments(list)
                adapter.notifyDataSetChanged()
                recycler.adapter = adapter
                loaded()
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