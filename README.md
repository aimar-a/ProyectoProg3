# 007 Casino

## Descripción

**007 Casino** es una aplicación de casino virtual desarrollada en Java, pensada para ofrecer una experiencia inmersiva y completa en el mundo de los juegos de azar. La plataforma incluye una variedad de seis juegos populares que permiten a los usuarios disfrutar de diferentes tipos de entretenimiento:  
- **Carreras de caballos**  
- **Ruleta**  
- **Máquinas tragamonedas (Slots)**  
- **Blackjack**  
- **Minas**  
- **Dinosaurio**  

Los usuarios deben registrarse y autenticarse a través de un sistema seguro para acceder y disfrutar plenamente de todas las funcionalidades del casino virtual. El proyecto combina entretenimiento con la aplicación de conceptos avanzados de desarrollo en Java, asegurando una experiencia fluida, dinámica y segura.




## Características

- **Base de Datos Confiable**:  
  - Almacenamiento seguro de datos de inicio de sesión para proteger las cuentas de los usuarios.  
  - Registro de información personal y transacciones con un enfoque en la privacidad y la seguridad.  
  - Gestión eficiente del saldo, historial de movimientos y preferencias de los usuarios.  
  - Opciones intuitivas para depositar y retirar fondos, asegurando un manejo transparente de los recursos.  

- **Variedad de Juegos**:  
  Amplia selección de juegos para todos los gustos, que incluye:  
  - Carreras de caballos  
  - Ruleta  
  - Slots  
  - Blackjack  
  - Minas  
  - Dinosaurio  

- **Interfaz Gráfica Intuitiva**:  
  Diseñada con Java Swing, la interfaz ofrece una experiencia de usuario clara, accesible y adaptada a usuarios de cualquier nivel técnico.  

- **Sistema de Autenticación Seguro**:  
  Un robusto sistema de registro y login protege las cuentas de los usuarios y garantiza el acceso seguro a todas las funcionalidades del casino.  

- **Gestión Integral de Perfil**:  
  Funcionalidades avanzadas para que los usuarios gestionen su cuenta de manera completa, incluyendo:  
  - **Actualización de datos personales**: Mantén tu información siempre actualizada y precisa.  
  - **Opciones de fondos**: Realiza depósitos y retiros fácilmente.  
  - **Consulta del historial de movimientos**: Accede a un registro detallado y transparente de todas tus transacciones.  
  - **Cambio de contraseña**: Refuerza la seguridad de tu cuenta con la posibilidad de actualizar tu clave de acceso.  

Estas características garantizan una experiencia inmersiva, segura y eficiente para todos los usuarios del casino.







## Requisitos

- **Java Development Kit (JDK)**: jdk-21.0.4.7-hotspot o superior.

## Instalación y Ejecución

1. Clona este repositorio o descarga el código fuente del proyecto.
2. Asegúrate de tener instalado el JDK en la versión especificada (jdk-21.0.4.7-hotspot o superior).
3. Navega al directorio principal del proyecto y compila el archivo Java principal con el siguiente comando:
    ```
    javac src/main/Main.java
    ```
4. Luego, ejecuta la aplicación utilizando el siguiente comando:
    ```
    java src/main/Main
    ```

Con estos pasos, podrás ejecutar la aplicación y comenzar a interactuar con el sistema de juegos.





## Configuraciones del Proyecto

Este apartado describe las configuraciones disponibles en los archivos `conf/database.properties` y `conf/interface.properties` del proyecto, fundamentales para personalizar su comportamiento.

### Configuraciones de la Base de Datos (`conf/database.properties`)

El archivo `database.properties` gestiona las configuraciones relacionadas con la base de datos SQLite utilizada en el proyecto. Los parámetros configurables incluyen:

-   **`db.create`**: Controla si se debe crear una nueva base de datos desde cero.
    -   Valores: `true` (crea una nueva base de datos eliminando la existente) o `false`.
-   **`db.dir`**: Define la ubicación del archivo de la base de datos SQLite.
-   **`db.driver`**: Especifica el controlador JDBC para la conexión a la base de datos. En este caso: `org.sqlite.JDBC`.
-   **`db.url`**: Proporciona la URL de conexión, que utiliza la ruta especificada en `db.dir`.
-   **`db.reCreateTables`**: Indica si las tablas existentes deben ser eliminadas y creadas nuevamente.
    -   Valores: `true` (reinicia las tablas) o `false`.
