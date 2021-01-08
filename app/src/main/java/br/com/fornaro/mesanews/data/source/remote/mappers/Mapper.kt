package br.com.fornaro.mesanews.data.source.remote.mappers

interface Mapper<T, R> {
    fun map(input: T): R
}