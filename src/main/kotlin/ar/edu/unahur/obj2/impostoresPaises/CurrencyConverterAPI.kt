package ar.edu.unahur.obj2.impostoresPaises

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

// Pueden mirar cómo está hecho si les da curiosidad,
// pero no pueden cambiar absolutamente nada de este archivo.

class CurrencyConverterAPI {
  private val urlBase = "https://free.currconv.com/api/v7/convert?compact=ultra&apiKey=d04de9e40952afdda643&q=USD_"
  private val client = OkHttpClient()
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
  private val cache = mutableMapOf<String, Any?>()

  private val currencyAdapter = crearAdapter<Map<String, Double>>(
    Types.newParameterizedType(
      MutableMap::class.java,
      String::class.java,
      Double::class.javaObjectType
    )
  )

  fun convertirDolarA(codigoMoneda: String) =
    obtenerRecurso(codigoMoneda, currencyAdapter)!!["USD_${codigoMoneda}"]

  private fun <T> obtenerRecurso(ruta: String, adapter: JsonAdapter<T>) =
    cache.getOrPut(ruta) {
      obtenerDeLaAPI(ruta, adapter)
    } as T?

  private fun <T> obtenerDeLaAPI(ruta: String, adapter: JsonAdapter<T>): T? {
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
