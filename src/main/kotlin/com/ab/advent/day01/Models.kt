package com.ab.advent.day01

data class Elf(
    val inventory: List<Food>
){
    val carriedCalories = inventory.sumOf { it.calories }
}

data class Food(
    val calories: Long
)