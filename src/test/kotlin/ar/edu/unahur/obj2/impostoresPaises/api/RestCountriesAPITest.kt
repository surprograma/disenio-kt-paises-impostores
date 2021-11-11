package ar.edu.unahur.obj2.impostoresPaises.api

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe

// Estos tests están simplemente como ejemplo de lo que **no** hay que hacer.
// Prueben de ejecutarlos sin internet y van a ver cómo fallan miserablemente.

class RestCountriesAPITest : DescribeSpec({
  describe("API de países") {
    val api = RestCountriesAPI()

    val caboVerde =
      Country(
        "Cabo Verde",
        "CPV",
        "Praia",
        "Africa",
        555988,
        4033.0,
        emptyList(),
        listOf(Language("Portuguese")),
        listOf(RegionalBloc("AU", "African Union")),
        listOf(Currency("CVE"))
      )

    val bolivia =
      Country(
        "Bolivia (Plurinational State of)",
        "BOL",
        "Sucre",
        "Americas",
        11673029,
        1098581.0,
        listOf("ARG", "BRA", "CHL", "PRY", "PER"),
        listOf(Language("Spanish"), Language("Aymara"), Language("Quechua")),
        listOf(RegionalBloc("USAN", "Union of South American Nations")),
        listOf(Currency("BOB"))
      )

    it("buscar por nombre") {
      val paisesCaboVerde = api.buscarPaisesPorNombre("cabo verde")
      paisesCaboVerde.shouldContainExactly(caboVerde)
    }

    it("buscar por código") {
      api.paisConCodigo("BOL").shouldBe(bolivia)
    }

    it("info de todos los países") {
      api.todosLosPaises().shouldHaveSize(250)
    }
  }
})
