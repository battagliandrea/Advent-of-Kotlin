package com.ab.advent.utils

import java.io.File
import java.io.FileNotFoundException

fun String.readLines(): List<String> =
    try{
        File("src/main/resources/$this").bufferedReader().readLines()
    } catch (e: FileNotFoundException){
        listOf()
    }

fun String.readText(): String =
    try{
        File("src/main/resources/$this").bufferedReader().readText()
    } catch (e: FileNotFoundException){
        ""
    }

