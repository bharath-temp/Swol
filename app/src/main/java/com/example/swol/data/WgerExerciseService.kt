package com.example.swol.data

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/*
curl -X 'GET' \
  'https://wger.de/api/v2/exercise/search/?language=english&term=biceps' \
  -H 'accept: application/json'

'https://wger.de/api/v2/exercise/search/?language=english&term=biceps'
Token:
a03189c511637bcdf9c1f7223d741852b87f437c

curl -X GET https://wger.de/api/v2/workout/ \
     -H 'Authorization: Token a03189c511637bcdf9c1f7223d741852b87f437c'
 */
interface WgerExerciseService {
    @GET("api/v2/exercise/search/")
    suspend fun searchExercise(
        @Header("Authorization") token: String = "Token a03189c511637bcdf9c1f7223d741852b87f437c",
        @Query("language") language: String = "english",
        @Query("term") term: String
    ): Response<FilteredExercises>

    companion object {
        const val BASE_URL = "https://wger.de/"

        fun create(): WgerExerciseService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(WgerExerciseService::class.java)
        }
    }
}