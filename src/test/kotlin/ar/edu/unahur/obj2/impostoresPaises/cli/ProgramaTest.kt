package ar.edu.unahur.obj2.impostoresPaises.cli

import io.kotest.core.spec.style.DescribeSpec
import io.mockk.*

class ProgramaTest : DescribeSpec({
  describe("Programa") {
    val consolaMock = mockk<Consola>()

    // Configuramos un mock para la entrada salida
    // TODO: hacer lo mismo para la RestCountriesAPI
    Programa.entradaSalida = consolaMock

    // Indicamos que los llamados a `escribirLinea` no hacen nada (just Runs)
    every { consolaMock.escribirLinea(any()) } just Runs

    it("buscar país") {
      // Cuando se llame a `leerLinea()`, simulamos que el/la usuaria escribió "thailand".
      // Notar que esto lo configuramos *antes* de iniciar el programa,
      // para que cuando efectivamente se llame al método ya el mock sepa qué tiene que hacer.
      every { consolaMock.leerLinea() } returns "thailand"

      // Iniciamos el programa
      Programa.iniciar()

      // Verificamos que se escribió "por pantalla" el resultado esperado
      verify {
        consolaMock.escribirLinea("Thailand (THA) es un país de Asia, con una población de 69799978 habitantes.")
      }
    }
  }
})
