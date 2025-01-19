  # 007 Casino

### Descripción
**007 Casino** es una aplicación de casino virtual desarrollada en Java, diseñada para brindar una experiencia inmersiva y variada en el ámbito de los juegos de azar. La plataforma ofrece seis juegos populares: carreras de caballos, ruleta, máquinas tragamonedas (slots), blackjack, un juego de minas y un juego de dinosaurios.

Los usuarios deben registrarse en la plataforma y autenticarse mediante un sistema seguro para disfrutar plenamente de las funciones de este casino virtual. Este proyecto combina entretenimiento con una sólida aplicación de conceptos de desarrollo en Java.


### Características


- **Base de datos confiable**:
     - Almacenamiento de datos de inicio de sesión para garantizar seguridad.
     - Opciones para depositar y retirar fondos.Registro de información personal y transacciones.
     - Gestión eficiente del saldo, historial de movimientos y preferencias de los usuarios.

 
- **Variedad de juegos:** Carreras de caballos, ruleta, slots, blackjack, minas y dinosaurios.


- **Interfaz gráfica intuitiva**: Desarrollada con Java Swing, garantiza una navegación clara y accesible para usuarios de cualquier nivel.


- **Sistema de autenticación seguro**: A través de un proceso de registro y login ,los usuarios pueden proteger sus cuentas y acceder a las funcionalidades completas del casino.


- **Gestión integral de perfil**: 
	Permite a los usuarios:
   - Actualización de datos personales para mantener información precisa.
   - Opciones para depositar y retirar fondos.
   - Consulta detallada del historial de movimientos, asegurando transparencia en las transacciones.
   - Posibilidad de cambiar la contraseña de acceso.
   

     

### Requisitos
- **Java Development Kit**: jdk-21.0.4.7-hotspot o superior.

### Instalación y Ejecución
1. Clona este repositorio o descarga el código fuente.
2. Asegúrate de tener el JDK en la versión especificada.
3. Navega al directorio del proyecto y ejecuta el archivo principal:
   ```bash
   javac src/Main.java
   java src/Main

   ```
## Inicializacion de la base de datos
1. Abrir el archivo resources/db/crearBD.txt.
2. Cambiar el txt de false a true.

### Estructura del Proyecto
- `src/Main.java`: Punto de entrada del programa.
- `src/` contiene las clases y paquetes del proyecto que gestionan los juegos, el gestor de la base de datos, la autenticación, el perfil del usuario y la interfaz gráfica.
- `conf/` encontramos las propiedades de la interfaz y de la base de datos
- `lib/` contiene la libreria para interactuar con la base de datos.
- `resources/` encontramos csv, la base de datos, y la carpetas con imagenes.


### Tecnologías Utilizadas
- **Java**: Lenguaje de programación principal.
- **Java Swing**: Biblioteca para la creación de la interfaz gráfica.
- **IA**: Utilizada para optimizar el proceso de desarrollo.

### Autores
- César Llata Remacha
- David Trueba López
- Aimar Arriaga Markaida

### Notas
Este proyecto es un desarrollo académico para la asignatura de Programación III. El uso de inteligencia artificial ayudó a agilizar el desarrollo del código y de la interfaz.Si bien está pensado para fines educativos, ofrece una base sólida para futuras mejoras y expansiones.

---
