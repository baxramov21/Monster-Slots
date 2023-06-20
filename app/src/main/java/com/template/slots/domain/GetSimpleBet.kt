package com.template.slots.domain

class GetSimpleBet(private val repository: Repository) {
    operator fun invoke() = repository.getBet()
}