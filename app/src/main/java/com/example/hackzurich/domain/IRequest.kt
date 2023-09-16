package com.example.hackzurich.domain

interface IRequest<T> {

    fun fromDomainObject(): T
}