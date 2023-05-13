package com.android.pagingwithflow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pagingwithflow.Utlis.ProgressBarHandler
import com.android.pagingwithflow.adapter.DiscoverMovieAdapter
import com.android.pagingwithflow.adapter.LoaderStateAdapter
import com.android.pagingwithflow.databinding.ActivityDiscoverAllMovieBinding
import com.android.pagingwithflow.databinding.ActivityMovieDetailsBinding
import com.android.pagingwithflow.repo.DiscoverMoveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverAllMovie : AppCompatActivity() {
    private lateinit var binding: ActivityDiscoverAllMovieBinding
    private val mainViewModel: DiscoverMoveViewModel by viewModels()

    var progressBar: ProgressBarHandler? = null

    @Inject
    lateinit var discoverMovieAdapter: DiscoverMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverAllMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "TMDB APP"
        progressBar = ProgressBarHandler(this)
        progressBar!!.show()
        initRecyclerview()

        lifecycleScope.launch {
            mainViewModel.discoverMovieResult.collectLatest { response ->
                progressBar?.hide()
                discoverMovieAdapter.submitData(response)

            }
        }
    }


    private fun initRecyclerview() {
        binding.apply {
            recyclerviewAll.apply {
                layoutManager =
                    LinearLayoutManager(this@DiscoverAllMovie, LinearLayoutManager.VERTICAL, false)
                adapter = discoverMovieAdapter.withLoadStateHeaderAndFooter(
                    header = LoaderStateAdapter { discoverMovieAdapter::retry },
                    footer = LoaderStateAdapter { discoverMovieAdapter::retry }
                )
            }
        }
    }


}

