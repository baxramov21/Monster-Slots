package com.template.slots.domain

class AutoSpin(private val repository: Repository) {
    operator fun invoke(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int {
        return repository.autoSpin(deposit, bet, slotSpin)
    }
}