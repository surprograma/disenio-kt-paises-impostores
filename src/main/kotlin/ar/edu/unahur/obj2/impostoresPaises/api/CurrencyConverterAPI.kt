package ar.edu.unahur.obj2.impostoresPaises.api

import com.squareup.moshi.Types

/*
👀 ¡¡ATENCIÓN!!
El código de este archivo *funciona* tal cual está y no debe realizarse ninguna modificación.
Lo incluimos en el proyecto únicamente con fines didácticos, para quienes quieran ver cómo
está hecho. El ejercicio se tiene que resolver sin alterar para nada este archivo.
 */

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
