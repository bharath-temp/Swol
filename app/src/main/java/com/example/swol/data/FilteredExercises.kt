package com.example.swol.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilteredExercises(
    @Json(name = "suggestions") val exercises: List<Exercise>
)

@JsonClass(generateAdapter = true)
data class Exercise(
    @Json(name = "data") val data: Datapoint
)

@JsonClass(generateAdapter = true)
data class Datapoint(
    @Json(name = "name") val name: String,
    @Json(name = "category") val category: String
)
