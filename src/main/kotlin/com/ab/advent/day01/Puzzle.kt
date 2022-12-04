package com.ab.advent.day01

import com.ab.advent.utils.readLines
/*
PART 01

The Elves take turns writing down the number of Calories contained by the various meals,
snacks, rations, etc. thatthey've brought with them, one item per line.
Each Elf separates their own inventory from the previous Elf's inventory (if any) by a blank line.

For example, suppose the Elves finish writing their items' Calories and end up with the following list:

1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
This list represents the Calories of the food carried by five Elves:

The first Elf is carrying food with 1000, 2000, and 3000 Calories, a total of 6000 Calories.
The second Elf is carrying one food item with 4000 Calories.
The third Elf is carrying food with 5000 and 6000 Calories, a total of 11000 Calories.
The fourth Elf is carrying food with 7000, 8000, and 9000 Calories, a total of 24000 Calories.
The fifth Elf is carrying one food item with 10000 Calories.
In case the Elves get hungry and need extra snacks, they need to know which Elf to ask:
they'd like to know how many Calories are being carried by the Elf carrying the most Calories. In the example above, this is 24000 (carried by the fourth Elf).

Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?

PART TWO
By the time you calculate the answer to the Elves' question,
they've already realized that the Elf carrying the most Calories of food might eventually run out of snacks.

To avoid this unacceptable situation, the Elves would instead like to know the total Calories carried by the top
three Elves carrying the most Calories. That way, even if one of those Elves runs out of snacks,
they still have two backups.

In the example above, the top three Elves are the fourth Elf (with 24000 Calories),
then the third Elf (with 11000 Calories), then the fifth Elf (with 10000 Calories).
The sum of the Calories carried by these three elves is 45000.

Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?
*/

fun main(args: Array<String>) {
    println("Day 1 - Puzzle 1")

    val input = "puzzle_01_input.txt".readLines()

    println(elves(input))

    println("Find the Elf carrying the most Calories. How many total Calories is that Elf carrying?")
    println(answer1(input))

    println("Find the top three Elves carrying the most Calories. How many Calories are those Elves carrying in total?")
    println(answer2(input))
}

fun elves(input: List<String>) = input.chunked { it.isBlank() }
    .map { data -> Elf(inventory = data.map { it.toLong() }.map{ Food(calories = it)})}

fun answer1(input: List<String>) = elves(input).maxBy { it.carriedCalories }.carriedCalories

fun answer2(input: List<String>) = elves(input).sortedByDescending { it.carriedCalories }.take(3).sumOf { it.carriedCalories }

fun <T> List<T>.chunked(regex: (T) -> Boolean): List<List<T>>{
    val output = arrayListOf<List<T>>()
    var last = 0
    this.forEachIndexed { index, value ->
        if(regex(value)){
            output.add(subList(last, index))
            last = index + 1
        }
    }
    return output
}

