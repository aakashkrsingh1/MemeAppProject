package com.akash.memeshare

import android.content.Intent

import android.graphics.drawable.Drawable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.View
import android.widget.Toast

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



class MainActivity : AppCompatActivity() {
   var currentMemeUrl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }
    private fun loadmeme(){
/* function to load the meme */

        //  NOT USED WHEN SINGLETON PATTERN IS USED val queue= Volley.newRequestQueue(this)
        //meme url below
        val url = "https://meme-api.herokuapp.com/gimme"
        LoadingBar.visibility=View.VISIBLE
        //Json Object Request

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
                        //
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
                Toast.makeText(this,"Something Went Wrong ",Toast.LENGTH_LONG).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }


    fun shareMeme(view: View) {
        Log.d("Button Log", "The share meme button was pressed. ")
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this cool meme I found, on Akash's Meme Share app, $currentMemeUrl")

        startActivity(Intent.createChooser(i, "Share this meme with"))
    }
    fun nextMeme(view: View) {
        Log.d("Button Log", "The Next meme Button was pressed. ")
        loadmeme()

    }
}
