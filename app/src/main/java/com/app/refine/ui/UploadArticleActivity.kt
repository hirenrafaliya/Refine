package com.app.refine.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.adapter.ContentAdapter
import com.app.refine.constant.*
import com.app.refine.databinding.ActivityUploadArticleBinding
import com.app.refine.model.*
import com.app.refine.repository.UploadRepository
import com.app.refine.utils.Utils
import com.app.refine.viewmodel.UploadArticleViewModel
import com.google.gson.Gson
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
        setUploadArticle()
    }

    private fun setUploadArticle() {
        binding.tvUpload.setOnClickListener {
            Toast.makeText(this@UploadArticleActivity, "uploading...", Toast.LENGTH_SHORT).show()
            UploadRepository().uploadArticle(article)
        }
    }

    private fun initArticle() {
        article =
            Article(
                null,
                ArticleType.ARTICLE,
                Display("", "", ""),
                mutableListOf(),
                0,
                Metadata(Utils.getCurrentDate())
            )
    }

    private fun initRecyclerView(contentList: MutableList<Content>) {
        binding.recyclerView.apply {
            adapter = ContentAdapter(contentList)
            layoutManager = LinearLayoutManager(this@UploadArticleActivity)
        }
    }

    private fun setActions() {
        contentList.add(Content("BLANK", null, null, null))

        binding.apply {
            tvTypeText.setOnClickListener {
                layoutAddText.visibility = View.VISIBLE
                layoutAddImage.visibility = View.INVISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE

                contentList[position].type = ContentType.TEXT

                setLayoutAddText()
            }

            tvTypeImage.setOnClickListener {
                layoutAddText.visibility = View.INVISIBLE
                layoutAddImage.visibility = View.VISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE

                contentList[position].type = ContentType.IMAGE

                setLayoutAddImage()
            }

            tvTypeDisplay.setOnClickListener {
                layoutAddText.visibility = View.VISIBLE
                layoutAddImage.visibility = View.INVISIBLE
                layoutAddDisplay.visibility = View.INVISIBLE

                setLayoutAddDisplay()
            }

            tvAddContent.setOnClickListener {
                addContentToList()

                resetItems()
            }

        }
    }

    private fun addContentToList() {
        article.contentList.add(contentList[position])

        position++
        contentList.add(Content("BLANK", null, null, null))
        binding.apply {
            layoutAddText.visibility = View.GONE
            layoutAddImage.visibility = View.GONE
            layoutAddDisplay.visibility = View.GONE
        }

        val gson = Gson()
        gson.serializeNulls()
        Log.d(TAG, "article: ${gson.toJson(contentList[position - 1])}")
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
        }

    }

    private fun setLayoutAddText() {

        contentList[position] = Content(
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
                    contentList[position].text?.text = it.toString()
            }
            edtTextSize.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.size = it.toString().toInt()
            }
            edtTextFont.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.font = it.toString()
            }
            edtTextColor.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.color = it.toString()
            }

            edtTextMarginStart.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.marginStart = it.toString().toInt()
            }
            edtTextMarginTop.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.marginTop = it.toString().toInt()
            }
            edtTextMarginEnd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.marginEnd = it.toString().toInt()
            }
            edtTextMarginBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.marginBottom = it.toString().toInt()
            }

            edtTextPaddingStart.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.paddingStart = it.toString().toInt()
            }
            edtTextPaddingTop.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.paddingTop = it.toString().toInt()
            }
            edtTextPaddingEnd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.paddingEnd = it.toString().toInt()
            }
            edtTextPaddingBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].text?.paddingBottom = it.toString().toInt()
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
        contentList[position] = Content(
            ContentType.IMAGE,
            null,
            Image("", 8, 8, 8, 8, 2, 8, null),
            null
        )
        binding.apply {
            edtImageUrl.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.url = it.toString()
            }

            edtImageCornerRadius.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.cornerRadius = it.toString().toInt()
            }

            edtImageElevation.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.elevation = it.toString().toInt()
            }

            edtImageMarginBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.marginBottom = it.toString().toInt()
            }

            edtImageMarginStart.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.marginStart = it.toString().toInt()
            }
            edtImageMarginTop.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.marginTop = it.toString().toInt()
            }
            edtImageMarginEnd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.marginEnd = it.toString().toInt()
            }
            edtImageMarginBottom.addTextChangedListener {
                if (it.toString().isNotBlank())
                    contentList[position].image?.marginBottom = it.toString().toInt()
            }
        }


    }

    private fun setLayoutAddDisplay() {
        binding.apply {
            edtDisplayTitle.addTextChangedListener {
                if (it.toString().isNotBlank())
                    article.display.title = it.toString()
            }
            edtDisplayDescription.addTextChangedListener {
                if (it.toString().isNotBlank())
                    article.display.description = it.toString()
            }
            edtImageUrl.addTextChangedListener {
                if (it.toString().isNotBlank())
                    article.display.description = it.toString()
            }
        }
    }

    private val contentList = mutableListOf<Content>()
    private var position = 0
    fun getCurrentContent(): Content {
        return contentList[position]
    }
}