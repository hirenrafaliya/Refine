package com.app.refine.custom

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.app.refine.R
import com.app.refine.databinding.DialogIosBinding

class IosDialog(val context: Context, private val container: ViewGroup) {

    private var binding: DialogIosBinding =
        DialogIosBinding.inflate((context as Activity).layoutInflater)
    private var isCancelable = true

    init {
        if (isCancelable)
            binding.viewBg.setOnClickListener { dismiss() }

        binding.tvSecondary.setOnClickListener { dismiss() }
    }

    fun show() {
        container.removeAllViews()
        container.visibility = View.VISIBLE
        container.addView(binding.root)

        startAnimation(binding.viewBg, R.anim.anim_fade_in)
        startAnimation(binding.layoutDialog, R.anim.anim_dialog_show)
    }

    fun dismiss() {
        startAnimation(binding.layoutDialog, R.anim.anim_dialog_dismiss)
        val animation = getAnimation(R.anim.anim_fade_out)
        animation.duration = 450
        binding.viewBg.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                container.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
    }

    fun setData(
        title: String = "",
        desc: String = "",
        primaryBtnText: String = "Ok",
        secondaryBtnText: String = "Cancel"
    ) {
        binding.tvTitle.text = title
        binding.tvDesc.text = desc
        binding.tvPrimary.text = primaryBtnText
        binding.tvSecondary.text = secondaryBtnText
    }

    fun setPrimaryBtnOnClickListener(listener: View.OnClickListener) {
        binding.tvPrimary.setOnClickListener(listener)
    }

    fun setIsCancelable(isCancelable: Boolean) {
        if (!isCancelable)
            binding.viewBg.setOnClickListener(null)
    }

    fun hideSecondaryBtn(){
        binding.tvSecondary.visibility=View.GONE
        binding.viewVertical.visibility=View.GONE
    }

    private fun startAnimation(v: View, id: Int): Animation {
        val animation = AnimationUtils.loadAnimation(context, id)
        v.startAnimation(animation)
        return animation
    }

    private fun getAnimation(id: Int): Animation = AnimationUtils.loadAnimation(context, id)


}