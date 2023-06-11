package com.template.slots.data.db

import android.content.Context
import android.content.SharedPreferences

object PreferenceHelper {

    private const val PREF_FILE_NAME = "app_preferences"
    private const val KEY_DEPOSIT = "deposit"
    private const val KEY_BET = "bet"

    fun saveDeposit(context: Context, result: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_DEPOSIT, result)
        editor.apply()
    }

    fun getDeposit(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_DEPOSIT, 0)
    }

    fun saveBet(context: Context, result: Int) {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_BET, result)
        editor.apply()
    }

    fun getBet(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_BET, 0)
    }
}