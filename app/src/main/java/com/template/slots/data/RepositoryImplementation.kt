package com.template.slots.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.template.slots.data.db.PreferenceHelper
import com.template.slots.domain.Repository
import kotlin.random.Random

class RepositoryImplementation(private val application: Application) : Repository {

    private val db = PreferenceHelper
    private val bet_lv = MutableLiveData<Int>()
    private val deposit_lv = MutableLiveData<Int>()
    private val win_lv = MutableLiveData<Boolean>()
    override fun autoSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int {
        TODO("Not yet implemented")
    }

    override fun simpleSpin(
        deposit: Int,
        bet: Int,
        slotSpin: (slotsRow: MutableList<Int>) -> MutableList<Int>
    ): Int {
        TODO("Not yet implemented")
    }


    override fun generateSlotSpin(slotsRow: List<Int>): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (position in slotsRow) {
            result.add(slotsRow.random())
        }
        return result
    }

    override fun generateWin(generate: Boolean): LiveData<Boolean> {
        val win = Random.nextBoolean()
        if (generate) {
            win_lv.postValue(win)
        }
        return win_lv
    }

    override fun generateReward(bet: Int, win: Boolean): Int {
        val startValue: Int
        val endValue: Int
        val result: Int
        if (win) {
            startValue = bet * 2
            endValue = bet * 3
            result = Random.nextInt(startValue, endValue)
        } else {
            result = bet.unaryMinus()
        }
        return result
    }

    override fun getObserveAbleBet(): LiveData<Int> {
        bet_lv.postValue(db.getBet(application))
        return bet_lv
    }

    override fun getObserveAbleDeposit(): LiveData<Int> {
        deposit_lv.postValue(db.getDeposit(application))
        return deposit_lv
    }

    override fun setBet(bet: Int) {
        db.saveBet(application, bet)
        bet_lv.postValue(bet)
    }

    override fun setDeposit(deposit: Int) {
        db.saveDeposit(application, deposit)
        deposit_lv.postValue(deposit)
    }

    override fun getBet(): Int = db.getBet(application)

    override fun getDeposit(): Int = db.getDeposit(application)
    override fun updateDeposit(deposit: Int, bet: Int, reward: Int): Int {
        return if (reward > 0)
            (deposit + reward) - bet
        else
            deposit + reward
    }
}