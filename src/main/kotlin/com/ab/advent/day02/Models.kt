package com.ab.advent.day02

import java.lang.IllegalArgumentException


// A. ROCK
// B. PAPER
// C. SCISSORS

// X. ROCK
// Y. PAPER
// Z. SCISSORS


// 1. Mappare l'input in un torneo che è una lista di round di shape
// 2. Dare un punteggio a quella determinata HandShape
// 3. Decretare il mio punteggio calcolandolo per ogni riga

enum class Output(val score: Int){
    WIN(6),
    DRAW(3),
    LOSE(0);

    fun inverse() = when (this){
        WIN -> LOSE
        DRAW -> DRAW
        LOSE -> WIN
    }
}

sealed class HandShape(val score: Int){
    object Rock: HandShape(1)
    object Paper: HandShape(2)
    object Scissors: HandShape(3)

    fun evaluate(other: HandShape): Output = when{
        this == other -> Output.DRAW
        this == Rock && other == Scissors -> Output.WIN
        this == Scissors && other == Paper -> Output.WIN
        this == Paper && other == Rock -> Output.WIN
        else -> Output.LOSE
    }
}

data class Result(val output1: Output, val output2: Output)

data class Round(
    val left: HandShape,
    val right: HandShape,
){
    val result: Result = left.evaluate(right).let { output -> Result(output, output.inverse()) }
}

data class Tournament(
    val rounds: List<Round>
){
    val totalScore = rounds.sumOf { it.right.score + it.result.output2.score }
}

fun String.toShape(): HandShape = when(this){
    "A", "X" -> HandShape.Rock
    "B", "Y" -> HandShape.Paper
    "C", "Z" -> HandShape.Scissors
    else -> throw IllegalArgumentException()
}

fun String.toOutuput(): Output = when(this){
    "X" -> Output.LOSE
    "Y" -> Output.DRAW
    "Z" -> Output.WIN
    else -> throw IllegalArgumentException()
}

object Strategy {
    private val scenarios: List<Round> = buildList {
        val shapes: List<HandShape> = listOf(HandShape.Rock, HandShape.Paper, HandShape.Scissors)
        lazyCartesianProduct(shapes, shapes).forEach { pair -> add(Round(left = pair.first, right = pair.second)) }
    }

    fun find(shape1: HandShape, shape2: HandShape): Round =
        scenarios.single { it.left == shape1 && it.right == shape2 }

    fun find(shape1: HandShape, shape2: Output): Round =
        scenarios.single { it.left == shape1 && it.result.output2 == shape2 }
}

private fun <A, B> lazyCartesianProduct(
    listA: Iterable<A>,
    listB: Iterable<B>
): Sequence<Pair<A, B>> =
    sequence {
        listA.forEach { a ->
            listB.forEach { b ->
                yield(a to b)
            }
        }
    }

fun List<List<String>>.toTournament1(): Tournament =
    this.map { Strategy.find(it[0].toShape(), it[1].toShape()) }.let(::Tournament)

fun List<List<String>>.toTournament2(): Tournament =
        this.map { Strategy.find(it[0].toShape(), it[1].toOutuput()) }.let(::Tournament)
