package com.template.slots.domain

class GetObserveAbleDeposit(private val repository: Repository) {
    operator fun invoke() = repository.getObserveAbleDeposit()
}