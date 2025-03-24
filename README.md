




TECNOLÓGICO NACIONAL DE MÉXICO
INSTITUTO TECNOLÓGICO DE TEPIC

Centro de Investigación Científica y de Educación Superior de Ensenada, Baja California - Unidad Académica Tepic

Sistema de adquisición de datos basados en visión artificial mediante dispositivos móviles para la detección de animales domésticos

Michael Antonio Carrillo Saucedo

Asesor interno: Dr. Antonio Navarrete Guzmán

Asesor externo: Dr. José Agustín Almaraz Damián




Tepic, Nayarit a 27 de mayo del 2025
Agradecimientos
A nuestra institución, que; sin pensar se volvió nuestro refugio. Permitiéndonos además de obtener el mayor de conocimientos en nuestras carreras, conocer a nuevos compañeros y amigos.
Amigos con los cuales compartí muchas risas, desvelos, viajes, y llantos.
A mi madre y padre que con sacrificio y orgullo permitieron que yo pudiera seguir estudiando una carrera.
A mi compañera de vida, Lizeth que ha sido mi mayor apoyo moral en días bajo presión emocionales y escolares.

Resumen
En este reporte presenta el desarrollo de una aplicación móvil para sistemas operativos Android para un sistema de adquisición de datos basado en inteligencia artificial. El propósito de la aplicación es diseñar un sistema que mediante machine learning genere un diagnóstico para animales domésticos.
Los datos a analizar se ingresan en el interfaz de usuario, se guardan los datos en formato CSV, posterior se toma una foto, se almacena y junto con los datos se analiza con la inteligencia artificial.













Descripción de la empresa u organización y del puesto o área de trabajo del estudiante
La Unidad Académica Tepic del Centro de Investigación Científica y de Educación Superior de Ensenada (CICESE Tepic) realiza investigación aplicada en el área de las Tecnologías de la Información y Comunicación (TIC), y desarrollos tecnológicos.

Problemática
Objetivos
Objetivo General
Desarrollar una aplicación móvil basada en un sistema de adquisición de datos basados en visión artificial para la detección de animales domésticos.
Objetivos específicos
•	Iniciarse en el desarrollo de aplicaciones móviles.
•	Diseño de la interfaz de usuario.
•	Desarrollo de aplicación móvil.
•	Selección de datos para el desarrollo de un modelo de Machine Learning.
•	Integración del Modelo de Machine Learning seleccionado a la aplicación móvil.

Justificación


Marco teórico
Android es un sistema operativo de código abierto para móviles basado en el núcleo de Linux que admite la construcción de aplicaciones para manejar, configurar y personalizar el dispositivo móvil; esto hace que se pueda disfrutar de una interfaz amigable.
Para desarrollar una aplicación es necesario de un entorno de desarrollo por lo que desde 2013 se anunció en la conferencia Google I/O que Android Studio es el entorno de desarrollo integrado (IDE) oficial para desarrollar aplicaciones para el SO Android
Según Android Developers, la página oficial de Google para desarrollo en android, desde su interfaz podemos acceder a una gran cantidad de opciones, entre las que destacan: 
•	Un sistema de compilación flexible basado en Gradle
•	Un emulador rápido y cargado de funciones
•	Un entorno unificado donde puedes desarrollar para todos los dispositivos Android
•	Ediciones en vivo para actualizar elementos componibles en emuladores y dispositivos físicos, en tiempo real
•	Integración con GitHub y plantillas de código para ayudarte a compilar funciones de apps comunes y también importar código de muestra
•	Variedad de marcos de trabajo y herramientas de prueba
•	Herramientas de Lint para identificar problemas de rendimiento, usabilidad y compatibilidad de versiones, entre otros
•	Compatibilidad con C++ y NDK
•	Compatibilidad integrada con Google Cloud Platform, que facilita la integración con Google Cloud Messaging y App Engine.

El desarrollo de aplicaciones se conforma de la capa de presentación FrontEnd y una capa de acceso a datos BackEnd. El frontend trabaja la interfaz visual, y hace que el usuario pueda interactuar con nuestro sitio o sistema (Ibarra et al. 2021).
Frontend es la parte de una aplicación que interactúa con los usuarios, es conocida como el lado del cliente. Básicamente es todo lo que vemos en la pantalla cuando accedemos a un sitio web o aplicación: tipos de letra, colores, adaptación para distintas pantallas, los efectos del ratón, teclado, movimientos, desplazamientos, efectos visuales y otros elementos que permiten navegar dentro de una página web.
Por otro lado,	el backend se encarga de la lógica detrás de una aplicación web. Esto incluye la gestión de la base de datos, la seguridad y la lógica de negocio de la aplicación (Celi et al. 2023).

