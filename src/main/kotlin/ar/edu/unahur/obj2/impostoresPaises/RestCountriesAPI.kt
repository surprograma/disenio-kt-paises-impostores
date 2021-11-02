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

class RestCountriesAPI : RestAPI() {
  override val urlBase = "https://restcountries.com/v2"

  private val countriesAdapter = crearAdapter<List<Country>>(
    Types.newParameterizedType(List::class.java, Country::class.java)
  )

  private val countryAdapter = crearAdapter<Country>(Country::class.java)

  fun todosLosPaises() = obtenerRecurso("/all", countriesAdapter)!!

  fun buscarPaisesPorNombre(nombre: String) =
    obtenerRecurso("/name/${nombre}", countriesAdapter).orEmpty()

  fun paisConCodigo(codigoIso3: String) =
    checkNotNull(
      obtenerRecurso("/alpha/${codigoIso3}", countryAdapter)
    ) { "No se encontró ningún país con el código $codigoIso3" }
}

// Tomamos solamente un subconjunto de la información que da la API.
// Todos los campos disponibles pueden verse en http://restcountries.eu/#api-endpoints-response-example.

data class Country(
  val name: String,
  val alpha3Code: String,
  val capital: String,
  val region: String,
  val population: Long,
  val area: Double?,
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
