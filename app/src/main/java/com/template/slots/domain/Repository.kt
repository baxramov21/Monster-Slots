package com.template.slots.domain

import androidx.lifecycle.LiveData

interface Repository {

    fun autoSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int

    fun simpleSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int

    fun generateSlotSpin(slotsRow: List<Int>): MutableList<Int>
    fun generateWin(generate: Boolean = false): LiveData<Boolean>
    fun generateReward(bet: Int, win: Boolean): Int
    fun getObserveAbleBet(): LiveData<Int>
    fun getObserveAbleDeposit(): LiveData<Int>
    fun setBet(bet: Int)
    fun setDeposit(deposit: Int)
    fun getBet(): Int
    fun getDeposit(): Int
     fun updateDeposit(deposit: Int, bet: Int, reward: Int): Int
}