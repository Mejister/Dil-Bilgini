package com.test.kelimebilgini.mapper

interface EnitityMapper<Q,A> {
    fun fromEntity(entity:Q):A
    fun toEntity(domainModel:A):Q
}
