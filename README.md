# VOLAREDB

Aplicación de consola para gestionar aerolíneas, clientes, vuelos y reservas. El proyecto está organizado como una aplicación Java de NetBeans dentro de `Reto Adt/`.

## Funcionalidades

1. Registrar aerolíneas en MySQL.
2. Registrar clientes en MySQL.
3. Crear vuelos y guardarlos en un fichero serializado.
4. Reservar vuelos para clientes existentes.
5. Consultar vuelos futuros.
6. Consultar las reservas de un cliente.
7. Consultar el historial de reservas con fecha de salida pasada.
8. Listar todos los vuelos guardados.

## Tecnologías y requisitos

- JDK 25, según `Reto Adt/nbproject/project.properties`.
- Apache Ant, incluido normalmente al abrir el proyecto en NetBeans.
- MySQL Server.
- MySQL Connector/J compatible con el servidor y el JDK.

## Estructura

```text
Reto Adt/
  src/
    Controlador/  Acceso a MySQL y consultas de vuelos
    Modelo/       Aerolínea, cliente, vuelo, reserva y clase
    Util/         Entrada, validaciones y escritura serializada
    exception/    Excepciones de dominio
    reto/adt/     Aplicación de consola (reto.adt.Main)
  nbproject/      Configuración del proyecto NetBeans
  build.xml       Tareas Ant
volaredb.sql      Esquema y datos iniciales de MySQL
```

## Preparación de MySQL

1. Inicia el servidor MySQL.
2. Ejecuta `volaredb.sql` una vez para crear `volaredb`, sus tablas, algunos datos de prueba y las rutas de imagen de ejemplo.
3. Si ya existen los clientes de ejemplo, ejecuta solo estas sentencias para actualizar sus imágenes:

```sql
UPDATE cliente SET ruta = 'src/Imagenes/descarga.jpg' WHERE id_C = 1;
UPDATE cliente SET ruta = 'src/Imagenes/descarga (1).jpg' WHERE id_C = 2;
```

El script completo incluye `CREATE DATABASE`, `CREATE TABLE` e inserciones con IDs fijos; no está pensado para ejecutarse varias veces sobre la misma base de datos sin limpiarla o adaptar los datos.

La conexión JDBC se configura en `Reto Adt/src/Controlador/DaoImplementacion.java`, en las constantes `URL`, `USER` y `PASS`. Ajusta esos valores a tu instalación local y no guardes credenciales reales en control de versiones.

## Configurar el driver JDBC

NetBeans necesita el JAR de MySQL Connector/J en el classpath de compilación y ejecución. La configuración actual de `Reto Adt/nbproject/project.properties` contiene rutas absolutas de varios ordenadores; si ninguna existe en tu equipo, añade el JAR desde las propiedades del proyecto o actualiza `javac.classpath` con su ruta local.

## Abrir, compilar y ejecutar

Abre la carpeta `Reto Adt/` como proyecto Ant en NetBeans. Comprueba que el JDK 25 está seleccionado y que el Connector/J está añadido; después usa **Clean and Build Project** y **Run Project**. La clase principal configurada es `reto.adt.Main`.

También se puede ejecutar Ant desde una terminal situada en `Reto Adt/`, si Ant y el JDK están configurados en el `PATH`:

```powershell
ant clean jar
ant run
```

El `pom.xml` situado en la raíz del repositorio es una configuración Maven separada y declara otra clase principal (`com.mycompany.reto0adt.Reto0Adt`). Para ejecutar el código descrito aquí, utiliza el proyecto Ant de `Reto Adt/`.

## Datos y rutas

- Clientes y aerolíneas se almacenan en MySQL.
- Los vuelos y sus IDs reservados se guardan en `vuelos.dat`, relativo al directorio de trabajo desde el que se ejecuta la aplicación. Conserva ese fichero si quieres mantener los vuelos y las reservas.
- Las imágenes de ejemplo están en `Reto Adt/src/Imagenes/`. La columna `cliente.ruta` debe apuntar a un archivo de imagen existente; el SQL incluye rutas para dos clientes de prueba.
- La clase `Vuelo` mantiene un `serialVersionUID` compatible con vuelos serializados por una versión anterior del proyecto.

## Paquetes principales

- `reto.adt.Main`: menú y coordinación de las operaciones.
- `Controlador.Dao` y `Controlador.DaoImplementacion`: contratos y acceso a MySQL, más consultas del fichero de vuelos.
- `Modelo`: entidades usadas por la aplicación.
- `Util`: lectura por consola, validaciones y utilidades de serialización.

Las reservas se guardan dentro de cada vuelo serializado, en `idReservados`; no se almacenan en una tabla independiente de MySQL.
