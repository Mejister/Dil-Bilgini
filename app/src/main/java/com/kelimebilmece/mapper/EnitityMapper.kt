package com.kelimebilmece.mapper

interface EnitityMapper<Q,A> {
    fun fromEntity(entity:Q):A
    fun toEntity(domainModel:A):Q
}
