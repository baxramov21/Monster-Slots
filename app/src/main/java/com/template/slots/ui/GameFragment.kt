package com.template.slots.ui

import android.os.Bundle
import android.provider.UserDictionary.Words.FREQUENCY
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.template.slots.R
import com.template.slots.databinding.FragmentGameBinding
import com.template.slots.ui.adapter.SlotItem
import com.template.slots.ui.adapter.SlotsAdapter
import com.template.slots.ui.view_model.MainViewModel
import com.template.slots.ui.view_model.MyViewModelFactory
import kotlinx.coroutines.*

class GameFragment : Fragment() {

    private val viewModelFactory by lazy {
        MyViewModelFactory(requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

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

    private fun onBackPressed() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, HomeFragment.newInstance())
            .commit()
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
        binding.textViewDeposit.text = "000 ${viewModel.getDeposit()}"
        binding.betAmount.text = "${viewModel.getBet()}"

        onSimpleSpinClicked()
        observeSlotsListChanges()
        onBetChanged()
        onAutoClickListener()
    }

    private fun onSimpleSpinClicked() {
        binding.btnSpin.setOnClickListener {
            disableStartGameButtons()
            with(binding) {
                textViewDeposit.text = "000${(viewModel.getDeposit() - viewModel.getBet())}"
            }
            viewModel.startGame(SLOTS_LIST)
        }
    }

    private fun onAutoClickListener() {
        binding.btnAutoSpin.setOnClickListener {
//            val deposit = viewModel.getDeposit()
//            val bet = viewModel.getBet()
//            while (deposit >= bet) {
//                disableStartGameButtons()
//                viewModel.startGame(SLOTS_LIST)
//            }
//            if (deposit < bet) {
//                requireActivity().supportFragmentManager.beginTransaction()
//                    .replace(R.id.main_container, GameFinishedFragment.newInstance())
//                    .commit()
//            }

            disableStartGameButtons()
            with(binding) {
                textViewDeposit.text = "000${(viewModel.getDeposit() - viewModel.getBet())}"
            }
            viewModel.startGame(SLOTS_LIST)
        }
    }

    private fun disableStartGameButtons() {
        binding.btnSpin.isEnabled = false
        binding.btnAutoSpin.isEnabled = false
    }

    private fun enableStartGameButtons() {
        binding.btnSpin.isEnabled = true
        binding.btnAutoSpin.isEnabled = true
    }

    private fun observeSlotsListChanges() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            generateSlotsTable(it)
        }

        viewModel.gameFinished.observe(viewLifecycleOwner) {
            if (it) {
                var deposit = viewModel.getDeposit()
                val bet = viewModel.getBet()
                deposit -= bet
                deposit += viewModel.getReward(bet)
                binding.textViewDeposit.text = "000$deposit"
                viewModel.setDeposit(deposit)
                viewModel.setBet(bet)
                enableStartGameButtons()
            }
        }
    }

    private fun onBetChanged() {
        var bet = viewModel.getBet()
        val deposit = viewModel.getDeposit()
        binding.decreaseBet.setOnClickListener {
            if (bet > 1) {
                bet--
                binding.betAmount.text = "$bet"
                viewModel.setBet(bet)
            } else {
                Toast.makeText(
                    context,
                    "You can't decrease the value more than this",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        binding.increaseBet.setOnClickListener {
            if (deposit >= bet) {
                bet++
                viewModel.setBet(bet)
                binding.betAmount.text = "$bet"
            } else {
                Toast.makeText(context, "You don't have enough resources", Toast.LENGTH_SHORT)
                    .show()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, GameFinishedFragment.newInstance())
                    .commit()
            }
        }
    }

    private fun generateSlotsTable(imageIds: List<Int>) {
        for (position in 0 until imageViews.size) {
            imageViews[position].setImageResource(imageIds[position])
        }
    }

    override fun onDestroy() {
        if (_binding != null) {
            _binding = null
        }
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()

        val SLOTS_LIST = listOf<Int>(
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

        private const val SECONDS_IN_MILLISECONDS = 5000
        private const val CHANGE_IMAGE_PERIOD_IN_MILLIS = 100
        private const val FREQUENCY = SECONDS_IN_MILLISECONDS / CHANGE_IMAGE_PERIOD_IN_MILLIS
    }
}