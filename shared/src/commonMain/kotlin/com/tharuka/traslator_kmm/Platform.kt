package com.tharuka.traslator_kmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform