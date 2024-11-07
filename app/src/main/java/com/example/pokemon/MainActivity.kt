package com.example.pokemon

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.json.JSONObject
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response


class MainActivity : AppCompatActivity() {

    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var fetchButton: Button

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        nameTextView = findViewById(R.id.nameTextView)
        typeTextView = findViewById(R.id.typeTextView)
        imageView = findViewById(R.id.imageView)
        fetchButton = findViewById(R.id.fetchButton)

        // Set button click listener to fetch Ditto's data
        fetchButton.setOnClickListener {
            fetchPokemonData("https://pokeapi.co/api/v2/pokemon/ditto")
        }
    }

    private fun fetchPokemonData(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    nameTextView.text = "Failed to load data"
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = JSONObject(responseBody.string())
                    val name = json.getString("name")
                    val imageUrl = json.getJSONObject("sprites").getString("front_default")
                    val types = json.getJSONArray("types")
                    val typeNames = (0 until types.length()).joinToString(", ") { i ->
                        types.getJSONObject(i).getJSONObject("type").getString("name")
                    }

                    // Update the UI with fetched data
                    runOnUiThread {
                        nameTextView.text = "Name: $name"
                        typeTextView.text = "Type(s): $typeNames"
                        imageView.load(imageUrl)
                    }
                }
            }
        })
    }
}
