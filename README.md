# Países impostores

![Portada](assets/portada.jpg)

## Antes de empezar: algunos consejos

El enunciado tiene **mucha** información, van a necesitar leerlo varias veces. La sugerencia es que lo lean entero una vez (para tener una idea general) y luego vuelvan a consultarlo las veces que hagan falta.

Concentrensé en los requerimientos y, excepto que se traben mucho, respeten el orden sugerido. No es necesario que hagan TDD, pero sí sería interesante que vayan creando las distintas clases y métodos a medida que resuelven cada requerimiento y no antes. 

En otras palabras: trabajen completando cada requerimiento antes de pasar al siguiente, con los tests que aseguran que funciona incluidos. Si al avanzar en los requerimientos les parece necesario refactorizar, adelante, van a tener los tests que garantizan que no rompieron nada. :smirk: 

## Descripción del dominio

Desde un observatorio de políticas públicas mundiales nos piden desarrollar un sistema para realizar estadísticas sobre distintos países del mundo. Según detallaron, de cada país les interesaría saber:

* su nombre,
* su código ISO-3,
* su población,
* en qué continente está ubicado,
* la lista de países con los que limita,
* la lista de bloques regionales en los que participa,
* la lista de idiomas oficiales.

Por simplicidad, manejaremos al continente, los bloques regionales y los idiomas como `String`. Por ejemplo, podríamos representar a Bolivia con los siguientes datos:

```kotlin
nombre: "Bolivia"
codigoIso3: "BOL"
poblacion: 10985059
continente: "América"
// Notar que estos son objetos, no strings
paisesLimitrofes: [argentina, brasil, chile, paraguay, peru]  
bloquesRegionales: ["UNASUR"]
idiomasOficiales: ["Español", "Quechua", "Aymara"]
```

## Requerimientos

Dividimos los requerimientos en 3 étapas distintas, que deben ser respetadas. El ejercicio está planteado de esta manera para que el diseño les quede más prolijo y desacoplado.

### Etapa 1 - consultas

Crear al Observatorio, que es un objeto que conoce a todos los países y debe poder responder las consultas que se enuncian a continuación. 

Para dos países en particular, **cuyos nombres** se envían por parámetro:

1. Poder consultar si son limítrofes.
1. Saber si se necesita traducción para que puedan dialogar. Esto ocurre si no comparten ninguno de sus idiomas oficiales.
1. Conocer si son potenciales aliados. Esto es así cuando no necesitan traducción y además comparten algún bloque regional.

Ojo, que se pide (a propósito) que los parámetros sean **los nombres** de los países y no los objetos que los representan. Para este punto puede resultar un poco molesto, pero nos va a facilitar la etapa siguiente.

Sobre el conjunto de todos los países:

4. Obtener los nombres de los 5 países con mayor población.
5. Indicar cuál es el continente más poblado.

Obviamente, para esta etapa hay que incluir todos los tests correspondientes.

### Etapa 2 - Conectando con el mundo real

Queremos ahora modificar al observatorio para que pueda resolver todos los requerimientos anteriores, pero esta vez interactuando con la [API RestCountries](http://restcountries.eu/). Esta [API](https://es.wikipedia.org/wiki/Interfaz_de_programaci%C3%B3n_de_aplicaciones) es un servicio gratuito que brinda información real sobre los países del mundo.

Para facilitarles la interacción con dicho servicio y que no tengan que preocuparse por cuestiones propias de la interacción HTTP, les dejamos la clase `RestCountriesAPI`, que provee tres métodos para hacer consultas:

```kotlin
// Devuelve una lista con todos los países del mundo.
todosLosPaises(): List<Country>

// Devuelve todos los países cuyo nombre incluya el String que viene por parámetro. 
// Ejemplo: si ponemos "guay" devolverá a Paraguay y Uruguay, 
// pero si ponemos "uruguay" devolverá solo a este último.
buscarPaisesPorNombre(nombre: String): List<Country>

// Devuelve al país cuyo código ISO 3 coincide con el parámetro. 
// Arroja un error si no existe ningún país con ese código.
paisConCodigo(codigoIso3: String): Country
```

Nótese que los objetos que devuelve esta clase son de tipo `Country`, y que probablemente tengan una estructura diferente a los que ustedes crearon en la etapa anterior. Para no tener que tirar todo el código del Observatorio, conviene crear un objeto que oficie de _adaptador_ entre el `Country` que devuelve la API y el `Pais` que ustedes crearon.

Se pide entonces:

1. Hacer un objeto o clase que se encargue de realizar la conversión de `Country` a `Pais`. Este objeto debería conocer a la API, para poder hacer las consultas necesarias. Incluir los tests correspondientes.
1. Modificar al Observatorio y a sus tests para que funcionen con la API. Para ello, van a tener que utilizar tanto la API como el objeto o clase que hayan creado en el requerimiento anterior. Si en la etapa anterior hicieron un buen diseño, no deberían necesitar modificar mucho el código.

### Etapa 3 - API impostora

Los tests de la etapa anterior tienen un gran problema - cada vez que los ejecutamos hacen varios llamados a la API. Esto, además de ser bastante más lento que un test "puro", tiene otras desventajas: no podemos correr los tests sin acceso a internet, nos acopla fuertemente con un servicio externo, no podemos decidir sobre los datos, etc.

Para solucionar esto, se pide modificar todos los tests que utilicen la API, reemplazandola por un impostor implementado con [mockk](https://mockk.io/).

### Etapa 4 - Usando la aplicación

Llegó el momento de realmente conectar a nuestro programa con el mundo real, permitiendo que "cualquier persona" (que tenga una computadora, Kotlin y los conocimientos necesarios para ejecutarlo) pueda utilizarlo.

Para ello, vamos a programar una pequeña CLI, _command line interface_ o _interfaz por línea de comandos_, que nos permita acceder a los requerimientos de la etapa 1, trayendo la información de la RestCountries API. El diseño de la interfaz queda librado a su creatividad, siempre y cuando cumpla con los siguientes requerimientos:

1. Deben poder realizarse todas las consultas de la etapa 1, interactuando con la API real.
1. En caso de error, hay que mostrar algún mensaje amigable.
1. Incluir al menos un test por cada opción que tenga el CLI, y alguno donde se muestre un error. Simular la interacción de usuario utilizando mockk.

A modo de ejemplo, les dejamos unos GIFs mostrando podría ser la interacción:

_Caso feliz :smiley:_
![CLI](assets/cli.gif)

_Error :cry:_
![CLI error](assets/cli-error.gif)

## Créditos

Enunciado original creado por Federico Aloi para UNaHur, levemente inspirado en un enunciado de Carlos Lombardi.

[![CC BY-SA 4.0][cc-by-sa-image]][cc-by-sa]

Esta obra está bajo una [Licencia Creative Commons Atribución-CompartirIgual 4.0 Internacional][cc-by-sa].

[cc-by-sa]: https://creativecommons.org/licenses/by-sa/4.0/deed.es
[cc-by-sa-image]: https://licensebuttons.net/l/by-sa/4.0/88x31.png
