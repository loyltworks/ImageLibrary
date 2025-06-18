package com.loyltworks.showimage

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL

class ShowImage {

    fun showImage(context: Context, url: String, imageview: ImageView) {

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                 withContext(Dispatchers.Main) {
                    imageview.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                imageview.setImageResource(R.drawable.user)
            }
        }

    }
}