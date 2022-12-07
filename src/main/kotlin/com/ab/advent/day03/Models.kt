package com.ab.advent.day03

data class Group(val rucksacks: List<Rucksack>){
    val priority: Int = rucksacks.sumOf { it.commonItem.value.toPriority() }
}

data class ChankedGroup(val chunks: List<List<Rucksack>>){
    val priority: Int = chunks
        .map { c -> c.map { it.id } }
        .map {(sack1, sack12, sack13) -> sack1.first{ it in sack12 && it in sack13}}
        .sumOf { it.toPriority() }
}

class Rucksack(
    val id :String,
    val compartment1: List<Item>,
    val compartment2: List<Item>
) {
    val commonItem = compartment1.first{ it in compartment2}
}

data class Item(
    val value: Char
)
fun Char.toPriority(): Int = if(this.isLowerCase()) this - 'a' + 1 else this - 'A' + 27
fun String.toItem(): List<Item> =
    this.map { value -> Item(value = value) }

fun List<String>.toGroup() = this.toRucksacks().let(::Group)
fun List<String>.toChunkedGroup() = this.toRucksacks().chunked(3)
    .let(::ChankedGroup)

private fun List<String>.toRucksacks() =
    this.map { it.chunked(it.length / 2) }
        .map {(compartment1, compartment2) -> Rucksack(compartment1 + compartment2, compartment1.toItem(), compartment2.toItem()) }







