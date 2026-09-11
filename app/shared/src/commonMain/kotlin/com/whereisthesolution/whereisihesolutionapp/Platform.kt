package com.whereisthesolution.whereisihesolutionapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform