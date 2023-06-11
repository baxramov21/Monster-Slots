package com.template.slots.data

import android.app.Application
import com.template.slots.data.db.PreferenceHelper
import com.template.slots.domain.Repository
import kotlin.random.Random

class RepositoryImplementation(private val application: Application) : Repository {

    private val db = PreferenceHelper

    override fun autoSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int {
        val win = generateWin()
        return generateReward(deposit, win)
    }

    override fun simpleSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int {
        val win = generateWin()
        return generateReward(deposit, win)
    }


    override fun generateSlotSpin(slotsRow: MutableList<Int>): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (position in slotsRow) {
            result.add(slotsRow.random())
        }
        return result
    }

    override fun generateWin() = Random.nextBoolean()

    override fun generateReward(bet: Int, win: Boolean): Int {
        val startValue: Int
        val endValue: Int
        val result: Int
        if (win) {
            startValue = (bet / 2) + 5
            endValue = (bet * 3) + 5
            result = Random.nextInt(startValue, endValue)
        } else {
            result = bet * -1
        }
        return result
    }

    override fun getBet(): Int = db.getBet(application)

    override fun getDeposit(): Int = db.getDeposit(application)

    override fun setBet(bet: Int) = db.saveBet(application, bet)

    override fun setDeposit(deposit: Int) = db.saveDeposit(application, deposit)
}