package com.template.slots.domain

class GenerateWin(private val repository: Repository) {
    operator fun invoke(generate: Boolean = false) = repository.generateWin(generate)
}