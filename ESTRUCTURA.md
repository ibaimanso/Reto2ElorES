# 📂 ESTRUCTURA COMPLETA DEL PROYECTO ElorES

```
ElorES/
│
├── 📄 pom.xml                          # Maven: dependencias (Gson, SLF4J, Logback)
├── 📄 README.md                        # Documentación principal
├── 📄 ARQUITECTURA.md                  # Arquitectura detallada en capas
├── 📄 CHECKLIST.md                     # Lista de verificación del proyecto
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   │
│   │   │   ├── 📦 app/                         # Aplicación principal
│   │   │   │   └── App.java                    # Main que conecta con ElorServ
│   │   │   │
│   │   │   ├── 📦 protocol/                    # Protocolo de comunicación
│   │   │   │   ├── ActionType.java            # Enum con 23 acciones (LOGIN, GET_PROFILE, etc.)
│   │   │   │   ├── StatusCode.java            # Códigos HTTP-style (200, 401, 500, etc.)
│   │   │   │   ├── Request.java               # Petición cliente → servidor
│   │   │   │   └── Response.java              # Respuesta servidor → cliente
│   │   │   │
│   │   │   ├── 📦 dto/                         # Data Transfer Objects
│   │   │   │   ├── UserDTO.java               # Datos de usuario (id, nombre, email, etc.)
│   │   │   │   ├── HorarioDTO.java            # Datos de horario (día, hora, módulo, etc.)
│   │   │   │   └── ReunionDTO.java            # Datos de reunión (título, fecha, estado, etc.)
│   │   │   │
│   │   │   ├── 📦 client/                      # Capa de red
│   │   │   │   └── SocketClient.java          # Cliente Socket TCP (Singleton) - Puerto 9000
│   │   │   │
│   │   │   ├── 📦 service/                     # Capa de servicios (Lógica de negocio)
│   │   │   │   ├── AuthService.java           # Login/Logout + cifrado RSA
│   │   │   │   ├── UserService.java           # Perfil, alumnos, filtros
│   │   │   │   ├── HorarioService.java        # Consulta de horarios
│   │   │   │   └── ReunionService.java        # Crear, aceptar, cancelar reuniones
│   │   │   │
│   │   │   ├── 📦 controller/                  # Controladores (Intermediarios UI ↔ Service)
│   │   │   │   ├── BaseController.java        # Controlador base abstracto
│   │   │   │   ├── LoginController.java       # Gestión de login
│   │   │   │   ├── MainController.java        # Pantalla principal
│   │   │   │   ├── HorarioController.java     # Gestión de horarios
│   │   │   │   └── ReunionController.java     # Gestión de reuniones
│   │   │   │
│   │   │   ├── 📦 util/                        # Utilidades
│   │   │   │   ├── AppLogger.java             # Logger centralizado (SLF4J + Logback)
│   │   │   │   └── RSAEncryptionUtil.java     # Cifrado RSA de contraseñas
│   │   │   │
│   │   │   └── 📦 test/                        # Pruebas
│   │   │       └── ConnectionTest.java        # Test de conexión con ElorServ
│   │   │
│   │   └── resources/
│   │       ├── logback.xml                    # Configuración de logging
│   │       └── application.properties         # Configuración de la aplicación
│   │
│   └── test/
│       └── java/                              # Tests unitarios (futuro)
│
├── target/                                     # Compilados (generado por Maven)
│   └── classes/
│
└── logs/                                       # Logs de la aplicación (auto-generado)
    └── elores.log
```

---

## 📊 RESUMEN DE CLASES

### Total: **24 archivos Java**

| Paquete | Archivos | Descripción |
|---------|----------|-------------|
| app | 1 | Aplicación principal |
| protocol | 4 | Protocolo de comunicación (Request/Response) |
| dto | 3 | Objetos de transferencia de datos |
| client | 1 | Cliente Socket TCP |
| service | 4 | Servicios (Auth, User, Horario, Reunion) |
| controller | 5 | Controladores para UI |
| util | 2 | Utilidades (Logger, RSA) |
| test | 1 | Pruebas de conexión |

---

## 🔗 FLUJO DE DEPENDENCIAS

```
App.java
  ↓
SocketClient (Singleton)
  ↓
[Conexión TCP puerto 9000]
  ↓
ElorServ (Servidor)
```

```
LoginController
  ↓
AuthService
  ↓
SocketClient
  ↓
Request/Response
  ↓
ElorServ
```

---

## ✅ ESTADO: 100% ESTRUCTURA BASE CREADA

**Todas las clases compilan sin errores** ✅

**Listo para probar conexión con ElorServ** 🚀

---

## 🧪 PROBAR AHORA

### 1. Asegúrate de que ElorServ está ejecutándose:
```bash
cd C:\Users\in2dm3-v.ELORRIETA\eclipse-workspace\Reto2ElorServ
mvn exec:java -Dexec.mainClass="MainServer"
```

### 2. En otra terminal, ejecuta ElorES:
```bash
cd C:\Users\in2dm3-v.ELORRIETA\eclipse-workspace\ElorES
mvn clean compile
mvn exec:java -Dexec.mainClass="test.ConnectionTest"
```

Si ves:
```
🎉 TODAS LAS PRUEBAS DE CONEXIÓN COMPLETADAS
```

**¡LA COMUNICACIÓN FUNCIONA! ✅**
