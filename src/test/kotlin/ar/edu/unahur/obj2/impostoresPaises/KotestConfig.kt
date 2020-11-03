package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode

class KotestConfig: AbstractProjectConfig() {
  // Modificamos esta configuración para que se creen nuevos objetos para cada `it`.
  override val isolationMode = IsolationMode.InstancePerLeaf
}
