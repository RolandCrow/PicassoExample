package com.example.picassoexample

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.example.picassoexample.model.SunsetPhoto
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class SunsetPhotoActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_SUNSET_PHOTO = "SunsetPhotoActivity.EXTRA_SUNSET_PHOTO"
    }

    private lateinit var imageView: ImageView
    private lateinit var sunsetPhoto: SunsetPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sunset_photo)

        sunsetPhoto = intent.getParcelableExtra(EXTRA_SUNSET_PHOTO)!!
        imageView = findViewById(R.id.image)




    }

    override fun onStart() {

        super.onStart()
        Picasso.get()
            .load(sunsetPhoto.url)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .fit()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    val bitmap = (imageView.drawable as BitmapDrawable).bitmap
                    onPalette(Palette.from(bitmap).generate())

                }

                override fun onError(e: Exception?) {

                }
            })
    }

    fun onPalette(palette: Palette?) { if (null != palette) { val parent = imageView.parent.parent as ViewGroup
        parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY)) }
}}