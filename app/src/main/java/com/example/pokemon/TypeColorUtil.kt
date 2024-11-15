package com.example.pokemon

import android.content.Context
import androidx.core.content.ContextCompat

object TypeColorUtil {
    fun getTypeColor(context: Context, type: String): Int {
        return when (type.lowercase()) {
            "fire" -> ContextCompat.getColor(context, R.color.fire)
            "water" -> ContextCompat.getColor(context, R.color.water)
            "grass" -> ContextCompat.getColor(context, R.color.grass)
            "electric" -> ContextCompat.getColor(context, R.color.electric)
            "ice" -> ContextCompat.getColor(context, R.color.ice)
            "fighting" -> ContextCompat.getColor(context, R.color.fighting)
            "poison" -> ContextCompat.getColor(context, R.color.poison)
            "ground" -> ContextCompat.getColor(context, R.color.ground)
            "flying" -> ContextCompat.getColor(context, R.color.flying)
            "psychic" -> ContextCompat.getColor(context, R.color.psychic)
            "bug" -> ContextCompat.getColor(context, R.color.bug)
            "rock" -> ContextCompat.getColor(context, R.color.rock)
            "ghost" -> ContextCompat.getColor(context, R.color.ghost)
            "dragon" -> ContextCompat.getColor(context, R.color.dragon)
            "dark" -> ContextCompat.getColor(context, R.color.dark)
            "steel" -> ContextCompat.getColor(context, R.color.steel)
            "fairy" -> ContextCompat.getColor(context, R.color.fairy)
            else -> ContextCompat.getColor(context, R.color.default_type) // Default color if type not found
        }
    }
}