En Android, el diseño y la lógica de una pantalla están separados en dos ficheros distintos. Por un lado, en el fichero “/res/layout/activity_main.xml” tendremos el diseño puramente visual de la pantalla definido como fichero XML y por otro lado, en el fichero “/src/main/java/com/example/nombre_proyecto/App/MainActivity.kt, encontraremos el código Kotlin que determina la lógica de la pantalla. Kotlin es un lenguaje de programación moderno, pero ya consolidado, diseñado para facilitar la vida a los desarrolladores. Es conciso, seguro, interoperable con Java y otros lenguajes, y ofrece múltiples maneras de reutilizar código entre múltiples plataformas para una programación productiva (Kotlin, 2025)

(activity, intents)
El machine learning es un subcampo de la inteligencia artificial. Este ayuda a los ordenadores a aprender y actuar como seres humanos con la ayuda de algoritmos y datos. Dado un conjunto de datos, un algoritmo de machine learning aprende diferentes propiedades de los datos e infiere las propiedades de los datos que se pueden presentar en el futuro.
Sistema de adquisición de datos
Según Vallejo Valencia, M. y Arias Londoño, A. (2022) un sistema de adquisición de datos (SAD) es un conjunto de elementos que permiten la medición, de variables físicas y el muestreo, digitalización y análisis de esta información, de tal manera que es llevada a un computador u otro dispositivo digital donde puede ser desplegada, procesada o almacenada en los sistemas de guardado estático, como lo pueden ser archivos o base de datos.

Desarrollo
Procedimiento y descripción de actividades realizadas
Iniciarse en el desarrollo de aplicaciones móviles
Se investigó la documentación para el desarrollo de aplicaciones móviles para Android. Se inició con la descarga del entorno de desarrollo integrado Android Studio desde su sitio web Android Developers y posterior instalación.
Mediante la documentación más actual del lenguaje Kotlin se sentó las bases para la estructura de un programa funcional, así como el manejo de datos. Ya que los lenguajes comparten cierta similitud en la estructura del programa y el manejo de datos, esta actividad se realizó con éxito.
Se desarrollo una app piloto para probar la funcionalidad de lo aprendido, en la cual se programó un XML para el frontend con un botón en la pantalla principal y un mensaje en una pantalla secundaria. Mediante kotlin en backend se programó la lógica donde al presionar el botón en la pantalla principal desplazó a la segunda pantalla en la cual se mostró el mensaje.

Diseño de interfaz de usuario
La idea dada por parte del asesor externo era una interfaz con tres campos de entrada de datos, mismo en los que el usuario ingresaría los datos mediante el teclado del dispositivo movil, dos deslizadores con un rango de 1 a 10 de variables indefinidas y por último un botón para guardar dichos datos.
En el layout de la pantalla principal (main activity) se inició el diseño con lenguaje XML. Para los apartados de entrada de datos se utilizó el componente LinearLayout para contener dentro el componente ViewText para el nombre de dato a ingresar, y AppCompatEditText donde al seleccionarlo se despliega el teclado para escribir e ingresar el dato. En la figura # muestra parte del código.













La figura anterior se hizo para las tres variables que el usuario puede ingresar. A cada AppCompatEditText se le agregó un Id para identificarlo y conectarlo con la lógica en backend, y a cada TextView el nombre de la variable para mostrar que dato debe ingresarse.
Para los dos deslizadores se utilizó el componente RangeSlider con valor mínimo 0 y máximo 10 con avance de una unidad. Además, para diferenciar entre un deslizador y otro, se agregó un LinearLayout que contenía dos TextView los cuales permitían distinguir la escala 1 de la escala 2; el otro TextView muestra en qué valor está el deslizador. La figura # muestra parte del código.












Por último, para el botón de guardado se utilizó el componente AppCompatButton, el cual se identificó con una etiqueta de texto “GUARDAR” y un ID “btnGuardar”. La figura # muestra parte del código.




Desarrollo de aplicación móvil


