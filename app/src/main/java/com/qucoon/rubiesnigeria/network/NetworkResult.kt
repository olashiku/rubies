package com.qucoon.rubiesnigeria.network


sealed class NetworkResult<out T:Any> {
    class Success<out T:Any>(val data:T): NetworkResult<T>()
    class Errror(val exception: Throwable): NetworkResult<Nothing>()
    class Failed<out T:Any> (val message:T): NetworkResult<Nothing>()
    class SuccessSocketConnection<out T:Any> ( message:T): NetworkResult<Nothing>()
}
