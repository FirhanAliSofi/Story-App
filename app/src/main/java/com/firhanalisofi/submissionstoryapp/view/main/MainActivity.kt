package com.firhanalisofi.submissionstoryapp.view.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.firhanalisofi.submissionstoryapp.R
import com.firhanalisofi.submissionstoryapp.story.StoryAdapter
import com.firhanalisofi.submissionstoryapp.databinding.ActivityMainBinding
import com.firhanalisofi.submissionstoryapp.view.ViewModelFactory
import com.firhanalisofi.submissionstoryapp.data.response.ListStoryItem
import com.firhanalisofi.submissionstoryapp.data.response.LoginResultModel
import com.firhanalisofi.submissionstoryapp.data.session.LoginPreferences
import com.firhanalisofi.submissionstoryapp.view.detail.DetailStoryActivity
import com.firhanalisofi.submissionstoryapp.view.login.LoginActivity
import com.firhanalisofi.submissionstoryapp.view.maps.MapsActivity
import com.firhanalisofi.submissionstoryapp.helper.Constant.Companion.EXTRA_ID
import com.firhanalisofi.submissionstoryapp.view.add.AddStoryActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var loginPreferences: LoginPreferences
    private lateinit var loginResultModel: LoginResultModel
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginPreferences = LoginPreferences(binding.root.context)
        loginResultModel = loginPreferences.getUser()

        setViewModel()
        setRecycler(binding.root.context)
        getStories()
        onClick()
    }

    private fun setViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(binding.root.context)
    }

    private fun setRecycler(context: Context) {
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            storyAdapter = StoryAdapter()
            adapter = storyAdapter
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun getStories() {
        binding.rvMain.adapter = storyAdapter
        mainViewModel.getAllStory.observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }
    }

    private fun onClick() {
        storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                startActivity(Intent(this@MainActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(EXTRA_ID, data)
                })
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_negativ)) { _, _ ->

        }
        builder.setPositiveButton(getString(R.string.alert_positive)) { _, _ ->
            startActivity(Intent(this@MainActivity, LoginActivity::class.java).also {
                loginPreferences.deleteUser()
                Toast.makeText(this, getString(R.string.logout_succes), Toast.LENGTH_SHORT)
                    .show()
            })
        }
        val alert = builder.create()
        when (item.itemId) {
            R.id.action_logout ->
                alert.show()
            R.id.action_add -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                finish()
            }
            R.id.maps ->
                startActivity(Intent(this, MapsActivity::class.java))
        }
        return true
    }
}