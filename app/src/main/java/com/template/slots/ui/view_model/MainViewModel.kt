package com.template.slots.ui.view_model

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.slots.data.RepositoryImplementation
import com.template.slots.domain.*

class MainViewModel(application: Application) : ViewModel() {

    private val repository = RepositoryImplementation(application)

    private val autoSpin = AutoSpin(repository)
    private val simpleSpin = SimpleSpin(repository)
    private val generateWin = GenerateWin(repository)
    private val generateReward = GenerateReward(repository)
    private val generateSlotSpin = GenerateSlotSpin(repository)
    val setBet = SetBet(repository)
    val setDeposit = SetDeposit(repository)

    val getBet = GetBet(repository)
    val getDeposit = GetDeposit(repository)

    private val _imagesList = MutableLiveData<List<Int>>()
    val imageList: LiveData<List<Int>>
        get() = _imagesList

    private val _gameFinished = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean>
        get() = _gameFinished

    fun startGame(listOfImageIDs: List<Int>) {
        val timer = object : CountDownTimer(
            GAME_LENGTH_IN_SECONDS * 1000,
            SLOTS_GENERATION_INTERVAL_IN_MILLI_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _gameFinished.postValue(false)
                _imagesList.postValue(generateSlotSpin(listOfImageIDs, 5))
            }

            override fun onFinish() {
                _imagesList.postValue(generateSlotSpin(listOfImageIDs, 5))
                _gameFinished.postValue(true)
            }
        }
        timer.start()
    }

    init {
        if (getBet() == 0) {
            setBet(10)
            setDeposit(100)
        }
    }

    fun getReward(bet: Int) = generateReward(bet, generateWin())

    companion object {
        private const val GAME_LENGTH_IN_SECONDS = 5L
        private const val SLOTS_GENERATION_INTERVAL_IN_MILLI_SECONDS = 80L
    }
}