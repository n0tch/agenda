package com.gustavo.agenda.livedataconfig

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
open class LiveDataTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()
}