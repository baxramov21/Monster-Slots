package com.template.slots.domain

class GenerateSlotSpin(private val repository: Repository) {
    operator fun invoke(slotsRow: List<Int>) = repository.generateSlotSpin(slotsRow)
}