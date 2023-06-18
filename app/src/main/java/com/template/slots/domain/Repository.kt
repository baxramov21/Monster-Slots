package com.template.slots.domain

interface Repository {

    fun autoSpin(
        win: Boolean,
        bet: Int,
        slotSpin: (slotsRow: List<Int>, frequency: Int) -> Unit
    ): Int

    fun simpleSpin(
        win: Boolean,
        bet: Int,
        slotSpin: () -> Unit
    ): Int

    fun generateSlotSpin(slotsRow: List<Int>, frequency: Int): List<Int>
    fun generateWin(): Boolean
    fun generateReward(bet: Int, win: Boolean): Int
    fun getBet(): Int
    fun getDeposit(): Int
    fun setBet(bet: Int)
    fun setDeposit(deposit: Int)
}