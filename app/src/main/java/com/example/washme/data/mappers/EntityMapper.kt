package com.example.washme.data.mappers

interface EntityMapper<DomainModel, ForeignModel> {
    fun mapToModel(fromModel: ForeignModel): DomainModel
}