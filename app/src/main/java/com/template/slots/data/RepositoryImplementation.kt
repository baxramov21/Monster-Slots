package com.template.slots.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.template.slots.data.db.PreferenceHelper
import com.template.slots.domain.Repository
import kotlin.random.Random

typealias slotsRandomizing = (List<Int>, Int) -> Unit

class RepositoryImplementation(private val application: Application) : Repository {

    private val db = PreferenceHelper
    private val _randomSlots: MutableLiveData<List<Int>>? = null
    val randomSlots by lazy {
        _randomSlots ?: throw NullPointerException("_randomSlots is null")
    }

    override fun autoSpin(
        win: Boolean,
        bet: Int,
        slotSpin: (slotsRow: List<Int>, frequency: Int) -> Unit
    ): Int {
        return generateReward(bet, win)
    }

    override fun simpleSpin(
        win: Boolean,
        bet: Int,
        slotSpin: () -> Unit
    ): Int {
        slotSpin()
        return generateReward(bet, win)
    }

    override fun generateSlotSpin(slotsRow: List<Int>, frequency: Int): List<Int> {
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