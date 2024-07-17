package com.application.domain.entity

interface Results<C : Character<*, *>> {
    val results: List<C>
}