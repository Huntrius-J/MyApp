package com.example.myapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.api.Article
import com.example.myapp.api.NewsJson
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/v2/"

class news : Fragment(), NewsCellClickListener {

    companion object {
        fun newInstance() = news()
    }

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_news, container, false)

        getNewsData(root)

        return root
    }

    private fun getNewsData(root:View)
    {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getNews().awaitResponse()
            if(response.isSuccessful)
            {
                val newsList = response.body()!!.articles
                withContext(Dispatchers.Main)
                {
                    var adapter = NewsRecyclerAdapter(root.context,newsList,this@news)
                    var recyclerNews = root.findViewById<RecyclerView>(R.id.recyclerNews)
                    recyclerNews.adapter = adapter
                    recyclerNews.layoutManager = LinearLayoutManager(root.context)
                    var progressBar = root.findViewById<ProgressBar>(R.id.progressBar)
                    progressBar.visibility = View.GONE
                }
            }
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun newsCellClickListener(data: Article) {
        var browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
        startActivity(browserIntent)
    }

}

interface NewsCellClickListener {
    fun newsCellClickListener(data: Article)
}

