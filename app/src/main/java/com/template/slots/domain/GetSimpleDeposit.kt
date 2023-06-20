package com.template.slots.domain

class GetSimpleDeposit(private val repository: Repository) {
    operator fun invoke() = repository.getDeposit()
}