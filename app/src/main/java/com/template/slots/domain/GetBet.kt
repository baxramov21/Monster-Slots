package com.template.slots.domain

class GetBet(private val repository: Repository) {
    operator fun invoke() = repository.getBet()
}