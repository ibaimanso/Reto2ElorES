# ARQUITECTURA DEL PROYECTO ElorES

## 📐 Visión General

**ElorES** es la aplicación de escritorio Java para profesores que forma parte del ecosistema **Framework Educativo Elorrieta**. Se comunica **exclusivamente por Sockets TCP** con el servidor **ElorServ**.

---

## 🏗️ Arquitectura en Capas

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE PRESENTACIÓN                      │
│                    (UI - Swing/JavaFX)                       │
│                      [PENDIENTE]                             │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                   CAPA DE CONTROLADORES                      │
│  LoginController │ MainController │ HorarioController │      │
│                  ReunionController                           │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                    CAPA DE SERVICIOS                         │
│  AuthService │ UserService │ HorarioService │ ReunionService │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                     CAPA DE RED (CLIENT)                     │
│                     SocketClient (Singleton)                 │
│               Comunicación TCP con ElorServ                  │
└─────────────────────────────────────────────────────────────┘
                              ↓
                    [ SOCKET TCP - Puerto 9000 ]
                              ↓
                         ┌──────────┐
                         │ ElorServ │
                         └──────────┘
```

---

## 📦 Estructura de Paquetes

### 1. **protocol/** - Protocolo de Comunicación
Clases compartidas conceptualmente con el servidor (mismo formato).

#### `ActionType.java` (enum)
Define todas las acciones disponibles:
- **Autenticación**: `LOGIN`, `LOGOUT`, `GET_PUBLIC_KEY`
- **Perfil**: `GET_PROFILE`, `UPDATE_PROFILE`
- **Alumnos**: `GET_ALUMNOS`, `GET_ALUMNO_BY_ID`, `FILTER_ALUMNOS_BY_CICLO`, `FILTER_ALUMNOS_BY_MODULO`
- **Horarios**: `GET_MY_HORARIO`, `GET_HORARIO_BY_USER_ID`, `GET_HORARIOS_PROFESORES`
- **Reuniones**: `CREATE_REUNION`, `GET_MY_REUNIONES`, `ACCEPT_REUNION`, `CANCEL_REUNION`, `DELETE_REUNION`
- **Utilidades**: `PING`, `DISCONNECT`

#### `StatusCode.java` (enum)
Códigos de estado HTTP-style:
- **2xx**: `SUCCESS`, `CREATED`, `ACCEPTED`
- **4xx**: `BAD_REQUEST`, `UNAUTHORIZED`, `FORBIDDEN`, `NOT_FOUND`, `CONFLICT`
- **5xx**: `INTERNAL_ERROR`, `SERVICE_UNAVAILABLE`

#### `Request.java`
Petición del cliente al servidor:
```java
{
    action: ActionType,
    sessionToken: String,
    data: Map<String, Object>,
    timestamp: String
}
```

#### `Response.java`
Respuesta del servidor al cliente:
```java
{
    status: StatusCode,
    message: String,
    data: Object,
    timestamp: String
}
```

---

### 2. **dto/** - Data Transfer Objects
Objetos de transferencia de datos (sin lógica de negocio).

#### `UserDTO.java`
- id, email, username, nombre, apellidos
- dni, direccion, telefono1, telefono2
- argazkiaUrl, tipoId, tipoNombre

#### `HorarioDTO.java`
- id, diaSemana, horaInicio, horaFin
- moduloNombre, profesorNombre, cicloNombre, aula

#### `ReunionDTO.java`
- id, titulo, descripcion, fechaHora, ubicacion
- estado, creadorId, creadorNombre
- destinatarioId, destinatarioNombre

---

### 3. **client/** - Capa de Red

#### `SocketClient.java` (Singleton)
**Responsabilidades**:
- Mantener una única conexión TCP con ElorServ
- Enviar peticiones `Request` serializadas
- Recibir respuestas `Response` serializadas
- Gestionar el `sessionToken` tras login exitoso
- Reconexión y manejo de errores

**Métodos principales**:
```java
+ connect(): boolean
+ disconnect(): void
+ sendRequest(Request): Response
+ sendRequest(ActionType, Map<String, Object>): Response
+ isConnected(): boolean
+ setSessionToken(String): void
+ getSessionToken(): String
```

**Configuración**:
- Host: `localhost`
- Puerto: `9000`
- Timeout: 5000ms

---

### 4. **service/** - Capa de Servicios

#### `AuthService.java`
**Responsabilidades**:
- Obtener clave pública RSA del servidor
- Cifrar contraseñas con RSA
- Realizar login/logout
- Mantener usuario actual en sesión

**Métodos**:
```java
+ fetchPublicKey(): boolean
+ login(username, password): boolean
+ logout(): boolean
+ getCurrentUser(): UserDTO
+ isLoggedIn(): boolean
```

#### `UserService.java`
**Responsabilidades**:
- Consultar perfil del usuario logueado
- Obtener lista de alumnos
- Filtrar alumnos por ciclo/módulo

**Métodos**:
```java
+ getMyProfile(): UserDTO
+ getAlumnos(): List<UserDTO>
+ getAlumnoById(Integer): UserDTO
+ filterAlumnosByCiclo(Integer): List<UserDTO>
```

#### `HorarioService.java`
**Responsabilidades**:
- Consultar horario propio
- Consultar horarios de otros profesores

**Métodos**:
```java
+ getMyHorario(): List<HorarioDTO>
+ getHorarioByUserId(Integer): List<HorarioDTO>
+ getHorariosProfesores(): List<HorarioDTO>
```

#### `ReunionService.java`
**Responsabilidades**:
- Crear nuevas reuniones
- Consultar reuniones propias
- Aceptar/cancelar reuniones

**Métodos**:
```java
+ createReunion(ReunionDTO): boolean
+ getMyReuniones(): List<ReunionDTO>
+ acceptReunion(Integer): boolean
+ cancelReunion(Integer): boolean
```

---

### 5. **controller/** - Controladores

#### `BaseController.java` (abstracto)
Controlador base con instancias de servicios compartidas.

#### `LoginController.java`
Gestiona la pantalla de login.

#### `MainController.java`
Gestiona la pantalla principal del profesor.

#### `HorarioController.java`
Gestiona la pantalla de consulta de horarios.

#### `ReunionController.java`
Gestiona la pantalla de gestión de reuniones.

---

### 6. **util/** - Utilidades

#### `AppLogger.java`
Logger centralizado con SLF4J + Logback.

**Métodos**:
```java
+ info(String): void
+ error(String): void
+ error(String, Exception): void
+ debug(String): void
+ warn(String): void
```

#### `RSAEncryptionUtil.java`
Utilidad para cifrado RSA de contraseñas.

**Métodos**:
```java
+ setPublicKey(String): void
+ encrypt(String): String
+ isPublicKeyConfigured(): boolean
```

---

### 7. **test/** - Pruebas

#### `ConnectionTest.java`
Clase de prueba que verifica:
1. ✅ Conexión Socket TCP con ElorServ
2. ✅ Obtención de clave pública RSA
3. ✅ Test de PING/PONG
4. ✅ Validación del protocolo Request/Response

---

## 🔐 Flujo de Seguridad (RSA)

```
┌────────┐                                  ┌──────────┐
│ ElorES │                                  │ ElorServ │
└────┬───┘                                  └─────┬────┘
     │                                            │
     │  1. GET_PUBLIC_KEY Request                │
     │───────────────────────────────────────────>│
     │                                            │
     │  2. Response(publicKey)                   │
     │<───────────────────────────────────────────│
     │                                            │
     │  3. Cifrar contraseña con publicKey       │
     │    (RSA/ECB/PKCS1Padding)                 │
     │                                            │
     │  4. LOGIN Request(username, encPassword)  │
     │───────────────────────────────────────────>│
     │                                            │
     │                     5. Descifrar password  │
     │                        con privateKey      │
     │                     6. Validar credenciales│
     │                                            │
     │  7. Response(sessionToken, userDTO)       │
     │<───────────────────────────────────────────│
     │                                            │
     │  8. Todas las peticiones incluyen         │
     │     sessionToken en el header             │
     │───────────────────────────────────────────>│
