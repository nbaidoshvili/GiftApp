import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.gift.R
import com.example.gift.fragments.PagerFragmentDirections
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot


class RecyclerAdapterPosts (private val list: List<QueryDocumentSnapshot>): RecyclerView.Adapter<RecyclerAdapterPosts.RecyclerViewHolder>() {


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val person: TextView = itemView.findViewById(R.id.postPerson)
        val description: TextView = itemView.findViewById(R.id.postDescription)
        val post: ConstraintLayout = itemView.findViewById(R.id.post)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_post, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.person.text = list[position]["person"].toString()
        holder.description.text = list[position]["description"].toString()


        holder.post.setOnClickListener {
            val action = PagerFragmentDirections
                .actionPagerFragmentToCommentsFragment(pid = list[position].id, uid = list[position]["userId"].toString())
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

}