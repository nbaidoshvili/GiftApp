import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gift.R
import com.example.gift.fragments.PagerFragmentDirections
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class RecyclerAdapterComments (private val list: QuerySnapshot): RecyclerView.Adapter<RecyclerAdapterComments.RecyclerViewHolder>() {

    val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.commentName)
        val email: TextView = itemView.findViewById(R.id.commentEmail)
        val body: TextView = itemView.findViewById(R.id.commentBody)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_comment, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size()
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

//        holder.name.text = list.documents[position]["firstname"].toString() + " " + list.documents[position]["lastname"].toString()
//        holder.email.text = list.documents[position]["email"].toString()


        holder.body.text = list.documents[position]["body"].toString()

        fStore.collection("users").document(list.documents[position]["userId"].toString()).get()
            .addOnSuccessListener { user ->
                holder.name.text = user["firstname"].toString() + " " + user["lastname"].toString()
                holder.email.text = user["email"].toString()
            }

    }

}