package br.com.fornaro.mesanews.tools

import br.com.fornaro.mesanews.data.dispatchers.DispatcherMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object UnitTestDispatcherMap : DispatcherMap {

    override val io: CoroutineDispatcher = Dispatchers.Unconfined
    override val ui: CoroutineDispatcher = Dispatchers.Unconfined
    override val computation: CoroutineDispatcher = Dispatchers.Unconfined
}
