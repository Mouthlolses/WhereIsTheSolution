package com.whereisthesolution.whereisihesolutionapp.ai

import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val response = AiAgent.test()
    println("RESPOSTA DA IA:")
    println(response)

}