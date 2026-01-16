# 🧪 GUÍA DE PRUEBAS - ElorES

## 📋 Pre-requisitos

### 1. ElorServ debe estar ejecutándose
```bash
cd C:\Users\in2dm3-v.ELORRIETA\eclipse-workspace\Reto2ElorServ
mvn exec:java -Dexec.mainClass="MainServer"
```

**Deberías ver:**
```
╔═══════════════════════════════════════════════════════════════╗
║                   ELORSERV - SERVIDOR TCP                    ║
╚═══════════════════════════════════════════════════════════════╝
✓ Conexión a base de datos exitosa
✓ Servidor escuchando en puerto: 9000
✓ Esperando conexiones de clientes ElorES...
```

---

## 🚀 PRUEBA 1: Test de Conexión (ConnectionTest)

Esta prueba verifica que la comunicación Socket funciona correctamente.

### Ejecutar:
```bash
cd C:\Users\in2dm3-v.ELORRIETA\eclipse-workspace\ElorES
mvn clean compile
mvn exec:java -Dexec.mainClass="test.ConnectionTest"
```

### Resultado esperado:
```
╔═══════════════════════════════════════════════════════════════╗
║                  ELORES - TEST DE CONEXIÓN                   ║
╚═══════════════════════════════════════════════════════════════╝

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TEST 1: Conectando con ElorServ...
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[INFO] Conectando con ElorServ en localhost:9000...
[INFO] ✓ Conexión establecida con ElorServ
✓ ÉXITO: Conexión establecida con ElorServ

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TEST 2: Solicitando clave pública RSA...
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[INFO] → Enviando: GET_PUBLIC_KEY
[INFO] ← Recibido: SUCCESS
✓ ÉXITO: Clave pública recibida
   Longitud de clave: 392 caracteres

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TEST 3: Enviando PING al servidor...
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[INFO] → Enviando: PING
[INFO] ← Recibido: SUCCESS
✓ ÉXITO: PONG recibido
   Latencia: 5 ms

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
TEST 4: Probando petición LOGIN (sin credenciales)...
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
[INFO] → Enviando: LOGIN
[INFO] ← Recibido: BAD_REQUEST
✓ ÉXITO: El servidor validó correctamente la petición
   Respuesta esperada: Usuario y contraseña requeridos

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
RESUMEN DE PRUEBAS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✓ Conexión Socket TCP: OK
✓ Comunicación Request/Response: OK
✓ Protocolo de mensajes: OK
✓ Servidor ElorServ respondiendo correctamente

🎉 TODAS LAS PRUEBAS DE CONEXIÓN COMPLETADAS
```

### ✅ Si ves este resultado:
**¡La comunicación entre ElorES y ElorServ funciona perfectamente!**

### ❌ Si hay error de conexión:
Verifica:
1. ElorServ está ejecutándose
2. Puerto 9000 no está ocupado por otra aplicación
3. Firewall no está bloqueando localhost:9000

---

## 🚀 PRUEBA 2: Aplicación Principal (App)

Esta ejecuta la aplicación completa (sin UI por ahora).

### Ejecutar:
```bash
mvn exec:java -Dexec.mainClass="app.App"
```

### Resultado esperado:
```
╔═══════════════════════════════════════════════════════════════╗
║                      ELORES - INICIO                         ║
║              Aplicación de Escritorio - Profesores           ║
╚═══════════════════════════════════════════════════════════════╝

[INFO] Iniciando aplicación ElorES...
[INFO] Conectando con ElorServ en localhost:9000...
[INFO] ✓ Conexión establecida con ElorServ
✓ Conexión establecida con ElorServ

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
APLICACIÓN LISTA
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Estructura de clases creada correctamente:
  ✓ Protocol (Request/Response/ActionType/StatusCode)
  ✓ DTOs (UserDTO/HorarioDTO/ReunionDTO)
  ✓ Client (SocketClient)
  ✓ Services (Auth/User/Horario/Reunion)
  ✓ Controllers (Login/Main/Horario/Reunion)
  ✓ Utils (Logger/RSAEncryption)

Presiona Ctrl+C para salir...
```

### Para salir:
Presiona `Ctrl+C`

---

## 📊 VERIFICACIÓN EN ElorServ

Cuando ElorES se conecta, en la consola de **ElorServ** deberías ver:

```
→ Nuevo cliente conectado [#1]: 127.0.0.1:XXXXX
[Cliente #1] Procesando: GET_PUBLIC_KEY
[Cliente #1] Enviando respuesta: SUCCESS
```

Esto confirma que **ambas aplicaciones se están comunicando correctamente**.

---

## 🎯 PRÓXIMOS PASOS

Una vez confirmado que la conexión funciona:

### 1. Implementar UI (Swing o JavaFX)
- Ventana de Login
- Ventana Principal
- Gestión de Horarios
- Gestión de Reuniones

### 2. Probar Casos de Uso Reales
Con credenciales reales de la base de datos:
- Login → Obtener clave pública → Cifrar password → Login
- Ver perfil del profesor
- Listar alumnos
- Ver horarios
- Crear/gestionar reuniones

---

## 📝 LOGS

Los logs se guardan en:
```
C:\Users\in2dm3-v.ELORRIETA\eclipse-workspace\ElorES\logs\elores.log
```

Puedes revisar ahí toda la comunicación detallada.

---

## 🐛 TROUBLESHOOTING

### Error: "No se pudo conectar con ElorServ"
**Solución**: Asegúrate de que ElorServ está ejecutándose antes de iniciar ElorES

### Error: "Connection refused"
**Solución**: Verifica que el puerto 9000 está libre y no hay firewall bloqueando

### Error: "ClassNotFoundException"
**Solución**: Ejecuta `mvn clean compile` antes de ejecutar

---

## ✅ CRITERIO DE ÉXITO

**La prueba es exitosa si:**
1. ✅ ConnectionTest muestra "🎉 TODAS LAS PRUEBAS DE CONEXIÓN COMPLETADAS"
2. ✅ App.java muestra "APLICACIÓN LISTA"
3. ✅ ElorServ muestra "Nuevo cliente conectado"
4. ✅ No hay excepciones en ninguna consola

**Si se cumplen estos 4 puntos:**
🎉 **¡LA ARQUITECTURA BASE DE ElorES FUNCIONA CORRECTAMENTE!**

---

**Estado**: LISTO PARA PROBAR 🚀
