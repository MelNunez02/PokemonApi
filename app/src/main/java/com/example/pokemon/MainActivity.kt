package com.example.pokemon

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PokemonAdapter(emptyList())
        recyclerView.adapter = adapter

        // Fetch Pokémon data
        fetchPokemonList()
    }

    private fun fetchPokemonList() {
        val url = "https://pokeapi.co/api/v2/pokemon?limit=100"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Failed to fetch Pokémon list", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = JSONObject(responseBody.string())
                    val results = json.getJSONArray("results")
                    val pokemonList = mutableListOf<PokemonData>()

                    for (i in 0 until results.length()) {
                        val pokemon = results.getJSONObject(i)
                        val name = pokemon.getString("name")
                        val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${i + 1}.png"
                        val detailsUrl = pokemon.getString("url")

                        // Fetch detailed data to get the type
                        fetchPokemonDetails(name, imageUrl, detailsUrl, pokemonList)
                    }
                }
            }
        })
    }

    private fun fetchPokemonDetails(name: String, imageUrl: String, url: String, pokemonList: MutableList<PokemonData>) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("MainActivity", "Failed to fetch Pokémon details for $name", e)
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val json = JSONObject(responseBody.string())
                    val typesArray = json.getJSONArray("types")
                    val typeNames = (0 until typesArray.length()).joinToString(", ") { i ->
                        typesArray.getJSONObject(i).getJSONObject("type").getString("name")
                    }

                    // Add the Pokémon with its type information to the list
                    val pokemonData = PokemonData(name, imageUrl, typeNames)
                    pokemonList.add(pokemonData)

                    // Update the adapter with the new data on the main thread when all data is fetched
                    runOnUiThread {
                        adapter.updateData(pokemonList)
                    }
                }
            }
        })
    }
}
