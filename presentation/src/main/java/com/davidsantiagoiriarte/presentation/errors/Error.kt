package com.davidsantiagoiriarte.presentation.errors

sealed class Error {
    object NoConnectionError : Error()
    class DefaultError(val message : String?) : Error()
}
