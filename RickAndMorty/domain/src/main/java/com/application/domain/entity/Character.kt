package com.application.domain.entity

interface Character<L: Location, O: Origin> {
    val id: Int
    val name: String
    val status: String
    val species: String
    val origin: O
    val location: L
    val image: String
}