-   **`db.loadFromCSV`**: Determina si los datos de las tablas deben cargarse desde archivos CSV predefinidos.
    -   Valores: `true` (carga desde CSV) o `false`.

#### Rutas de Archivos CSV:

-   **`db.dir.csv.balances`**: Archivo CSV con datos de balances.
-   **`db.dir.csv.transactions`**: Archivo CSV con el historial de transacciones.
-   **`db.dir.csv.passwords`**: Archivo CSV con información de usuarios y contraseñas.
-   **`db.dir.csv.userData`**: Archivo CSV con datos adicionales de los usuarios.

**Ejemplo de Configuración:**

```properties
# Configuración para crear una nueva base de datos y cargar datos desde archivos CSV

db.create=true
db.reCreateTables=false
db.loadFromCSV=true
```

### Configuraciones de la Interfaz de Usuario (`conf/interface.properties`)

El archivo `interface.properties` gestiona configuraciones relacionadas con la experiencia visual y el comportamiento de la interfaz gráfica. Los parámetros configurables incluyen:

-   **`ui.darkMode`**: Activa o desactiva el modo oscuro.
    -   Valores: `true` (activado) o `false` (desactivado).
-   **`ui.fullScreen`**: Define si la aplicación se ejecutará en modo pantalla completa.
    -   Valores: `true` (pantalla completa) o `false` (ventana).

**Ejemplo de Configuración:**

```properties
# Configuración para habilitar el modo oscuro
# y desactivar la pantalla completa

ui.darkMode=true
ui.fullScreen=false
```

Estas configuraciones permiten ajustar la base de datos y la interfaz gráfica según las necesidades específicas del usuario.

## Organización del Proyecto

El proyecto está estructurado de forma modular para facilitar el desarrollo, mantenimiento y escalabilidad. A continuación, se describen los directorios principales y su propósito:

-   **`doc/`**: Contiene documentación del proyecto, como manuales de usuario, especificaciones técnicas y otros documentos de referencia.
-   **`lib/`**: Almacena las dependencias externas y librerías utilizadas en el proyecto.
-   **`conf/`**: Incluye archivos de configuración necesarios para la ejecución y personalización de la aplicación.
-   **`log/`**: Directorio destinado a almacenar los archivos de logs generados por la aplicación durante su ejecución.
-   **`resources/`**: Contiene recursos estáticos utilizados por la aplicación, organizados en subdirectorios según su tipo o función:

    -   **`csv/`**: Archivos de datos en formato CSV.
    -   **`db/`**: Archivos relacionados con la base de datos, como scripts de inicialización.
    -   **`img/`**: Recursos gráficos, organizados en subcarpetas según el contexto de uso (ej. inicio de sesión, perfil, o los distintos juegos).

-   **`src/`**: Código fuente del proyecto dividido en paquetes que representan las capas y funcionalidades principales:

    -   **`db/`**: Gestión de la conexión y acceso a la base de datos.
    -   **`domain/`**: Contiene las clases del dominio que encapsulan la lógica de negocio para los distintos módulos, como blackjack, slots y perfil.
    -   **`gui/`**: Interfaz gráfica de usuario, con subcarpetas específicas para cada sección o juego.
    -   **`io/`**: Manejo de entrada y salida de datos, como archivos y procesamiento de información.
    -   **`main/`**: Punto de entrada principal de la aplicación y configuración inicial.

Este diseño modular permite una clara separación de responsabilidades y facilita la navegación y desarrollo colaborativo en el proyecto.

## Tecnologías Utilizadas

-   **Java**: Lenguaje de programación principal empleado para el desarrollo de la lógica de negocio y la estructura general del proyecto.
-   **Java Swing**: Biblioteca gráfica utilizada para construir la interfaz de usuario, ofreciendo una experiencia visual interactiva y amigable.
-   **Inteligencia Artificial (IA)**: Aplicada en diversas áreas del desarrollo, optimizando procesos y mejorando la experiencia del usuario.
-   **SQLite**: Base de datos ligera y embebida, ideal para manejar los datos de forma eficiente y simplificada dentro del proyecto.

## Autores

-   César Llata Remacha
-   David Trueba López
-   Aimar Arriaga Markaida

### Notas

Este proyecto es un desarrollo académico para la asignatura de Programación III. El uso de inteligencia artificial ayudó a agilizar el desarrollo del código y de la interfaz. Si bien está pensado para fines educativos, ofrece una base sólida para futuras mejoras y expansiones.

---
