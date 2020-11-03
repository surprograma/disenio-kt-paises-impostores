package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot

class RestCountriesAPITest : DescribeSpec({
  describe("Países") {
    val paisesDeLatinoamerica = listOf("bolivia", "argentina", "chile", "peru", "paraguay", "venezuela", "colombia")

    it("buscar por nombre - con la API posta") {
      val api = RestCountriesAPI()

      paisesDeLatinoamerica.forEach {
        val paises = api.buscarPaisesPorNombre(it)
        paises.shouldHaveSize(1)
      }
    }

    it("buscar por nombre - usando un impostor") {
      val api = mockk<RestCountriesAPI>()

      // TODO: mejorar esto
      every { api.buscarPaisesPorNombre(any()) } returns
        listOf(
          Country(
            "Argentina",
            "La capital de Argentina", listOf(), 1000000, listOf(), listOf()))

      paisesDeLatinoamerica.forEach {
        val paises = api.buscarPaisesPorNombre(it)
        paises.shouldHaveSize(1)
      }
    }
  }
})
