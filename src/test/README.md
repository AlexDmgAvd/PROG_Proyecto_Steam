# Test Suite - PROG_Proyecto_Steam

Suite completa de tests unitarios para validar la implementación del proyecto Steam conforme a las restricciones y acciones especificadas en `steam.md`.

## Estructura de Tests

### 1. Tests de Formularios (`src/test/java/org/alexyivan/form/`)

Validan las restricciones de cada entidad mediante el método `validar()` de cada formulario.

#### UsuarioFormTest.java (23 tests)
- **Validación de Nombre de Usuario**: obligatorio, longitud (3-20), alfanuméricos + guiones, no empieza con número
- **Validación de Email**: obligatorio, formato válido, único
- **Validación de Contraseña**: obligatoria, mínimo 8 caracteres, mayúsculas, minúsculas, números
- **Validación de Nombre Real**: obligatorio, longitud (2-50)
- **Validación de Fecha de Nacimiento**: obligatoria, mínimo 13 años, no futura
- **Validación de Avatar**: longitud máxima 100 caracteres
- **Validación de Saldo**: no negativo
- **Validación de Estado**: ACTIVA, SUSPENDIDA, BANEADA
- **Test de registro válido sin errores**

#### JuegoFormTest.java (21 tests)
- **Validación de Título**: obligatorio, longitud (1-100), único
- **Validación de Descripción**: máximo 2000 caracteres
- **Validación de Desarrollador**: obligatorio, longitud (2-100)
- **Validación de Fecha de Publicación**: obligatoria
- **Validación de Precio Base**: obligatorio, positivo, máximo 999.99, máximo 2 decimales
- **Validación de Descuento**: rango 0-100
- **Validación de PEGI**: PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18
- **Validación de Idiomas**: máximo 200 caracteres
- **Validación de Estado**: DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE
- **Tests especiales**: juegos gratuitos, preventas
- **Test de registro válido sin errores**

#### CompraFormTest.java (17 tests)
- **Validación de Usuario ID**: obligatorio
- **Validación de Juego ID**: obligatorio
- **Validación de Fecha de Compra**: no futura
- **Validación de Método de Pago**: obligatorio, todos los tipos válidos
- **Validación de Precio Sin Descuento**: positivo, máximo 2 decimales
- **Validación de Descuento**: rango 0-100
- **Validación de Precio Final**: cálculo correcto
- **Validación de Estado**: PENDIENTE, COMPLETADA, CANCELADA, REEMBOLSADA
- **Tests de diferentes métodos de pago**: tarjeta, PayPal, cartera, transferencia
- **Tests con descuentos**: sin descuento, con descuento, descuentos grandes
- **Test de registro válido sin errores**

#### ResenhaFormTest.java (18 tests)
- **Validación de Usuario ID**: obligatorio
- **Validación de Juego ID**: obligatorio
- **Validación de Recomendado**: obligatorio, booleano
- **Validación de Texto**: obligatorio, longitud (50-8000)
- **Validación de Horas Jugadas**: no negativo
- **Validación de Fecha de Publicación**: no futura
- **Validación de Fecha de Última Edición**: posterior a publicación
- **Validación de Estado**: PUBLICADA, OCULTA, ELIMINADA
- **Tests especiales**: reseñas recomendadas, negativas, editadas, con muchas horas
- **Tests en límites**: mínimo (50), máximo (8000)
- **Test de registro válido sin errores**

#### BibliotecaFormTest.java (17 tests)
- **Validación de Usuario ID**: obligatorio
- **Validación de Juego ID**: obligatorio
- **Validación de Fecha de Adquisición**: obligatoria, no futura
- **Validación de Tiempo de Juego Total**: no negativo, máximo 1 decimal
- **Validación de Última Fecha de Juego**: no futura, no anterior a adquisición
- **Validación de Estado Instalación**: INSTALADO, NO_INSTALADO
- **Tests especiales**: juego recién adquirido, sin jugar, con muchas horas
- **Test de registro válido sin errores**

### 2. Tests de Controladores (`src/test/java/org/alexyivan/controlador/`)

Validan las acciones del sistema según las especificaciones de steam.md.

#### UsuarioControladorTest.java (12 tests)
**Acción: Registrar nuevo usuario**
- Registrar usuario válido
- Rechazar usuario inválido
- Nombres únicos (no duplicados)
- Emails únicos (no duplicados)

**Acción: Consultar perfil**
- Por nombre de usuario
- Por ID

**Acción: Añadir saldo a cartera**
- Cantidad válida (5.00-500.00)
- Rechazar menor a 5.00
- Rechazar mayor a 500.00
- Múltiples recargas

**Acción: Consultar saldo**
- Mostrar saldo disponible

**Validaciones**
- Edad mínima 13 años
- Email con formato válido
- Estado ACTIVA por defecto

