import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

// Algunos ejemplos para que jueguen un poco
// con lo que devuelve la API

val api = RestCountriesAPI()
api.buscarPaisesPorNombre("guay")
api.paisConCodigo("CHL")

val currencyApi = CurrencyConverterAPI("poné acá la API key")
// PEN es el código del sol peruano
currencyApi.convertirDolarA("PEN")
