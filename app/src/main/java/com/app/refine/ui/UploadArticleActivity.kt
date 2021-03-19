package com.app.refine.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.adapter.ContentAdapter
import com.app.refine.constant.*
import com.app.refine.databinding.ActivityUploadArticleBinding
import com.app.refine.model.*
import com.app.refine.viewmodel.UploadArticleViewModel
import java.util.*

class UploadArticleActivity : AppCompatActivity() {
    private val TAG = "uart_actv_tager"

    private lateinit var binding: ActivityUploadArticleBinding
    private lateinit var viewModel: UploadArticleViewModel
    private lateinit var article: Article
    private lateinit var content: Content


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                UploadArticleViewModel::class.java
            )

        initArticle()
        initRecyclerView(article.contentList)
        setActions()
    }

    private fun initArticle() {
        article =
            Article(null, ArticleType.ARTICLE, Display("", "", ""), mutableListOf(), Date(), 0)
    }

    private fun initRecyclerView(contentList: MutableList<Content>) {
        binding.recyclerView.apply {
            adapter = ContentAdapter(contentList)
            layoutManager = LinearLayoutManager(this@UploadArticleActivity)
        }
    }

    private fun setActions() {

        content = Content(
            ContentType.TEXT,
            null,
            null,
            null
        )

        binding.apply {
            tvTypeText.setOnClickListener {
                layoutAddText.visibility = View.VISIBLE
                layoutAddImage.visibility = View.INVISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE

                content.type = ContentType.TEXT

                setLayoutAddText()
            }

            tvTypeImage.setOnClickListener {
                layoutAddText.visibility = View.INVISIBLE
                layoutAddImage.visibility = View.VISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE

                content.type = ContentType.IMAGE

                setLayoutAddImage()
            }

            tvTypeDisplay.setOnClickListener {
                layoutAddText.visibility = View.VISIBLE
                layoutAddImage.visibility = View.INVISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE
            }

            tvAddContent.setOnClickListener {
                addContentToList()

                resetItems()
            }

        }
    }

    private fun resetItems() {
        binding.apply {
            edtTextText.text.clear()
            edtTextSize.text.clear()
            edtTextFont.text.clear()
            edtTextColor.text.clear()
            edtTextMarginStart.text.clear()
            edtTextMarginTop.text.clear()
            edtTextMarginEnd.text.clear()
            edtTextMarginBottom.text.clear()

            edtTextPaddingStart.text.clear()
            edtTextPaddingTop.text.clear()
            edtTextPaddingEnd.text.clear()
            edtTextPaddingBottom.text.clear()

            edtImageUrl.text.clear()

            edtDisplayTitle.text.clear()
            edtDisplayDescription.text.clear()
            edtDisplayImg.text.clear()
        }

    }

    private fun addContentToList() {
        article.contentList.add(content)

        binding.apply {
            layoutAddText.visibility = View.GONE
            layoutAddImage.visibility = View.GONE
            layoutAddDisplay.visibility = View.GONE
        }

        Log.d(TAG, "article: $article")

    }

    private fun setLayoutAddText() {

        content = Content(
            ContentType.TEXT,
            Text(
                "",
                14,
                ColorType.BLACK_S,
                AlignmentType.TEXT_START,
                FontType.REGULAR,
                8,
                0,
                8,
                0,
                0,
                0,
                0,
                0,
                WidthType.MATCH_PARENT,
                0.0f,
                null,
                null
            ),
            null,
            null
        )
        binding.apply {
            edtTextText.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.text = it.toString()
            }
            edtTextSize.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.size = it.toString().toInt()
            }
            edtTextFont.addTextChangedListener {
                content.text?.font = it.toString()
            }
            edtTextColor.addTextChangedListener {
                content.text?.color = it.toString()
            }

            edtTextMarginStart.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.marginStart = it.toString().toInt()
            }
            edtTextMarginTop.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.marginTop = it.toString().toInt()
            }
            edtTextMarginEnd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.marginEnd = it.toString().toInt()
            }
            edtTextMarginBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.marginBottom = it.toString().toInt()
            }

            edtTextPaddingStart.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.paddingStart = it.toString().toInt()
            }
            edtTextPaddingTop.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.paddingTop = it.toString().toInt()
            }
            edtTextPaddingEnd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.paddingEnd = it.toString().toInt()
            }
            edtTextPaddingBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    content.text?.paddingBottom = it.toString().toInt()
            }

            tvTextFontBold.setOnClickListener {
                edtTextFont.setText(FontType.BOLD)
            }
            tvTextFontRegular.setOnClickListener {
                edtTextFont.setText(FontType.REGULAR)
            }

            tvTextColorBlackPrimary.setOnClickListener {
                edtTextColor.setText(ColorType.BLACK_P)
            }
            tvTextColorBlackSecondary.setOnClickListener {
                edtTextColor.setText(ColorType.BLACK_S)
            }
            tvTextColorBluePrimary.setOnClickListener {
                edtTextColor.setText(ColorType.BLUE_P)
            }
            tvTextColorBlueSecondary.setOnClickListener {
                edtTextColor.setText(ColorType.BLUE_S)
            }

        }
    }

    private fun setLayoutAddImage() {
        content = Content(
            ContentType.TEXT,
            null,
            Image("", null),
            null
        )
        binding.apply {
            edtImageUrl.addTextChangedListener {
                content.image?.url = it.toString()
            }
        }
    }
}