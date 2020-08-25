package ar.edu.unahur.obj2.ejemplo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.lang.ArithmeticException

class CalculadoraTest : DescribeSpec({
  describe("Operaciones aritméticas") {
    it("suma") {
      Calculadora(3, 2).suma().shouldBe(5)
    }

    describe("division") {
      it("por cero") {
        shouldThrow<ArithmeticException> { Calculadora(10, 0).division() }
      }

      it("por número distinto a cero") {
        Calculadora(12, 2).division().shouldBe(6)
      }
    }
  }
})
