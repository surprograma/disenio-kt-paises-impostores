package ar.edu.unahur.obj2.impostoresPaises.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.Type

/*
👀 ¡¡ATENCIÓN!!
El código de este archivo *funciona* tal cual está y no debe realizarse ninguna modificación.
Lo incluimos en el proyecto únicamente con fines didácticos, para quienes quieran ver cómo
está hecho. El ejercicio se tiene que resolver sin alterar para nada este archivo.
 */

abstract class RestAPI {
  private val client = OkHttpClient()
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
  private val cache = mutableMapOf<String, Any?>()

  protected abstract val urlBase: String

  protected fun <T> obtenerRecurso(ruta: String, adapter: JsonAdapter<T>) =
    cache.getOrPut(ruta) {
      obtenerDeLaAPI(ruta, adapter)
    } as T?

  protected fun <T> crearAdapter(type: Type): JsonAdapter<T> {
    return moshi.adapter(type)
  }

  private fun <T> obtenerDeLaAPI(ruta: String, adapter: JsonAdapter<T>): T? {
    val response = client.newCall(crearRequest(urlBase + ruta)).execute()
    if (!response.isSuccessful) {
      return null
    }

    val json = response.body!!.string()

    // Horrible, pero la RestCountriesAPI en vez de tirar un 404,
    // devuelve un objeto que dice que es un 404.
    if (json.contains("\"status\":404")) {
      return null
    }

    return adapter.fromJson(json)
  }

  private fun crearRequest(url: String): Request {
    return Request.Builder()
      .url(url)
      .build()
  }
}
