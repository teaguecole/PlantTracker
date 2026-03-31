package com.teaguecole.planttracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform