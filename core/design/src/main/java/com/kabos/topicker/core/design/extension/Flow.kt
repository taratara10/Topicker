package com.kabos.topicker.core.design.extension

import kotlinx.coroutines.flow.MutableStateFlow


fun <T> MutableStateFlow<T>.update(action: T.() -> T) {
    value = value.action()
}
