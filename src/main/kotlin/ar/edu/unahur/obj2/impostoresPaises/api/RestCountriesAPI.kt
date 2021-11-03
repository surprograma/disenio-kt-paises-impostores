package ar.edu.unahur.obj2.impostoresPaises.api

import com.squareup.moshi.Types

// Pueden mirar cómo está hecho si les da curiosidad,
// pero no pueden cambiar absolutamente nada de este archivo.

class RestCountriesAPI : RestAPI() {
  private val countriesAdapter = crearAdapter<List<Country>>(
    Types.newParameterizedType(List::class.java, Country::class.java)
  )

  private val countryAdapter = crearAdapter<Country>(Country::class.java)

  override val urlBase = "https://restcountries.com/v2"

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
  val capital: String?,
  val region: String,
  val population: Long,
  val area: Double?,
  var borders: List<String>?,
  val languages: List<Language>,
  var regionalBlocs: List<RegionalBloc>?
) {
  init {
    borders = borders.orEmpty()
    regionalBlocs = regionalBlocs.orEmpty()
  }
}

data class Language(
  val name: String
)

data class RegionalBloc(
  val acronym: String,
  val name: String
)
