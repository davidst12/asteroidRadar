package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val clickListener: AsteroidListener): ListAdapter<Asteroid, AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        holder.bindAsteroid(getItem(position)!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
            return AsteroidViewHolder.from(parent)
    }
}

class AsteroidViewHolder private constructor(val binding: AsteroidItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bindAsteroid(item: Asteroid, clickListener: AsteroidListener) {
        binding.asteroid = item
        binding.executePendingBindings()
        binding.clickListener = clickListener
    }

    companion object {
        fun from(parent: ViewGroup): AsteroidViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = AsteroidItemBinding.inflate(layoutInflater, parent, false)

            return AsteroidViewHolder(binding)
        }
    }
}

class AsteroidDiffCallback: DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}