package com.template.slots.domain

class SimpleSpin(private val repository: Repository) {
    operator fun invoke(deposit: Int, bet: Int, slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>) = repository.simpleSpin(deposit, bet, slotSpin)
}