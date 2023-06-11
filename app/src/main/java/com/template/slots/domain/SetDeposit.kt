package com.template.slots.domain

class SetDeposit(private val repository: Repository) {
    operator fun invoke(deposit: Int) = repository.setDeposit(deposit)
}