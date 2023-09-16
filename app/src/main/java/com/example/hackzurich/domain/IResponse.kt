package com.example.hackzurich.domain

interface IResponse<T> {

    fun toDomainObject(): T

}