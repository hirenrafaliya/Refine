package com.app.refine.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.refine.R
import com.app.refine.constant.ArticleType
import com.app.refine.databinding.ItemArticleBinding
import com.app.refine.databinding.ItemBlankBinding
import com.app.refine.model.Article
import com.app.refine.ui.ContentActivity
import com.app.refine.utils.toHtml
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.bson.BsonObjectId
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

class ArticleAdapter(val articleList: MutableList<Article>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "artc_adap_tager"
    private val ARTICLE = 1
    private val BLANK = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ARTICLE) {
            return ArticleHolder(
                ItemArticleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            return BlankHolder(
                ItemBlankBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ARTICLE) {
            onBindArticle(holder as ArticleHolder)
        }
    }

    override fun getItemCount(): Int = articleList.size

    override fun getItemViewType(position: Int): Int {
        return if (articleList[position].type == ArticleType.ARTICLE) {
            ARTICLE
        } else {
            BLANK
        }
    }

    class ArticleHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
    class BlankHolder(val binding: ItemBlankBinding) : RecyclerView.ViewHolder(binding.root)


    private fun onBindArticle(holder: ArticleHolder) {
        val position = holder.layoutPosition
        val article = articleList[position]

        holder.binding.layoutParent.clipToOutline = true

        Glide.with(holder.itemView.context)
            .load(article.display.img)
            .placeholder(R.drawable.img_placeholder)
            .transition(DrawableTransitionOptions.withCrossFade(400))
            .into(holder.binding.imgDisplay)

        holder.binding.tvTitle.text = article.display.title.toHtml()
        holder.binding.tvDescription.text = article.display.description.toHtml()

        holder.binding.layoutParent.setOnClickListener {
            val intent = Intent(holder.itemView.context, ContentActivity::class.java)
            intent.putExtra("article", articleList[position])
            holder.itemView.context.startActivity(intent)
        }

        Log.d(TAG, "onBindArticle toString: ${article._id.toString()}")
    }
}
