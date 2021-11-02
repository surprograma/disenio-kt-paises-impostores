package ar.edu.unahur.obj2.impostoresPaises

import com.squareup.moshi.Types

// Pueden mirar cómo está hecho si les da curiosidad,
// pero no pueden cambiar absolutamente nada de este archivo.

class CurrencyConverterAPI : RestAPI() {
  override val urlBase = "https://free.currconv.com/api/v7/convert?compact=ultra&apiKey=d04de9e40952afdda643&q=USD_"

  private val currencyAdapter = crearAdapter<Map<String, Double>>(
    Types.newParameterizedType(
      MutableMap::class.java,
      String::class.java,
      Double::class.javaObjectType
    )
  )

  fun convertirDolarA(codigoMoneda: String) =
    obtenerRecurso(codigoMoneda, currencyAdapter)!!["USD_${codigoMoneda}"]
}
