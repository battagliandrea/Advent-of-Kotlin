package com.ab.advent.day04

data class Assignment(val start: Int, val end: Int){
    private val range = start..end

    fun containsAll(other: Assignment) = range.intersect(other.range).size == other.range.count()
    fun containsAny(other: Assignment) = range.intersect(other.range).isNotEmpty()

    companion object {
        fun parse(input: String) =
            input.split("-").map { uniqueId -> uniqueId.toInt() }
                .let { (start, end) ->  Assignment(start, end) }
    }
}

data class AssignmentSheet(val left: Assignment, val right: Assignment){

    fun hasOverlap() = left.containsAll(right) || right.containsAll(left)
    fun anyOverlap() = left.containsAny(right) || right.containsAny(left)

    companion object {
        fun parse(input: String) =
            input.split(",").map(Assignment::parse)
                .let { (left, right) -> AssignmentSheet(left, right) }
    }
}

