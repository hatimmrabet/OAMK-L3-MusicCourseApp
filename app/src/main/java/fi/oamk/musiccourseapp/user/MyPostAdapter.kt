package fi.oamk.musiccourseapp.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import fi.oamk.musiccourseapp.R
import fi.oamk.musiccourseapp.posts.Post

class MyPostAdapter(private val myDataset: ArrayList<Post>, private val listener: OnPostListener): RecyclerView.Adapter<MyPostAdapter.MyViewHolder>() {

    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.postinfo_row,parent,false)
        //Order posts by date
        myDataset.sortByDescending { it.date }
        return MyViewHolder(myView)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        val title: TextView = itemView.findViewById(R.id.post_title)
        val instrument: TextView = itemView.findViewById(R.id.post_instrument)
        val description: TextView = itemView.findViewById(R.id.post_desc)
        val price: TextView = itemView.findViewById(R.id.post_price)
        val time: TextView = itemView.findViewById(R.id.post_time)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if(position != RecyclerView.NO_POSITION)
            {
                listener.onPostClick(position)
            }
        }
    }

    interface OnPostListener{
        fun onPostClick(position: Int)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        database = Firebase.database.reference
        val post: Post = myDataset.get(position)
        holder.title.text = post.title
        holder.instrument.text = post.instrument
        holder.description.text = post.description
        holder.price.text = post.price.toString() + " €"
        holder.time.text = post.date
    }

    override fun getItemCount() = myDataset.size
}