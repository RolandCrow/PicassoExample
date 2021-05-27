package com.example.picassoexample

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picassoexample.model.SunsetPhoto
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageGalleryAdapter: ImageGalleryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layoutManager = GridLayoutManager(this, 2) // фото по 2 в ряд
        recyclerView = findViewById(R.id.rv_images) // добавляем ресайклер вью из адаптера
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        imageGalleryAdapter = ImageGalleryAdapter(this, SunsetPhoto.getSunsetPhotos()) //
    // получить фото из модели


    }

    override fun onStart() {
        super.onStart()
        recyclerView.adapter = imageGalleryAdapter // при старте соединяем адаптер с фото к
    // рейсайклер вью
    }


    private inner class ImageGalleryAdapter(val context: Context, val sunsetPhotos: // класс для
    // ресайклер вью
    Array<SunsetPhoto>) : RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ImageGalleryAdapter.MyViewHolder {
            val context = parent.context
            val inflater = LayoutInflater.from(context)
            val photoView = inflater.inflate(R.layout.item_image, parent, false)
            return MyViewHolder(photoView)
        }

        override fun onBindViewHolder(holder: ImageGalleryAdapter.MyViewHolder, position: Int) {
            val sunsetPhoto = sunsetPhotos[position]
            val imageView = holder.photoImageView // загрузка фото при прокручивании экрана
        // пользователем

            Picasso.get()
                .load(sunsetPhoto.url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(imageView)
        }

        override fun getItemCount(): Int {
           return sunsetPhotos.size
        }


        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View //
        // внутренний класс холдер сопутствующий ресайклер вью
        .OnClickListener {

            var photoImageView: ImageView  = itemView.findViewById(R.id.iv_photo)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(view: View?) { // при клике переход в другую активность
                val position = bindingAdapterPosition
                if(position != RecyclerView.NO_POSITION) {

                    val sunsetPhoto = sunsetPhotos[position]
                    val intent = Intent(context, SunsetPhotoActivity::class.java).apply {
                        putExtra(SunsetPhotoActivity.EXTRA_SUNSET_PHOTO, sunsetPhoto) // явный
                    // интент с объектом выбранной фотографии

                    }
                    startActivity(intent)
                }


            }

        }

    }
}