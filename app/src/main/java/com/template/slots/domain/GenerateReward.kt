package com.template.slots.domain

class GenerateReward(private val repository: Repository) {
    operator fun invoke(bet: Int, win: Boolean): Int = repository.generateReward(bet, win)
}