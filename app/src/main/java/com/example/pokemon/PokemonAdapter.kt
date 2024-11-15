package com.example.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PokemonAdapter(private var pokemonList: List<PokemonData>) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    // ViewHolder class to hold references to each view in the item layout
    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val typeTextView: TextView = view.findViewById(R.id.typeTextView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]

        // Set the Pokémon name and image
        holder.nameTextView.text = pokemon.name
        holder.imageView.load(pokemon.imageUrl) // Use Coil to load the image

        // Set type color
        val types = pokemon.type.split(", ")
        if (types.isNotEmpty()) {
            val primaryType = types[0] // Use the first type as the primary color
            holder.typeTextView.setTextColor(TypeColorUtil.getTypeColor(holder.itemView.context, primaryType))
        }

        // Set type text
        holder.typeTextView.text = pokemon.type // Assuming PokemonData has a type field

        // Fade-in animation
        holder.itemView.alpha = 0f
        holder.itemView.animate().alpha(1f).setDuration(300).start()
    }

    override fun getItemCount(): Int = pokemonList.size

    // Method to update data in the adapter and refresh the RecyclerView
    fun updateData(newData: List<PokemonData>) {
        pokemonList = newData
        notifyDataSetChanged()
    }
}
