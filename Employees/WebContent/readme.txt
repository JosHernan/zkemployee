@author:
JoseGpe.
developer JavaWEB.
​https://joshernan.github.io/
¿Qué es Spring?
Spring es un framework para desarrollar aplicaciones empresariales Java. El beneficio de usar Spring sobre otros frameworks es que es de código abierto. Esto significa que los desarrolladores puede construir código reutilizable sin depender de un proveedor. Otra ventaja sustancial de Spring es su estructura en capas que le permite elegir sólo aquellos componentes que necesite mientras que le ofrece un framework de desarrollo de aplicaciones J2EE sin fisuras.

Liste las ventajas del framework Spring
Spring tiene las siguientes ventajas:

Arquitectura en capas que le permite usar lo que necesita prescindiendo de lo que no necesita.

Spring permite a los desarrolladores centrarse en la programacion de Objetos Planos Java (POJO). Esto permite realizar pruebas e integración continuas.

Siendo de código abierto, no hay condiciones de proveedor.

La Inyección de Dependencias y la inversión del control hace la Conectividad a Base de Datos Java más simple (JDBC).

Liste algunas características de Spring
Contenedor:

Spring gestiona y contiene la configuración y ciclo de vida de aplicacion de los objetos.

Ligero:

Cuando se trata de transparencia y tamaño, Spring es un framework de aplicaciones ligero. La versión más ligera del framework de Spring sólo ocupa 1 MB. Además, la sobrecarga en términos de procesado es similarmente minúscula.

Framework MVC:

Spring utiliza el framework web modelo-vista-controlador (MVC) que se construye como el núcleo de funcionalidad Spring. Este framework puede dar cabida a muchas tecnologías de presentación como JSP, Tiles, Velocity, POI, e iText. Además, es altamente configurable con el uso de interfaces de estrategia. No obstante, otros muchos frameworks pueden ser usados fácilmente en lugar de Spring MVC Framework.

Inversión de Control (IOC):

Spring consigue un bajo acoplamiento a través del uso de la Inversión de Control. Los objetos proporcionan sus dependencias en lugar de buscar o crear objetos de dependencia.

Gestión de Transacciones:

Spring framework proporcion una capa abstracta genérica para llevar a cabo la gestión de transacciones. Esto permite al desarrollador incluir gestores de transacciones mientras que hace muy sencillo el separar transacciones y evitar problemas de bajo nivel. Este soporte de transacciones no está conectado al entorno J2REE. También puede ser utilizado en entornos sin contenedores.

Programación Orientada a Aspectos (AOP):

Al separar los servicios de sistema de la lógica de negocio de la aplicación, Spring soporta la programación orientada a aspectos. Esto permite también el despliegue cohesivo de aplicaciones.

Manejo de Excepciones JDBC:

La capa de abstracción para la Conectividad de Base de Datos Java (JDBC) proporciona una jerarquía de excepciones muy útil. Esto permite desarrollar de forma más fácil una estrategia de gestión de errores. Adicionalmente, Spring ofrece una gran integración con servicios como JDO, IBATIS, e Hibernate.

¿Qué es la Inyección de Dependencias (IOC)?
La Inyección de Dependencias o la Inversión de Control (IOC), en su nivel más básico, le permite describir cómo los objetos deberían ser creados en lugar de crearlos directamente. Es decir, describe los servicios que necesitan diferentes componentes usando un fichero de configuración en lugar de conectar directamente estos componentes y servicios por código. En el caso del framework Spring, estos servicios y componentes se conectan luego por el contenedor IOC.

Por ejemplo, a los objetos se les dan sus dependencias cuando son creados por un proceso externo que gestiona cada objeto dentro del sistema. En otras palabras, las dependencias se insertan en los objetos. Esto es contrario a la forma en que las dependencias se tratan en otros frameworks, que es por que se acuñó el término inversión de control. Esto representa una inversión de la responsabilidad de crear referencias a dependencias dentro de los objetos. Aquí tiene un curso que le puede enseñar más sobre la Orientación a Objetos con Spring MVC.

Liste algunos tipos distintos de Inyecciones de Dependencia (IOC)
Los tres tipos de inyecciones de dependencia, o IOC, son:

Inyección Setter (usada por Spring): Las propiedades JavaBeans se usan para asignar dependencias.

Inyección de Interfaz (usada por Avalon): Una interfaz se utiliza para la inyección.

Inyección de Constructor (usada por Spring, Contenedor Pico, y otros): los parámetros del Constructor se usan para proporcionar dependencias.

Es importante que tenga en cuenta que Spring sólo soporta Inyecciones Setter y de Constructor.



¿Por qué no hay variables globales en Java?
Debido a que las variables globales son accesibles, por lo que infringen la transparencia referencial, además de crear colisiones en el espacio de nombres.

¿Qué significa la palabra clave “static”, y dónde puede ser usada?
Dependiendo el contexto cambia su uso y significado. Variables static, están compartidas por la clase, no por una instancia concreta. Métodos static, compartido a nivel de clase. Clase static, clases internas que no están atadas a su clase contenedora. Además, static puede usarse en un bloque de código de una clase para especificar código que se ejecutará cuando la máquina virtual se inicia por primera vez.

¿De qué manera tratarías problemas de dependencia?
La pregunta podría resultar ambigua, pero es el propósito, ya que puede referirse a resolver problemas de inyección de dependencias, también a proyectar dependencias, usando librerías externas, de terceras personas.

¿Cómo pruebas tu código?
Aquí deberás hablar de tu experiencia usando librerías como JUnit, Mockito y Selenium. Si bien puede ser que no tengas mucha experiencia usando librerías, si eres capaz de mencionarlas te dará puntos. También podrías investigar y comenzar a estudiar del Desarrollo Orientado a Pruebas (TDD), que actualmente está adquiriendo popularidad.

¿De qué manera crearías una clase simple que tenga tres variables?
Aunque a primera vista podría resultar sencilla, pues se explica en clases como Programación Orientada a Objetos, la experiencia indica que será necesario saber cómo sobrescribir correctamente los métodos hashCode() y equals() (usando, por ejemplo, EqualsBuilder y HashCodeBuilder, en la librería Apache Commons).

¿Qué es el borrado de tipos?
El borrado de tipos es un fenómeno JVM que significa que en tiempo de ejecución no se tiene conocimiento de los tipos de objetos genéricos, como List<Integer> (el compilador ve a todos los objetos Lista teniendo el mismo tipo, List<Object>).

¿Cuándo y por qué son los getters y setters importantes?
El concepto principal que hay que saber para las entrevistas es que los setters y getters pueden formar parte de interfaces y esconder detalles de la implementación, de forma que no hay que hacer a las variables miembros públicas.

Estas preguntas podrían parecer sencillas pero al momento de estar en una entrevista técnica de trabajo, debido a los nervios podrías flaquear, así que vale la pena hacer un último repaso de tus conocimientos.

