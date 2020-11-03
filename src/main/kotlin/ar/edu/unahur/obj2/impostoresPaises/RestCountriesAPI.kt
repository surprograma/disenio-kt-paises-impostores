package ar.edu.unahur.obj2.impostoresPaises

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

class RestCountriesAPI {
  private val client = OkHttpClient()
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

  private val countryAdapter = crearCountryAdapter()

  fun buscarPaisesPorNombre(nombre: String): List<Country> {
    val response = obtenerRespuesta("https://restcountries.eu/rest/v2/name/${nombre}")
    return countryAdapter.fromJson(response.body!!.source())!!
  }

  private fun obtenerRespuesta(url: String) = client.newCall(crearRequest(url)).execute()

  private fun crearRequest(url: String): Request {
    return Request.Builder()
      .url(url)
      .build()
  }

  private fun crearCountryAdapter(): JsonAdapter<List<Country>> {
    val countryListType = Types.newParameterizedType(List::class.java, Country::class.java)
    return moshi.adapter(countryListType)!!
  }
}

// Tomamos solamente un subconjunto de la información que da la API.
// Todos los campos disponibles pueden verse en http://restcountries.eu/#api-endpoints-response-example.

data class Country(
  val name: String,
  val capital: String,
  val callingCodes: List<String>,
  val population: Long,
  val borders: List<String>,
  val languages: List<Language>
)

data class Language(
  val name: String
)
