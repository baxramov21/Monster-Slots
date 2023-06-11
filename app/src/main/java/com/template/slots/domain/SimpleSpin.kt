package com.template.slots.domain

class SimpleSpin(private val repository: Repository) {
    operator fun invoke(deposit: Int, bet: Int) = repository.simpleSpin(deposit, bet)
}