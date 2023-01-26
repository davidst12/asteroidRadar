package com.udacity.asteroidradar

import android.accounts.AccountManager.get
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ProcessLifecycleOwner.get
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.potentially_hazardous_asteroid_image)

    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("asteroidList")
fun bindAsteroidList(recyclerView: RecyclerView, asteroidList: List<Asteroid>?){
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(asteroidList)
}

@BindingAdapter("pictureOfTheDayImage")
fun bindPictureOfTheDay(imageView: ImageView, image: PictureOfDay?){
    if(image?.mediaType == "image"){
        Picasso.with(imageView.context).load(image.url).into(imageView)
        val context = imageView.context
        imageView.contentDescription = String.format(context.getString(R.string.nasa_picture_of_day_content_description_format, image.title))
    }else{
        Log.i("BindingAdapters", "Is not a picture -> ${image?.mediaType}")
    }
}
