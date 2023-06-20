package com.template.slots.domain

class GetObserveAbleBet(private val repository: Repository) {
    operator fun invoke() = repository.getObserveAbleBet()
}