# ElorES - Aplicación de Escritorio para Profesores

## 📋 Descripción
Cliente de escritorio Java que se conecta al servidor **ElorServ** mediante **Sockets TCP**.

## 🏗️ Arquitectura

```
ElorES/
├── app/                    # Aplicación principal
│   └── App.java           # Main de la aplicación
│
├── protocol/              # Protocolo de comunicación (compartido con servidor)
│   ├── ActionType.java   # Tipos de acciones
│   ├── StatusCode.java   # Códigos de estado HTTP-style
│   ├── Request.java      # Petición al servidor
│   └── Response.java     # Respuesta del servidor
│
├── dto/                   # Data Transfer Objects
│   ├── UserDTO.java      # Datos de usuario
│   ├── HorarioDTO.java   # Datos de horario
│   └── ReunionDTO.java   # Datos de reunión
│
├── client/               # Capa de red
│   └── SocketClient.java # Cliente Socket TCP (Singleton)
│
├── service/              # Capa de servicios
│   ├── AuthService.java      # Autenticación
│   ├── UserService.java      # Gestión de usuarios
│   ├── HorarioService.java   # Gestión de horarios
│   └── ReunionService.java   # Gestión de reuniones
│
├── controller/           # Controladores para UI
│   ├── BaseController.java
│   ├── LoginController.java
│   ├── MainController.java
│   ├── HorarioController.java
│   └── ReunionController.java
│
├── util/                 # Utilidades
│   ├── AppLogger.java        # Logger centralizado
│   └── RSAEncryptionUtil.java # Cifrado RSA
│
└── test/                 # Pruebas
    └── ConnectionTest.java   # Test de conexión con servidor
```

## 🚀 Casos de Uso Implementados

### Sprint 1 (PSP - CU01 a CU04)
- ✅ **CU01**: Login con cifrado RSA
- ✅ **CU02**: Consultar perfil
- ✅ **CU03**: Consultar alumnos
- ✅ **CU04**: Consultar mi horario

### Sprint 2 (Funcionalidad completa)
- ⏳ **CU05**: Consultar horarios de otros profesores
- ⏳ **CU06**: Crear reuniones
- ⏳ **CU07**: Aceptar reuniones
- ⏳ **CU08**: Cancelar reuniones

## 🔧 Requisitos

- **Java 17** o superior
- **Maven** para gestión de dependencias
- **ElorServ** ejecutándose en `localhost:9000`

## 📦 Dependencias

- **Gson** 2.10.1 - Serialización JSON
- **SLF4J** 2.0.9 - API de logging
- **Logback** 1.4.11 - Implementación de logging

## ⚙️ Configuración

La configuración se encuentra en `src/main/resources/application.properties`:

```properties
server.host=localhost
server.port=9000
```

## 🧪 Probar Conexión

Ejecutar la clase de test:

```bash
mvn exec:java -Dexec.mainClass="test.ConnectionTest"
```

Esta clase realiza:
1. ✅ Conexión Socket TCP
2. ✅ Obtención de clave pública RSA
3. ✅ Test de PING/PONG
4. ✅ Validación del protocolo

## 🎯 Ejecutar Aplicación

```bash
mvn exec:java -Dexec.mainClass="app.App"
```

## 🔐 Seguridad

- Las contraseñas se cifran con **RSA** usando la clave pública del servidor
- Nunca viajan en texto plano por la red
- Gestión de sesiones mediante **sessionToken**

## 📝 Próximos Pasos

1. Implementar interfaz gráfica (Swing o JavaFX)
2. Crear ventanas para:
   - Login
   - Panel principal
   - Gestión de horarios
   - Gestión de reuniones
3. Conectar controllers con la UI

## 🤝 Integración con ElorServ

Este cliente **DEBE** conectarse al servidor **ElorServ** que:
- Escucha en puerto **9000**
- Usa protocolo **Request/Response** serializado
- Gestiona autenticación con **RSA**
- Almacena datos en MySQL vía **Hibernate**

## 📄 Licencia

Proyecto académico - Reto 2 DAM 2º - Elorrieta
