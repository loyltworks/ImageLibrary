package com.loyltworks.showimage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import kotlinx.coroutines.*
import java.net.URL

class ShowImage {

    fun showImage(context: Context, url: String, imageview: ImageView,placeholder:Int) {

        imageview.setImageResource(placeholder)
        imageview.tag = url

        val cachedBitmap = ImageCache.get(url)
        if (cachedBitmap != null) {
            imageview.setImageBitmap(cachedBitmap)
            return
        }

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val inputStream = URL(url).openStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                inputStream.close()


                if (bitmap != null) {
                    ImageCache.put(url, bitmap) // cache the bitmap
                }

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
    object ImageCache {
        private val cacheSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt() // 1/8th memory in KB

        private val bitmapCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, value: Bitmap): Int {
                return value.byteCount / 1024
            }
        }

        fun get(url: String): Bitmap? = bitmapCache.get(url)

        fun put(url: String, bitmap: Bitmap) {
            if (get(url) == null) {
                bitmapCache.put(url, bitmap)
            }
        }
    }

}