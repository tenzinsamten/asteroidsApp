package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(
            this,
            AsteroidViewModelFactory(activity.application)
        ).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var asteroidAdapter: AsteroidAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        asteroidAdapter = AsteroidAdapter(AsteroidAdapter.AsteroidListener {
            it.let {
                findNavController().navigate(MainFragmentDirections.actionShowDetail(it))

            }
        })
        binding.asteroidRecycler.adapter = asteroidAdapter
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer { asteroidList ->
            asteroidList?.let {
                binding.statusLoadingWheel.visibility = View.GONE
                asteroidAdapter?.submitList(asteroidList)
            }
        })

        viewModel.photoOfTheDay.observe(viewLifecycleOwner, Observer { photo ->
            photo?.let {
                if (photo.mediaType == "image") {
                    Picasso.with(context)
                        .load(photo.url)
                        .into(binding.activityMainImageOfTheDay)
                } else {
                    Picasso.with(context)
                        .load("https://images01.military.com/sites/default/files/styles/full/public/2019-07/NASAmeatball1200.jpg")
                        .into(binding.activityMainImageOfTheDay)
                }
            }
        })
        binding.statusLoadingWheel.visibility = View.VISIBLE

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
