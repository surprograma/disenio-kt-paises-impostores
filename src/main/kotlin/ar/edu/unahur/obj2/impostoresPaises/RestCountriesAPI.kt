package ar.edu.unahur.obj2.impostoresPaises

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

// Pueden mirar cómo está hecho si les da curiosidad,
// pero no pueden cambiar absolutamente nada de este archivo.

class RestCountriesAPI {
  private val urlBase = "https://restcountries.eu/rest/v2"
  private val client = OkHttpClient()
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

  private val countriesAdapter = crearAdapter<List<Country>>(
    Types.newParameterizedType(List::class.java, Country::class.java)
  )

  private val countryAdapter = crearAdapter<Country>(Country::class.java)

  fun todosLosPaises() = obtenerRespuesta("/all", countriesAdapter)!!

  fun buscarPaisesPorNombre(nombre: String) =
    obtenerRespuesta("/name/${nombre}", countriesAdapter).orEmpty()

  fun paisConCodigo(codigoIso3: String) =
    checkNotNull(
      obtenerRespuesta("/alpha/${codigoIso3}", countryAdapter),
      { "No se encontró ningún país con el código $codigoIso3" }
    )

  private fun <T> obtenerRespuesta(ruta: String, adapter: JsonAdapter<T>): T? {
    val response = client.newCall(crearRequest(urlBase + ruta)).execute()
    return if (response.isSuccessful) { adapter.fromJson(response.body!!.source())!! } else { null }
  }

  private fun crearRequest(url: String): Request {
    return Request.Builder()
      .url(url)
      .build()
  }

  private fun <T> crearAdapter(type: Type): JsonAdapter<T> {
    return moshi.adapter(type)
  }
}

// Tomamos solamente un subconjunto de la información que da la API.
// Todos los campos disponibles pueden verse en http://restcountries.eu/#api-endpoints-response-example.

data class Country(
  val name: String,
  val alpha3Code: String,
  val capital: String,
  val region: String,
  val population: Long,
  val borders: List<String>,
  val languages: List<Language>,
  val regionalBlocs: List<RegionalBloc>
)

data class Language(
  val name: String
)

data class RegionalBloc(
  val acronym: String,
  val name: String
)
