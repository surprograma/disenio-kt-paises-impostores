package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.extensions.junitxml.JunitXmlReporter

class KotestConfig: AbstractProjectConfig() {
  // Modificamos esta configuración para que se creen nuevos objetos para cada `it`.
  override val isolationMode = IsolationMode.InstancePerLeaf

  // Reporta el resultado de los tests como XML, lo cual usamos para que se muestren en el pull request cuando fallan.
  override fun listeners() = listOf(JunitXmlReporter())
}
