package com.template.slots.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.messaging.FcmBroadcastProcessor.reset
import com.template.slots.R
import com.template.slots.databinding.FragmentGameBinding
import com.template.slots.ui.screens.HomeFragment
import com.template.slots.ui.screens.LoadingFragment
import com.template.slots.ui.view_model.MainViewModel
import com.template.slots.ui.view_model.MyViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

    private val viewModelFactory by lazy {
        MyViewModelFactory(requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw NullPointerException("FragmentGameBinding is null")

    private val imageViews: ArrayList<ImageView> by lazy {
        val list: ArrayList<ImageView>
        with(binding.slotsTable) {
            list = arrayListOf(
                image1,
                image2,
                image3,
                image4,
                image5,
                image6,
                image7,
                image8,
                image9,
            )
        }
        list
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeWin(viewLifecycleOwner)
        onSimpleSpinClicked()
        rotateSlots()
        onTextChanged()
        changeBet()
        onAutoSpinClicked()
        onBackPressed()
        onGameFinished()
    }


    private fun onTextChanged() {
        with(viewModel) {
            deposit.observe(viewLifecycleOwner) {
                this.canContinueGame(it)
                binding.textViewDeposit.text = "0000$it"
                enableViews(binding.btnSpin, binding.btnAutoSpin)
            }

            bet.observe(viewLifecycleOwner) {
                binding.betAmount.text = "$it"
            }
        }
    }

    private fun onBackPressed() {
        binding.imageViewBack.setOnClickListener {
            coroutineScope.launch {
                delay(800)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, HomeFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun onAutoSpinClicked() {
        with(binding) {
            btnAutoSpin.setOnClickListener {
                if (viewModel.canSpin()) {
                    if (!viewModel.getAutoSpinGoing()) {
                        btnAutoSpin.setImageResource(R.drawable.button_stop_autospin)
                        coroutineScope.launch {
                            viewModel.setAutoSpinGoing(true)
                            viewModel.autoSpin(SLOTS_LIST)
                        }
                        disableViews(btnSpin)
                    } else {
                        stopAutoSpin()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_enough_coins),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun stopAutoSpin() {
        binding.btnAutoSpin.setImageResource(R.drawable.button_autospin)
        viewModel.setAutoSpinGoing(false)
        enableViews(binding.btnSpin)
    }

    private fun onSimpleSpinClicked() {
        with(binding) {
            btnSpin.setOnClickListener {
                if (viewModel.canSpin()) {
                    coroutineScope.launch {
                        viewModel.spin(SLOTS_LIST)
                    }
                    disableViews(btnSpin, btnAutoSpin)
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_enough_coins),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onGameFinished() {
        viewModel.gameFinished.observe(viewLifecycleOwner) {
            if (it) {
                showCustomDialog()
                viewModel.reset()
            }
        }
    }

    private fun showCustomDialog() {
        val dialogView: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_layout, null)

        val imageView: ImageView = dialogView.findViewById(R.id.gameOverImage)
        imageView.setImageResource(R.drawable.game_over)
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogView)
        alertDialogBuilder.setCancelable(true)

        stopAutoSpin()
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        val buttonReplay: ImageView = dialogView.findViewById(R.id.buttonReplay)
        buttonReplay.setOnClickListener {
            openScreen(LoadingFragment.newInstance())
            alertDialog.dismiss()
        }
    }

    private fun openScreen(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun rotateSlots() {
        with(viewModel) {
            imageList.observe(viewLifecycleOwner) {
                for (i in it.indices) {
                    imageViews[i].setImageResource(it[i])
                }
            }
        }
    }

    private fun changeBet() {
        with(binding) {
            increaseBet.setOnClickListener {
                viewModel.increaseBetByOne()
            }

            decreaseBet.setOnClickListener {
                viewModel.decreaseBetByOne()
            }
        }
    }

    private fun enableViews(vararg views: View) {
        views.map { it.isEnabled = true }
    }

    private fun disableViews(vararg views: View) {
        views.map { it.isEnabled = false }
    }


    override fun onDestroy() {
        if (_binding != null) {
            _binding = null
        }
        super.onDestroy()
    }

    companion object {

        private val SLOTS_LIST = listOf(
            R.drawable.ico_1,
            R.drawable.ico_2,
            R.drawable.ico_3,
            R.drawable.ico_4,
            R.drawable.ico_5,
            R.drawable.ico_6,
            R.drawable.ico_7,
            R.drawable.ico_8,
            R.drawable.ico_1
        )

        @JvmStatic
        fun newInstance() = GameFragment()
    }
}