package com.template.slots.domain

class UpdateDeposit(private val repository: Repository) {
    operator fun invoke(deposit: Int, bet: Int, reward: Int) = repository.updateDeposit(deposit, bet, reward)
}