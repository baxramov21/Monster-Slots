package com.template.slots.domain

class SetBet(private val repository: Repository) {
    operator fun invoke(bet: Int) = repository.setBet(bet)
}