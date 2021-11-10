package com.ilgar.starexsampleapp.ui.core

interface EventHandler<E> {
    fun obtainEvent(event: E)
}