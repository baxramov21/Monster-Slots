package com.template.slots.domain

class AutoSpin(private val repository: Repository) {
    operator fun invoke(
        win: Boolean,
        bet: Int,
        slotSpin: (slotsRow: List<Int>, frequency: Int) -> Unit
    ): Int {
        return repository.autoSpin(win, bet, slotSpin)
    }
}