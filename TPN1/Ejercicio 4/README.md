# Ejercicio 4 — Gestión comercial MVC

Proyecto Spring Boot MVC basado en el DER suministrado. Incluye Web MVC, JPA, Validation, Thymeleaf y MySQL Driver. La configuración predeterminada usa MySQL de XAMPP.

## Ejecutar

```powershell
cd 'TPN1/Ejercicio 4'
mvn spring-boot:run
```

Abrir `http://localhost:8080`. Las rutas ABM disponibles son `/productos`, `/clientes`, `/administradores` y `/departamentos`.

## MySQL con XAMPP y DBeaver

Iniciar **MySQL** desde el panel de XAMPP. La aplicación crea automáticamente la base `ejercicio4`. Si asignaste contraseña al usuario `root`, actualizar `spring.datasource.password` en `application.properties` y ejecutar:

```powershell
mvn -s maven-settings.xml spring-boot:run
```

Las eliminaciones se realizan mediante flags o el estado `ELIMINADO`; no se usa `deleteById`.
