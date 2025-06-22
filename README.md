# EzPark Platform - Backend

## Despliegue en Render

Este proyecto está configurado para desplegarse automáticamente en Render usando Google Cloud SQL como base de datos.

### Configuración previa requerida:

1. **Google Cloud SQL**: Instancia PostgreSQL configurada con:
   - IP: `34.61.141.252`
   - Base de datos: `ezpark_platform`
   - Usuario: `ezpark_user`
   - Puerto: `5432`

2. **Variables de entorno en Render** (ya configuradas en `render.yaml`):
   - `SPRING_PROFILES_ACTIVE=gcp`
   - `PORT=10000`
   - `DATABASE_URL=jdbc:postgresql://34.61.141.252:5432/ezpark_platform`
   - `DB_USERNAME=ezpark_user`
   - `DB_PASSWORD=)oa]mVy@%rd6\$[*`

### Endpoints disponibles:

- **Health Check**: `/api/health` - Verificación de estado de la aplicación
- **Actuator Health**: `/actuator/health` - Health check de Spring Boot
- **API Documentation**: Los endpoints REST están organizados por módulos

### Proceso de despliegue:

1. Hacer push de los cambios al repositorio de Git
2. Conectar el repositorio a Render
3. Render automáticamente:
   - Detectará el entorno Java
   - Ejecutará `mvn clean package -DskipTests`
   - Iniciará la aplicación con `java -jar target/ezpark-platform-*.jar`
   - Verificará el estado con `/api/health`

### Estructura del proyecto:

- **booking/**: Módulo de reservas
- **parking/**: Módulo de estacionamientos
- **payment/**: Módulo de pagos
- **review/**: Módulo de reseñas
- **schedule/**: Módulo de horarios
- **user/**: Módulo de usuarios
- **vehicle/**: Módulo de vehículos
- **shared/**: Código compartido e infraestructura

### Archivos de configuración importantes:

- `render.yaml`: Configuración de despliegue en Render
- `application-gcp.properties`: Configuración para Google Cloud Platform
- `system.properties`: Especifica Java 21
- `.buildpacks`: Fuerza la detección de Java en Render
- `.java-version`: Versión de Java para compatibilidad

### Notas técnicas:

- Java 21 con Spring Boot 3.x
- PostgreSQL como base de datos
- Sin Docker (deployment nativo en Render)
- Health checks configurados
- Logging optimizado para producción