#### JuegoControladorTest.java (20 tests)
**Acción: Añadir juego al catálogo**
- Crear juego válido
- Rechazar juego inválido

**Acción: Buscar juegos**
- Por texto (título)
- Por categoría

**Acción: Consultar catálogo completo**
- Listar todos los juegos
- Ordenar alfabéticamente
- Ordenar por precio

**Acción: Consultar detalles de juego**
- Mostrar información completa

**Acción: Aplicar descuento**
- Establecer descuento válido
- Rechazar descuento inválido

**Acción: Cambiar estado del juego**
- Todos los estados válidos

**Validaciones**
- Título único
- Precio máximo (999.99)
- Juegos gratuitos (precio 0)
- Preventas con fecha futura
- Todos los PEGI ratings

#### BibliotecaControladorTest.java (17 tests)
**Acción: Ver biblioteca personal**
- Listar juegos del usuario
- Filtrar por estado instalación

**Acción: Añadir juego a biblioteca**
- Agregar juego válido
- Cambiar estado instalación

**Acción: Eliminar juego de biblioteca**
- Quitar juego

**Acción: Actualizar tiempo de juego**
- Incrementar horas jugadas
- Rechazar horas negativas

**Acción: Consultar última sesión**
- Ver última fecha de juego

**Acción: Ver estadísticas de biblioteca**
- Métricas generales

**Validaciones**
- Usuario debe existir
- Juego debe existir
- No duplicados en biblioteca
- Múltiples juegos por usuario

#### CompraControladorTest.java (22 tests)
**Acción: Realizar compra**
- Crear transacción válida
- Rechazar usuario inexistente
- Rechazar juego inexistente

**Acción: Procesar pago**
- Completar transacción

**Acción: Consultar detalles de compra**
- Mostrar información completa
- Solo usuario puede ver sus compras

**Acción: Solicitar reembolso**
- Devolver compra

**Validaciones**
- No permitir compra duplicada
- No permitir sin saldo en cartera
- Juego debe ser comprable
- Usuario cuenta debe estar ACTIVA
- Todos los métodos de pago
- Cálculo correcto de descuentos
- Estado de compra (PENDIENTE, COMPLETADA)
- Fecha automática

#### ResenhaControladorTest.java (26 tests)
**Acción: Escribir reseña**
- Crear reseña válida
- Rechazar si juego no está en biblioteca

**Acción: Eliminar reseña**
- Cambiar a estado ELIMINADA

**Acción: Ver reseñas de un juego**
- Listar reseñas publicadas
- Filtrar positivas/negativas
- Ordenar por recientes/útiles

**Acción: Ocultar reseña**
- Cambiar a estado OCULTA

**Acción: Consultar estadísticas de reseñas**
- Calcular métricas

**Acción: Ver reseñas de un usuario**
- Listar reseñas del usuario

**Validaciones**
- No permitir duplicada (user + juego)
- Usuario solo puede ver sus propias reseñas
- Texto entre 50-8000 caracteres
- Solo reseñas PUBLICADAS visibles
- Visibilidad correcta después de eliminar
- Porcentaje de positivas/negativas
- Límites exactos (50 mín, 8000 máx)

## Ejecutar los Tests

### Maven
```bash
mvn test
```

### Maven - Tests específicos
```bash
mvn test -Dtest=UsuarioFormTest
mvn test -Dtest=UsuarioControladorTest
mvn test -Dtest=JuegoFormTest
```

### Desde IDE (NetBeans, IntelliJ, Eclipse)
- Click derecho sobre carpeta `src/test/java` → Run Tests
- O sobre un archivo de test específico

## Cobertura de Requisitos

### Restricciones de Validación
- ✅ Usuario: 23 tests
- ✅ Juego: 21 tests
- ✅ Compra: 17 tests
- ✅ Reseña: 18 tests
- ✅ Biblioteca: 17 tests

### Acciones Necesarias
- ✅ Gestión de Usuarios: 12 tests
- ✅ Gestión de Juegos: 20 tests
- ✅ Gestión de Biblioteca: 17 tests
- ✅ Gestión de Compras: 22 tests
- ✅ Gestión de Reseñas: 26 tests

### Total: 193 Tests

## Notas Importantes

1. **Los tests pueden fallar inicialmente** - Son diseñados para validar si el código cumple con las especificaciones
2. **Usa los tests como guía de implementación** - Si un test falla, revisa la restricción correspondiente en steam.md
3. **Algunos tests pueden estar comentados** - Si ciertas validaciones aún no están implementadas
4. **Los DTOs no son completos** - Algunos tests crearán objetos con las interfaces necesarias

## Formato de Reportes

Los tests generan reportes en:
- `target/surefire-reports/` (MAVEN)

## Referencias
- Ver steam.md para especificaciones completas
- Cada test tiene un comentario describiendo qué valida
- Los nombres de los tests describen el caso de uso
