package com.app.refine.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.adapter.ContentAdapter
import com.app.refine.constant.*
import com.app.refine.databinding.ActivityUploadArticleBinding
import com.app.refine.listener.OnContentItemClickListener
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
    private lateinit var adapter: ContentAdapter


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
        setWeight()
    }

    private fun setWeight() {
        var weight = 1.5f

        binding.tvX.setOnClickListener {
            when (weight) {
                1.5f -> {
                    weight = 2.0f
                    val param = binding.layoutAdd.layoutParams as ConstraintLayout.LayoutParams
                    param.verticalWeight = weight
                    binding.layoutAdd.layoutParams = param
                }
                2.0f -> {
                    weight = 4.0f
                    val param = binding.layoutAdd.layoutParams as ConstraintLayout.LayoutParams
                    param.verticalWeight = weight
                    binding.layoutAdd.layoutParams = param
                }
                4.0f -> {
                    weight = 0.5f
                    val param = binding.layoutAdd.layoutParams as ConstraintLayout.LayoutParams
                    param.verticalWeight = weight
                    binding.layoutAdd.layoutParams = param
                }
                0.5f -> {
                    weight = 1.5f
                    val param = binding.layoutAdd.layoutParams as ConstraintLayout.LayoutParams
                    param.verticalWeight = weight
                    binding.layoutAdd.layoutParams = param
                }
            }
        }
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
                Metadata(Utils.getCurrentDate(),"")
            )
    }

    private fun initRecyclerView(contentList: MutableList<Content>) {
        adapter = ContentAdapter(contentList, listener)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this@UploadArticleActivity)
    }

    private fun setActions() {
        contentList.add(Content("BLANK", null, null, null))
        binding.tvStatus.text = "P:$position"

        binding.apply {
            tvTypeText.setOnClickListener {
                layoutAddText.visibility = View.VISIBLE
                layoutAddImage.visibility = View.GONE
                layoutAddDisplay.visibility = View.GONE

                contentList[position].type = ContentType.TEXT

                setLayoutAddText()
            }

            tvTypeImage.setOnClickListener {
                layoutAddImage.visibility = View.VISIBLE
                layoutAddText.visibility = View.GONE
                layoutAddDisplay.visibility = View.GONE


                contentList[position].type = ContentType.IMAGE

                setLayoutAddImage()
            }

            tvTypeDisplay.setOnClickListener {
                layoutAddDisplay.visibility = View.VISIBLE
                layoutAddText.visibility = View.GONE
                layoutAddImage.visibility = View.GONE

                setLayoutAddDisplay()
            }

            tvAddContent.setOnClickListener {
                addContentToList()

                resetItems()
            }

        }
    }

    private fun addContentToList() {
        if (temp == -1) {
            article.contentList.add(contentList[position])

            position++
            binding.tvStatus.text = "P:$position"

            contentList.add(Content("BLANK", null, null, null))

        } else {
            article.contentList.removeAt(position)
            article.contentList.add(position, contentList[position])
            adapter.notifyItemChanged(position)

            position = temp
            temp = -1

            binding.tvStatus.text = "P:$position"
        }


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
                8,
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
            edtDisplayImg.addTextChangedListener {
                if (it.toString().isNotBlank())
                    article.display.img = it.toString()
            }
            edtInd.addTextChangedListener {
                if (it.toString().isNotBlank())
                    article.ind = it.toString().toInt()
            }
            edtTempId.addTextChangedListener{
                if (it.toString().isNotBlank())
                    article.metadata.tempId = it.toString()
            }
        }
    }

    private val contentList = mutableListOf<Content>()
    private var position = 0
    private var temp = -1

    private val listener = object : OnContentItemClickListener {
        @SuppressLint("SetTextI18n")
        override fun onContentItemClick(pos: Int) {
            if (temp == -1) {
                temp = position
                position = pos

                binding.tvStatus.text = "P:$position T:$temp"

                val content = contentList[position]

                if (content.type == ContentType.TEXT && content.text != null) {
                    binding.apply {
                        layoutAddText.visibility = View.VISIBLE
                        layoutAddImage.visibility = View.GONE
                        layoutAddDisplay.visibility = View.GONE

                        edtTextText.setText(content.text.text)
                        edtTextSize.setText(content.text.size.toString())
                        edtTextFont.setText(content.text.font)
                        edtTextColor.setText(content.text.color)
                        edtTextMarginStart.setText(content.text.marginStart.toString())
                        edtTextMarginTop.setText(content.text.marginTop.toString())
                        edtTextMarginEnd.setText(content.text.marginEnd.toString())
                        edtTextMarginBottom.setText(content.text.marginBottom.toString())
                        edtTextPaddingStart.setText(content.text.paddingStart.toString())
                        edtTextPaddingTop.setText(content.text.paddingTop.toString())
                        edtTextPaddingEnd.setText(content.text.paddingEnd.toString())
                        edtTextPaddingBottom.setText(content.text.paddingBottom.toString())
                    }
                } else if (content.type == ContentType.IMAGE && content.image != null) {
                    binding.apply {
                        layoutAddText.visibility = View.GONE
                        layoutAddImage.visibility = View.VISIBLE
                        layoutAddDisplay.visibility = View.GONE

                        edtImageUrl.setText(content.image.url)
                        edtImageCornerRadius.setText(content.image.cornerRadius.toString())
                        edtImageElevation.setText(content.image.elevation.toString())
                        edtImageMarginStart.setText(content.image.marginStart.toString())
                        edtImageMarginTop.setText(content.image.marginTop.toString())
                        edtImageMarginEnd.setText(content.image.marginEnd.toString())
                        edtImageMarginBottom.setText(content.image.marginBottom.toString())
                    }
                }
            }
        }
    }
}