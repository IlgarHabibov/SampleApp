package com.ilgar.starexsampleapp.ui.core

import androidx.lifecycle.ViewModel

abstract class CoreViewModel<E: CoreEvent>: ViewModel(), EventHandler<E> {
}