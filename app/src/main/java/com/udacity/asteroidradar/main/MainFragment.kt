package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidAdapter
import com.udacity.asteroidradar.AsteroidListener
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.AsteroidFilters

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter = AsteroidAdapter(AsteroidListener {
            asteroid ->  viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter

        viewModel.navigateToDetailFragment.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                viewModel.onDetailNavigate()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.show_today_menu -> {
                viewModel.asteroidRepository.updateFilter(AsteroidFilters.TODAY)
                true
            }
            R.id.show_week_menu -> {
                viewModel.asteroidRepository.updateFilter(AsteroidFilters.WEEK)
                true
            }
            else -> {
                viewModel.asteroidRepository.updateFilter(AsteroidFilters.ALL)
                true
            }
        }
    }
}
