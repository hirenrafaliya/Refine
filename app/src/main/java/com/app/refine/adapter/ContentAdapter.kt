package com.app.refine.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.refine.R
import com.app.refine.constant.ContentType
import com.app.refine.databinding.ItemBlankBinding
import com.app.refine.databinding.ItemImageBinding
import com.app.refine.databinding.ItemSpaceBinding
import com.app.refine.databinding.ItemTextBinding
import com.app.refine.model.Content
import com.app.refine.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ContentAdapter(private val contentList: MutableList<Content>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "cont_adap_tager"

    private val TEXT = 1
    private val IMAGE = 2
    private val SPACE = 3
    private val BLANK = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TEXT -> {
                TextHolder(
                    ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            IMAGE -> {
                ImageHolder(
                    ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            SPACE -> {
                SpaceHolder(
                    ItemSpaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                BlankHolder(
                    ItemBlankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TEXT -> {
                onBindText(holder as TextHolder)
            }
            IMAGE -> {
                onBindImage(holder as ImageHolder)
            }
            SPACE -> {
                onBindSpace(holder as SpaceHolder)
            }
        }
    }

    override fun getItemCount(): Int = contentList.size

    override fun getItemViewType(position: Int): Int {
        return when (contentList[position].type) {
            ContentType.TEXT -> {
                TEXT
            }
            ContentType.IMAGE -> {
                IMAGE
            }
            ContentType.SPACE -> {
                SPACE
            }
            else -> {
                BLANK
            }
        }
    }

    class TextHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)

    class ImageHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
    class SpaceHolder(val binding: ItemSpaceBinding) : RecyclerView.ViewHolder(binding.root)
    class BlankHolder(val binding: ItemBlankBinding) : RecyclerView.ViewHolder(binding.root)

    private fun onBindText(holder: TextHolder) {
        val position = holder.layoutPosition
        val content = contentList[position]
        val text = content.text

        holder.binding.apply {
            if (text.text != null)
                tv.text = text.text.toHtml()

            if (text.size != null)
                tv.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, text.size.toSdp(holder.itemView.context)
                )

            if (text.color != null)
                tv.setTextColor(text.color.toColor())
            if (text.alignment != null)
                tv.textAlignment = text.alignment.toAlignment()
            if (text.font != null)
                tv.typeface = text.font.toTypeFace(holder.itemView.context)
            if (text.marginStart != null && text.marginTop != null && text.marginEnd != null && text.marginBottom != null) {
                val marginParams = tv.layoutParams as ViewGroup.MarginLayoutParams
                marginParams.setMargins(
                    text.marginStart.toSdp(holder.itemView.context).toInt(),
                    text.marginTop.toSdp(holder.itemView.context).toInt(),
                    text.marginEnd.toSdp(holder.itemView.context).toInt(),
                    text.marginBottom.toSdp(holder.itemView.context).toInt()
                )
                tv.layoutParams = marginParams
            }
            if (text.paddingStart != null && text.paddingTop != null && text.paddingEnd != null && text.paddingBottom != null)
                tv.setPadding(
                    text.paddingStart.toSdp(holder.itemView.context).toInt(),
                    text.paddingTop.toSdp(holder.itemView.context).toInt(),
                    text.paddingEnd.toSdp(holder.itemView.context).toInt(),
                    text.paddingBottom.toSdp(holder.itemView.context).toInt()
                )
        }
    }

    private fun onBindImage(holder: ImageHolder) {
        val position = holder.layoutPosition
        val image = contentList[position].image

        if (image.url != null)
            Glide.with(holder.itemView.context)
                .load(image.url)
                .placeholder(R.drawable.img_placeholder)
                .transition(DrawableTransitionOptions.withCrossFade(800))
                .into(holder.binding.img)
    }

    private fun onBindSpace(holder: SpaceHolder) {

    }
}
