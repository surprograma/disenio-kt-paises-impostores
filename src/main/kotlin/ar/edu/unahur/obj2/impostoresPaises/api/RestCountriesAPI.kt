package ar.edu.unahur.obj2.impostoresPaises.api

import com.squareup.moshi.Types

/*
👀 ¡¡ATENCIÓN!!
El código de este archivo *funciona* tal cual está y no debe realizarse ninguna modificación.
Lo incluimos en el proyecto únicamente con fines didácticos, para quienes quieran ver cómo
está hecho. El ejercicio se tiene que resolver sin alterar para nada este archivo.
 */

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
  var regionalBlocs: List<RegionalBloc>?,
  var currencies: List<Currency>?
) {
  init {
    borders = borders.orEmpty()
    regionalBlocs = regionalBlocs.orEmpty()
    currencies = currencies.orEmpty()
  }
}

data class Language(
  val name: String
)

data class RegionalBloc(
  val acronym: String,
  val name: String
)

data class Currency(
  val code: String
)
