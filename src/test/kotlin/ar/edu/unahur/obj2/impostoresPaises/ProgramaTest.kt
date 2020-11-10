package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

class ProgramaTest : DescribeSpec({
  describe("Programa") {
    val consolaMock = mockk<EntradaSalida>()

    // Configuramos un mock para la entrada salida
    // TODO: hacer lo mismo para la RestCountriesAPI
    Programa.entradaSalida = consolaMock

    // Indicamos que los llamados a `escribirLinea` no hacen nada (just Runs)
    every { consolaMock.escribirLinea(any()) } just Runs

    it("buscar país") {
      // Cuando se llame a `leerLinea()`, simulamos que el/la usuaria escribió "thailand"
      every { consolaMock.leerLinea() } returns "thailand"

      // Iniciamos el programa
      Programa.iniciar()

      // Verificamos que se escribió "por pantalla" el resultado esperado
      verify {
        consolaMock.escribirLinea("Thailand (THA) es un país de Asia, con una población de 65327652 habitantes.")
      }
    }
  }
})
