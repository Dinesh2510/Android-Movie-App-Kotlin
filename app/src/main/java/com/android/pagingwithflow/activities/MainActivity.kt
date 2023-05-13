package com.android.pagingwithflow.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pagingwithflow.Utlis.ProgressBarHandler
import com.android.pagingwithflow.adapter.DiscoverMovieCardAdapter
import com.android.pagingwithflow.adapter.GenreAdapter
import com.android.pagingwithflow.adapter.MovieSliderAdapter
import com.android.pagingwithflow.adapter.PopularMovieAdapter
import com.android.pagingwithflow.databinding.ActivityMainBinding
import com.android.pagingwithflow.repo.DiscoverMoveViewModel
import com.android.pagingwithflow.repo.MovieViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: DiscoverMoveViewModel by viewModels()
    private val movieViewModel: MovieViewModel by viewModels()
    var progressBar: ProgressBarHandler? = null

    lateinit var movieSliderAdapter: MovieSliderAdapter
    lateinit var popularMovieAdapter: PopularMovieAdapter
    lateinit var genreAdapter: GenreAdapter

    /*   @Inject
       lateinit var discoverMovieAdapter: DiscoverMovieAdapter */
    @Inject
    lateinit var discoverMovieCardAdapter: DiscoverMovieCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //   title = "TMDB APP"
        progressBar = ProgressBarHandler(this)
        progressBar!!.show()
        movieViewModel.getUpcomingMovieResult()
        movieViewModel.getPopularMovieResult()
        movieViewModel.getTopRatedMovieResult()
        movieViewModel.getGenreMovieResult()
        observeViewModel()
        binding.MovieSeeAllMovie.setOnClickListener {
            val intent = Intent(this, DiscoverAllMovie::class.java)
            startActivity(intent)
        }

    }

    private fun observeViewModel() {
        movieViewModel.upcomingLiveData.observe(this@MainActivity, Observer { response ->
            movieSliderAdapter = MovieSliderAdapter(this@MainActivity, response)
            binding.apply {
                progressBar!!.hide()
                imageSliderMovieFragment.setSliderAdapter(movieSliderAdapter)
                imageSliderMovieFragment.setIndicatorAnimation(IndicatorAnimationType.WORM)
                imageSliderMovieFragment.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
                imageSliderMovieFragment.startAutoCycle()
            }
        })
        movieViewModel.popularLiveData.observe(this@MainActivity, Observer { response ->
            movieSliderAdapter = MovieSliderAdapter(this@MainActivity, response)
            binding.apply {
                popularMovieAdapter = PopularMovieAdapter(this@MainActivity, response)
                recyclerviewPopular.apply {
                    adapter = popularMovieAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }
            }
        })
        movieViewModel.topRatedLiveData.observe(this@MainActivity, Observer { response ->
            movieSliderAdapter = MovieSliderAdapter(this@MainActivity, response)
            binding.apply {
                popularMovieAdapter = PopularMovieAdapter(this@MainActivity, response)
                recyclerviewTopRated.apply {
                    adapter = popularMovieAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }
            }
        })
        movieViewModel.genreLiveData.observe(this@MainActivity, Observer { response ->
            genreAdapter = GenreAdapter(this@MainActivity, response)
            binding.apply {
                genreAdapter = GenreAdapter(this@MainActivity, response)
                recyclerviewGenre.apply {
                    adapter = genreAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }
            }
        })
        lifecycleScope.launch {
            mainViewModel.discoverMovieResult.collectLatest { response ->
                binding.apply {
                    recyclerviewDiscover.apply {
                        adapter = discoverMovieCardAdapter
                        layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )

                    }

                }
                discoverMovieCardAdapter.submitData(response)

            }


        }
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerviewPopular.apply {
                setHasFixedSize(true)
                layoutManager =
                    GridLayoutManager(this@MainActivity, 3, LinearLayoutManager.HORIZONTAL, false)
                /*
                                adapter = discoverMovieAdapter.withLoadStateHeaderAndFooter(
                                    header = LoaderStateAdapter { discoverMovieAdapter::retry },
                                    footer = LoaderStateAdapter { discoverMovieAdapter::retry }
                                )
                */
            }
        }
    }


}

