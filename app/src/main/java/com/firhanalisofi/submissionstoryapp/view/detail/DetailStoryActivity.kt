package com.firhanalisofi.submissionstoryapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.firhanalisofi.submissionstoryapp.databinding.ActivityDetailStoryBinding
import com.firhanalisofi.submissionstoryapp.data.response.ListStoryItem
import com.firhanalisofi.submissionstoryapp.helper.Constant.Companion.EXTRA_ID

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDetail()
    }

    private fun getDetail() {
        val getData = intent.getParcelableExtra<ListStoryItem>(EXTRA_ID) as ListStoryItem
        binding?.apply {
            tvname.text = getData.name
            tvDate.text = getData.createdAt.toString()
            tvDesc.text = getData.description
            Glide.with(this@DetailStoryActivity)
                .load(getData.photoUrl)
                .into(ivHero)
        }
    }
}
