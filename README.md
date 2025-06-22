# EzPark Platform - Backend

## 🚀 Despliegue en Render + Google Cloud SQL

### Configuración Multi-entorno:
- **Desarrollo Local**: MySQL (`application.properties`)
- **Producción (Render)**: PostgreSQL en Google Cloud SQL (`application-prod.properties`)
- **Docker**: Configurado para despliegue automático en Render

### Variables de entorno en Render:
```
SPRING_PROFILES_ACTIVE=prod
DB_HOST=34.61.141.252
DB_PORT=5432
DB_NAME=ezpark-db
DB_USERNAME=ezpark_user
DB_PASSWORD=[configurado-en-render]
```

## Configuración para Desarrollo Local

### 🚀 Inicio Rápido

1. **Clonar el repositorio:**
   ```bash
   git clone <repository-url>
   cd ezpark-platform-prueba
   ```

2. **Ejecutar la aplicación:**
   ```bash
   # Con Maven Wrapper (recomendado)
   ./mvnw spring-boot:run
   
   # O con Maven instalado
   mvn spring-boot:run
   ```

3. **Acceder a la aplicación:**
   - **API Base**: http://localhost:8080
   - **Health Check**: http://localhost:8080/actuator/health
   - **H2 Console**: http://localhost:8080/h2-console

### 🗄️ Base de Datos

**Desarrollo Local (H2 In-Memory):**
- URL: `jdbc:h2:mem:ezpark`
- Usuario: `sa`
- Contraseña: *(vacía)*
- Console: http://localhost:8080/h2-console

### 📁 Estructura del Proyecto

```
src/main/java/com/acme/ezpark/platform/
├── booking/          # Módulo de reservas
├── parking/          # Módulo de estacionamientos  
├── payment/          # Módulo de pagos
├── review/           # Módulo de reseñas
├── schedule/         # Módulo de horarios
├── user/             # Módulo de usuarios
├── vehicle/          # Módulo de vehículos
└── shared/           # Código compartido
```

### 🔧 Configuración

- **Puerto**: 8080
- **Perfil activo**: `dev`
- **Base de datos**: H2 (in-memory)
- **Log level**: DEBUG

### 📋 Endpoints Principales

- `GET /actuator/health` - Health check
- `GET /h2-console` - H2 database console

### 🛠️ Desarrollo

**Requisitos:**
- Java 21+
- Maven 3.6+

**Comandos útiles:**
```bash
# Compilar
./mvnw compile

# Ejecutar tests
./mvnw test

# Limpiar y compilar
./mvnw clean compile

# Ejecutar aplicación
./mvnw spring-boot:run
```
