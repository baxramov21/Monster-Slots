package com.template.slots.domain

class AutoSpin(private val repository: Repository) {
    operator fun invoke(deposit: Int, bet: Int): Int {
        return repository.autoSpin(deposit, bet)
    }
}