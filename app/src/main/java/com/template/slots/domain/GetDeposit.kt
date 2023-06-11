package com.template.slots.domain

class GetDeposit(private val repository: Repository) {
    operator fun invoke() = repository.getDeposit()
}