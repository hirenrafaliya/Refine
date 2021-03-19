package com.app.refine.adapter

import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.app.refine.R
import com.app.refine.constant.ActionType
import com.app.refine.constant.ContentType
import com.app.refine.constant.EvenType
import com.app.refine.databinding.ItemBlankBinding
import com.app.refine.databinding.ItemImageBinding
import com.app.refine.databinding.ItemSpaceBinding
import com.app.refine.databinding.ItemTextBinding
import com.app.refine.listener.OnContentItemClickListener
import com.app.refine.model.Content
import com.app.refine.utils.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import top.defaults.drawabletoolbox.DrawableBuilder


class ContentAdapter(
    private val contentList: MutableList<Content>,
    private val listener: OnContentItemClickListener? = null
) :
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

    override fun getItemCount(): Int {
        return contentList.size
    }

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
        val context = holder.itemView.context

        holder.binding.apply {
            if (text != null) {
                if (text.text != null)
                    tv.text = text.text.toHtml()

                if (text.size != null)
                    tv.setTextSize(
                        TypedValue.COMPLEX_UNIT_PX, text.size.toSdp(context)
                    )

                if (text.color != null)
                    tv.setTextColor(text.color.toColor())
                if (text.alignment != null)
                    tv.textAlignment = text.alignment.toAlignment()
                if (text.font != null)
                    tv.typeface = text.font.toTypeFace(context)
                if (text.marginStart != null && text.marginTop != null && text.marginEnd != null && text.marginBottom != null) {
                    val marginParams = tv.layoutParams as ViewGroup.MarginLayoutParams
                    marginParams.setMargins(
                        text.marginStart.toSdp(context).toInt(),
                        text.marginTop.toSdp(context).toInt(),
                        text.marginEnd.toSdp(context).toInt(),
                        text.marginBottom.toSdp(context).toInt()
                    )
                    tv.layoutParams = marginParams
                }
                if (text.paddingStart != null && text.paddingTop != null && text.paddingEnd != null && text.paddingBottom != null)
                    tv.setPadding(
                        text.paddingStart.toSdp(context).toInt(),
                        text.paddingTop.toSdp(context).toInt(),
                        text.paddingEnd.toSdp(context).toInt(),
                        text.paddingBottom.toSdp(context).toInt()
                    )

                if (text.width != null)
                    if (text.width == "wrap_content") {
                        val params = holder.binding.tv.layoutParams as ViewGroup.LayoutParams
                        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                        holder.binding.tv.layoutParams = params
                    }

                if (text.horizontalBias != null) {
                    val params = holder.binding.tv.layoutParams as ConstraintLayout.LayoutParams
                    params.horizontalBias = text.horizontalBias
                    holder.binding.tv.layoutParams = params
                }

                if (text.action != null) {
                    if (text.action?.event == EvenType.ONCLICK) {
                        if (text.action?.type == ActionType.BROWSE) {
                            holder.binding.layoutParent.setOnClickListener {
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(text?.action!!.link)
                                    )
                                )
                            }
                        } else if (text.action?.type != null) {
                            Toast.makeText(
                                context,
                                "This action is not supported in this version of the app",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                if (text.background != null) {
                    val background = DrawableBuilder()
                    text.background?.apply {
                        if (cornerRadius != 0)
                            background.cornerRadius(cornerRadius.toSdp(context).toInt())
                        if (solidColor != null)
                            background.solidColor(solidColor.toColor())
                        if (strokeColor != null)
                            background.strokeColor(strokeColor.toColor())
                        if (strokeWidth != 0)
                            background.strokeWidth(strokeWidth.toSdp(context).toInt())
                        if (isRipple) background.ripple(true)
                        else background.ripple(false)
                        if (rippleColor != null)
                            background.rippleColor(rippleColor.toColor())
                    }
                }
            }
        }

        if (listener != null) {
            holder.binding.layoutParent.setOnClickListener {
                listener.onContentItemClick(position)
            }
        }
    }

    private fun onBindImage(holder: ImageHolder) {
        val position = holder.layoutPosition
        val image = contentList[position].image
        val context = holder.itemView.context

        if (image != null) {
            if (image.url != null)
                Glide.with(holder.itemView.context)
                    .load(image.url)
                    .placeholder(R.drawable.img_placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade(800))
                    .into(holder.binding.img)

            if (image.marginStart != null && image.marginTop != null && image.marginEnd != null && image.marginBottom != null) {
                val marginParams = holder.binding.img.layoutParams as ViewGroup.MarginLayoutParams
                marginParams.setMargins(
                    image.marginStart.toSdp(context).toInt(),
                    image.marginTop.toSdp(context).toInt(),
                    image.marginEnd.toSdp(context).toInt(),
                    image.marginBottom.toSdp(context).toInt()
                )
                holder.binding.img.layoutParams = marginParams
            }

            if (image.cornerRadius != 0) {
                holder.binding.img.setCornerRadius(image.cornerRadius.toSdp(context).toInt())
            }

            if (image.elevation != 0) {
                holder.binding.img.elevation = image.elevation.toFloat()
            }

            if (image.action != null) {
                if (image.action.type == ActionType.BROWSE) {
                    if (image.action.event == EvenType.ONCLICK) {
                        holder.binding.layoutParent.setOnClickListener {
                            holder.itemView.context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(image.action.link)
                                )
                            )
                        }
                    }
                } else if (image.action.type != null) {
                    Toast.makeText(
                        holder.itemView.context,
                        "This action is not supported in this version of the app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        if (listener != null) {
            holder.binding.layoutParent.setOnClickListener {
                listener.onContentItemClick(position)
            }
        }
    }

    private fun onBindSpace(holder: SpaceHolder) {
        val position = holder.layoutPosition
        val space = contentList[position].space

        if (space != null) {
            if (space.height != null) {
                val params = holder.binding.layoutParent.layoutParams
                params.height = space.height.toSdp(holder.itemView.context).toInt()
                holder.binding.layoutParent.layoutParams = params
            }
        }

        if (listener != null) {
            holder.binding.layoutParent.setOnClickListener {
                listener.onContentItemClick(position)
            }
        }
    }
}
