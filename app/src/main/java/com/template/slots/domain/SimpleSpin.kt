package com.template.slots.domain

class SimpleSpin(private val repository: Repository) {
    operator fun invoke(win: Boolean, bet: Int, slotSpin: () -> Unit) =
        repository.simpleSpin(win, bet, slotSpin)
}