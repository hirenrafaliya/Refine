package com.app.refine.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.refine.constant.ContentType
import com.app.refine.databinding.ItemBlankBinding
import com.app.refine.databinding.ItemImageBinding
import com.app.refine.databinding.ItemSpaceBinding
import com.app.refine.databinding.ItemTextBinding
import com.app.refine.model.Content

class ContentAdapter(private val contentList: MutableList<Content>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            tv.text = text.text ?: ""

            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, text.size.toFloat())
        }
    }

    private fun onBindImage(holder: ImageHolder) {

    }

    private fun onBindSpace(holder: SpaceHolder) {

    }
}
