package dev.chamo.millieapp.core.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val dispatcher: MillieAppDispatchers)

enum class MillieAppDispatchers {
    Default,
    IO,
}