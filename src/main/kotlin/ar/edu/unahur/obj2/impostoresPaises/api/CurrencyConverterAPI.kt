package ar.edu.unahur.obj2.impostoresPaises.api

import com.squareup.moshi.Types

// Pueden mirar cómo está hecho si les da curiosidad,
// pero no pueden cambiar absolutamente nada de este archivo.

class CurrencyConverterAPI(apiKey: String) : RestAPI() {
  private val currencyAdapter = crearAdapter<Map<String, Double>>(
    Types.newParameterizedType(
      MutableMap::class.java,
      String::class.java,
      Double::class.javaObjectType
    )
  )

  override val urlBase = "https://free.currconv.com/api/v7/convert?compact=ultra&apiKey=$apiKey"

  fun convertirDolarA(codigoMoneda: String) =
    obtenerRecurso("&q=USD_${codigoMoneda}", currencyAdapter)!!["USD_${codigoMoneda}"]
}
