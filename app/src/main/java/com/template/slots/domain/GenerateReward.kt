package com.template.slots.domain

class GenerateReward(private val repository: Repository) {
    operator fun invoke(deposit: Int): Int = repository.generateReward(deposit)
}