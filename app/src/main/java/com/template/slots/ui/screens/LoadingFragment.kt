package com.template.slots.ui.screens

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.template.slots.R
import com.template.slots.ui.GameFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoadingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = requireActivity().findViewById<ProgressBar>(R.id.progressBar)
        val textViewProgressText = requireView().findViewById<TextView>(R.id.textViewProgressText)
        initProgressBar(progressBar)
        runProgress(progressBar, textViewProgressText)
    }

    private fun initProgressBar(progressBar: ProgressBar) {
        val layers = arrayOf(
            createProgressBackgroundDrawable(),
            createProgressBarDrawable()
        )

        val layerDrawable = LayerDrawable(layers)
        layerDrawable.getDrawable(0)?.setTint(Color.RED)
        progressBar.progressDrawable = layerDrawable
        progressBar.max = 100
        progressBar.progress = 0
    }

    private fun createProgressBackgroundDrawable(): GradientDrawable {
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setColor(Color.RED)
        backgroundDrawable.cornerRadius = 40f
        return backgroundDrawable
    }

    private fun createProgressBarDrawable(): ScaleDrawable {
        val progressDrawable = GradientDrawable()
        progressDrawable.setColor(Color.BLUE)
        progressDrawable.cornerRadius = 40f
        val scaleDrawable = ScaleDrawable(progressDrawable, android.view.Gravity.START, 1f, -1f)
        scaleDrawable.level = 0

        return scaleDrawable
    }

    private fun runProgress(progressBar: ProgressBar, textView: TextView) {
        CoroutineScope(Dispatchers.Main).launch {
            for (i in 0..100) {
                progressBar.progress = i
                textView.text = "${i}%"
                delay(40)
            }
            openScreen(GameFragment.newInstance())
        }
    }

    private fun openScreen(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()

    }

    companion object {

        @JvmStatic
        fun newInstance() = LoadingFragment()
    }
}