package com.template.slots.domain

interface Repository {

    fun autoSpin(deposit: Int, bet: Int, slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>): Int
    fun simpleSpin(deposit: Int, bet: Int, slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>): Int
    fun generateSlotSpin(slotsRow: MutableList<Int>): MutableList<Int>
    fun generateWin(): Boolean
    fun generateReward(bet: Int, win: Boolean): Int
    fun getBet(): Int
    fun getDeposit(): Int
    fun setBet(bet: Int)
    fun setDeposit(deposit: Int)
}