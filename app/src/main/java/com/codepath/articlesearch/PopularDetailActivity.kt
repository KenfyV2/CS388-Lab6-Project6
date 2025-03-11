package com.codepath.articlesearch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class PopularDetailActivity : AppCompatActivity() {
    private lateinit var articleImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var bylineTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var abstractTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_detail)

        // Initialize views
        articleImageView = findViewById(R.id.popularDetailImage)
        titleTextView = findViewById(R.id.popularDetailTitle)
        bylineTextView = findViewById(R.id.popularDetailByline)
        dateTextView = findViewById(R.id.popularDetailDate)
        abstractTextView = findViewById(R.id.popularDetailAbstract)

        // Get the article from the intent
        val article = intent.getSerializableExtra(POPULAR_ARTICLE_EXTRA) as PopularArticle

        // Set the article details
        titleTextView.text = article.title
        bylineTextView.text = article.byline
        dateTextView.text = article.publishedDate
        abstractTextView.text = article.abstract

        // Load the article image
        Glide.with(this)
            .load(article.imageUrl)
            .into(articleImageView)
    }
}