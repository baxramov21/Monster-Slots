package com.template.slots.ui.view_model

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.slots.data.RepositoryImplementation
import com.template.slots.domain.*
import kotlinx.coroutines.delay

class MainViewModel(context: Context) : ViewModel() {

    private val repository = RepositoryImplementation(context as Application)

    private val generateWin = GenerateWin(repository)
    private val generateReward = GenerateReward(repository)
    private val generateSlotSpin = GenerateSlotSpin(repository)
    private val setBet = SetBet(repository)
    private val setDeposit = SetDeposit(repository)
    private val getObserveAbleBet = GetObserveAbleBet(repository)
    private val getObserveAbleDeposit = GetObserveAbleDeposit(repository)
    private val getDeposit = GetSimpleDeposit(repository)
    private val getBet = GetSimpleBet(repository)
    private val updateDeposit = UpdateDeposit(repository)

    private val lvWinState: LiveData<Boolean> = generateWin()

    private val _imagesList = MutableLiveData<List<Int>>()
    val imageList: LiveData<List<Int>>
        get() = _imagesList

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    val deposit = getObserveAbleDeposit()
    val bet = getObserveAbleBet()

    private var autoSpinIsGoing = false
    private var slotsGenerated = false
    private var currentBet = getBet()
    private var currentDeposit = getDeposit()

    suspend fun spin(slotsRow: List<Int>) {
        generateSpin(slotsRow)
        if (slotsGenerated) {
            generateWin(true)
            slotsGenerated = false
        }
    }

    suspend fun autoSpin(slotsRow: List<Int>) {
        while (currentDeposit >= currentBet && autoSpinIsGoing) {
            spin(slotsRow)
        }
        _gameFinished.postValue(true)
    }

    private suspend fun generateSpin(slotsRow: List<Int>) {
        val spinAmount =
            (GAME_LENGTH_IN_SECONDS * 1000) / SLOTS_GENERATION_INTERVAL_IN_MILLI_SECONDS
        for (i in 1..spinAmount) {
            _imagesList.postValue(generateSlotSpin(slotsRow))
            val delayDuration = delayDurationAnimation((spinAmount - i), spinAmount)
            delay(delayDuration)
        }
        delay(1000)
        slotsGenerated = true
    }

    private fun delayDurationAnimation(spinAmountLeft: Long, overAllSpinAmount: Long): Long {
        return when (spinAmountLeft) {

            in 51..overAllSpinAmount -> 40

            in 41..50 -> 60

            in 31..40 -> 80

            in 21..30 -> 100

            in 11..20 -> 120

            in 6..10 -> 150

            else -> 200

        }
    }

    fun setAutoSpinGoing(value: Boolean) {
        autoSpinIsGoing = value
    }

    fun getAutoSpinGoing() = autoSpinIsGoing


    fun increaseBetByOne() {
        if (currentDeposit > currentBet) {
            setBet(++currentBet)
        }
    }

    fun decreaseBetByOne() {
        if (currentBet > 1) {
            setBet(--currentBet)
        }
    }

    fun canSpin(): Boolean {
        return currentDeposit >= currentBet
    }

    fun canContinueGame(deposit: Int) {
        if (deposit <= 0) {
            _gameFinished.postValue(true)
        }
    }

    fun observeWin(lifecycleOwner: LifecycleOwner) {
        lvWinState.observe(lifecycleOwner) {
            val reward = generateReward(getBet(), it)
            val newDeposit = updateDeposit(getDeposit(), getBet(), reward)
            setDeposit(newDeposit)
        }
    }

    fun reset() {
        setBet(10)
        setDeposit(100)
        currentDeposit = 100
        currentBet = 10
        _gameFinished.postValue(false)
    }

    init {
        if (getBet() == 0) {
            setBet(10)
            setDeposit(100)
        }
    }

    companion object {
        private const val GAME_LENGTH_IN_SECONDS = 5L
        private const val SLOTS_GENERATION_INTERVAL_IN_MILLI_SECONDS = 80L
    }
}