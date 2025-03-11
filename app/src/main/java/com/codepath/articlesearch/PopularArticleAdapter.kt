package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val POPULAR_ARTICLE_EXTRA = "POPULAR_ARTICLE_EXTRA"

class PopularArticleAdapter(private val context: Context, private val articles: List<PopularArticle>) :
    RecyclerView.Adapter<PopularArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_popular_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val articleImageView = itemView.findViewById<ImageView>(R.id.popularArticleImage)
        private val titleTextView = itemView.findViewById<TextView>(R.id.popularArticleTitle)
        private val bylineTextView = itemView.findViewById<TextView>(R.id.popularArticleByline)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.popularArticleAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(article: PopularArticle) {
            titleTextView.text = article.title
            bylineTextView.text = article.byline
            abstractTextView.text = article.abstract

            // Load the article image with Glide
            Glide.with(context)
                .load(article.imageUrl)
                .into(articleImageView)
        }

        override fun onClick(v: View?) {
            // Get selected article
            val article = articles[absoluteAdapterPosition]

            // Navigate to a detail screen
            val intent = Intent(context, PopularDetailActivity::class.java)
            intent.putExtra(POPULAR_ARTICLE_EXTRA, article)
            context.startActivity(intent)
        }
    }
}
