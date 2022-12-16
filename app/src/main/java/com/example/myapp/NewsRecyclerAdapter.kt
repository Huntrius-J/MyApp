package com.example.myapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.api.Article
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class NewsRecyclerAdapter(val context: Context, val list: List<Article>, private val ncellClickListener: NewsCellClickListener):RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val view:View) :RecyclerView.ViewHolder(view)
    {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val imageView: ImageView = view.findViewById(R.id.newsImage)
    }
    val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.news_recycleritem, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = list[position].title.toString()
        holder.tvContent.text = list[position].content.toString()
        try {
            holder.tvAuthor.text = list[position].author.toString()
            Picasso.get().load(list[position].urlToImage).into(holder.imageView)
        }
        catch (ex:java.lang.Exception) {

        }

        val prop = list[position]
        holder.itemView.setOnClickListener {
            ncellClickListener.newsCellClickListener(prop)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}