package com.akash.memeshare

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
   var currentMemeUrl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme(){

        val queue= Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        LoadingBar.visibility=View.VISIBLE
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
               val url= response.get("url")
                currentMemeUrl=response.getString("url")
                Glide.with(this).load(url).listener(object:RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        LoadingBar.visibility=View.GONE

                        return false
                        //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        LoadingBar.visibility=View.GONE

                        return false
                        //To change body of created functions use File | Settings | File Templates.
                    }
                }).into(memeView)


            },
            Response.ErrorListener { error ->
                // TODO: Handle error
                Toast.makeText(this,"Something Went wrong",Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

    }


    fun shareMeme(view: View) {
        Log.d("Button Log", "share button pressed")
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "Hi, checkout this meme $currentMemeUrl")
        startActivity(Intent.createChooser(i, "Share this meme with"))
    }
    fun nextMeme(view: View) {
        Log.d("Button Log", "next meme button pressed ")
        loadmeme()

    }
}
