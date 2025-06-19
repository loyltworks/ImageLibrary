package com.loyltworks.showimage

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.*
import java.net.URL

class ShowImage {

    fun showImage(context: Context, url: String, imageview: ImageView) {
        imageview.setImageResource(R.drawable.user)
        imageview.tag = url



        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                withContext(Dispatchers.Main) {
                    if (imageview.tag == url) {
                        imageview.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    if (imageview.tag == url) {
                        imageview.setImageResource(R.drawable.user)
                    }
                }
            }
        }

    }
}