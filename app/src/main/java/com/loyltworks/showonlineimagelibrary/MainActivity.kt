package com.loyltworks.showonlineimagelibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loyltworks.showimage.ShowImage
import com.loyltworks.showonlineimagelibrary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ShowImage().showImage(this,"https://dummyimage.com/300.png/09f/fff",binding.image,
            com.loyltworks.showimage.R.drawable.user)
    }
}