```

---

## 📡 Flujo de Comunicación Request/Response

### Ejemplo: Login

**1. Cliente prepara Request:**
```java
Request {
    action: LOGIN,
    sessionToken: null,
    data: {
        "username": "profesor@elorrieta.com",
        "password": "ABC123XYZ..." // Cifrado RSA Base64
    },
    timestamp: "2026-01-16T10:30:45"
}
```

**2. Cliente serializa y envía por ObjectOutputStream**

**3. Servidor deserializa, procesa y responde:**
```java
Response {
    status: SUCCESS (200),
    message: "Login exitoso",
    data: {
        "sessionToken": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
        "user": {
            "id": 5,
            "nombre": "Juan",
            "apellidos": "Pérez García",
            "tipoNombre": "PROFESOR"
        }
    },
    timestamp: "2026-01-16T10:30:45.123"
}
```

**4. Cliente deserializa y guarda sessionToken**

---

## 🚀 Casos de Uso por Sprint

### Sprint 1 (PSP - CU01 a CU04)

| CU | Caso de Uso | ActionType | Estado |
|----|-------------|------------|--------|
| CU01 | Login con cifrado RSA | `LOGIN`, `GET_PUBLIC_KEY` | ✅ |
| CU02 | Consultar perfil | `GET_PROFILE` | ✅ |
| CU03 | Consultar alumnos | `GET_ALUMNOS` | ✅ |
| CU04 | Consultar mi horario | `GET_MY_HORARIO` | ✅ |

### Sprint 2 (Funcionalidad completa)

| CU | Caso de Uso | ActionType | Estado |
|----|-------------|------------|--------|
| CU05 | Consultar otros horarios | `GET_HORARIO_BY_USER_ID` | ⏳ |
| CU06 | Crear reuniones | `CREATE_REUNION` | ⏳ |
| CU07 | Aceptar reuniones | `ACCEPT_REUNION` | ⏳ |
| CU08 | Cancelar reuniones | `CANCEL_REUNION` | ⏳ |

---

## 🎯 Estado Actual del Proyecto

### ✅ COMPLETADO
- Arquitectura en capas definida
- Protocolo de comunicación (Request/Response)
- Cliente Socket TCP (Singleton)
- Capa de servicios completa
- Controladores base
- Utilidades (Logger, RSA)
- DTOs completos
- Test de conexión

### ⏳ PENDIENTE
- Interfaz gráfica (Swing o JavaFX)
- Ventanas: Login, Main, Horarios, Reuniones
- Parsing de JSON a DTOs (Gson)
- Manejo completo de errores en UI
- Validaciones de formularios

---

## 🔄 Próximos Pasos

1. **Implementar UI con Swing**:
   - `LoginFrame.java`
   - `MainFrame.java`
   - `HorarioFrame.java`
   - `ReunionFrame.java`

2. **Conectar Controllers con UI**:
   - Eventos de botones
   - Actualización de tablas
   - Mostrar mensajes de error/éxito

3. **Probar con servidor real**:
   - Ejecutar `ConnectionTest`
   - Login con credenciales reales
   - Probar todos los casos de uso

---

## 📚 Referencias Técnicas

- **Java**: 17+
- **Maven**: Gestión de dependencias
- **Gson**: Serialización JSON
- **SLF4J + Logback**: Logging
- **Sockets TCP**: Comunicación cliente-servidor
- **RSA**: Cifrado de contraseñas

---

**Autor**: ElorES Team  
**Proyecto**: Reto 2 - Framework Educativo Elorrieta  
**Curso**: 2º DAM  
**Fecha**: Enero 2026
