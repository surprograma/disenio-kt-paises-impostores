import ar.edu.unahur.obj2.impostoresPaises.RestCountriesAPI

// Algunos ejemplos para que jueguen un poco
// con lo que devuelve la API

val api = RestCountriesAPI()

api.buscarPaisesPorNombre("guay")

api.paisConCodigo("CHL")
