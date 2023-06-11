package com.template.slots.domain

interface Repository {

    fun autoSpin(deposit: Int, bet: Int): Int
    fun simpleSpin(deposit: Int, bet: Int): Int
    fun generateSlotSpin(slotsRow: MutableList<Int>): MutableList<Int>
    fun generateWin(): Boolean
    fun generateReward(deposit: Int): Int
    fun getBet(): Int
    fun getDeposit(): Int
    fun setBet(bet: Int): Int
    fun setDeposit(deposit: Int): Int
}