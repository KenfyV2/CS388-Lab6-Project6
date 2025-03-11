package com.codepath.articlesearch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

private const val TAG = "HomeFragment"
private const val API_KEY = BuildConfig.API_KEY
private const val POPULAR_ARTICLES_URL =
    "https://api.nytimes.com/svc/mostpopular/v2/viewed/7.json?api-key=${API_KEY}"

class HomeFragment : Fragment() {
    private val popularArticles = mutableListOf<PopularArticle>()
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var popularArticleAdapter: PopularArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the view for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Configure RecyclerView
        val layoutManager = LinearLayoutManager(context)
        articlesRecyclerView = view.findViewById(R.id.popular_recycler_view)
        articlesRecyclerView.layoutManager = layoutManager
        articlesRecyclerView.setHasFixedSize(true)
        popularArticleAdapter = PopularArticleAdapter(view.context, popularArticles)
        articlesRecyclerView.adapter = popularArticleAdapter

        return view
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    private fun fetchPopularArticles() {
        val client = AsyncHttpClient()
        client.get(POPULAR_ARTICLES_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch popular articles: $statusCode")
                Log.e(TAG, "Error: ${throwable?.message}")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched popular articles")
                try {
                    val parsedJson = createJson().decodeFromString(
                        PopularArticlesResponse.serializer(),
                        json.jsonObject.toString()
                    )
                    parsedJson.results?.let { list ->
                        popularArticles.addAll(list)
                        popularArticleAdapter.notifyDataSetChanged()
                    }
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Fetch popular articles when the view is created
        fetchPopularArticles()
    }
}
