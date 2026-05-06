# Gestor de Plataforma de Videojuegos Steam

La empresa Valve Corporation gestiona actualmente su plataforma Steam, que cuenta con millones de usuarios y miles de juegos en su catálogo. Para mejorar la experiencia de usuario y la gestión interna, se requiere diseñar un sistema completo que permita la gestión de usuarios, videojuegos, compras, bibliotecas, logros y reseñas.

Como proyecto educativo, el alumnado del IES de Teis creará una aplicación en Java que simule las funcionalidades principales de Steam.

En los siguientes puntos se recogen los datos necesarios y especificaciones técnicas que el proyecto ha de cumplir, así como las diferentes iteraciones por las que esta pasará.


## Entidades necesarias

### Usuario
El usuario representa a cada persona registrada en la plataforma Steam. Cada usuario tendrá los siguientes datos asociados:
- Id único
- Nombre de usuario (único)
- Email
- Contraseña
- Nombre real
- País
- Fecha de nacimiento
- Fecha de registro
- Avatar - Sera un string con el nomre del archivo de la imagen
- Saldo de la cartera Steam
- Estado de cuenta: {ACTIVA, SUSPENDIDA, BANEADA}

### Juego
El juego representa cada videojuego disponible en la plataforma Steam. Cada juego tendrá los siguientes datos asociados:
- Id único
- Título
- Descripción
- Desarrollador
- Fecha de lanzamiento
- Precio base
- Descuento actual (%)
- Categoría (Acción, Aventura, RPG, etc.)
- Clasificación por edad: {PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18}
- Idiomas disponibles []
- Estado: {DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE}

### Biblioteca
La biblioteca representa la colección de juegos que posee cada usuario. Cada entrada de biblioteca tendrá los siguientes datos asociados:
- Id único
- Usuario (referencia a Usuario)
- Juego (referencia a Juego)
- Fecha de adquisición
- Tiempo de juego total (horas)
- Última fecha de juego
- Estado de instalación: {INSTALADO, NO_INSTALADO}

### Compra
La compra representa cada transacción realizada por un usuario para adquirir un juego. Cada compra tendrá los siguientes datos asociados:
- Id único
- Usuario (referencia a Usuario)
- Juego (referencia a Juego)
- Fecha de compra
- Método de pago: {TARJETA_CREDITO, PAYPAL, CARTERA_STEAM, TRANSFERENCIA, OTROS}
- Precio sin descuento
- Descuento aplicado (%)
- Estado: {, COMPLETADA, REEMBOLSADA}

### Reseña
La reseña representa la valoración y opinión que un usuario hace de un juego. Cada reseña tendrá los siguientes datos asociados:
- Id único
- Usuario (referencia a Usuario)
- Juego (referencia a Juego)
- Recomendado (booleano)
- Texto de la reseña
- Horas jugadas al momento de la reseña
- Fecha de publicación
- Fecha de última edición
- Estado: {PUBLICADA, OCULTA, ELIMINADA}

## Restricciones de validación para formularios

### Restricciones de Usuario

#### Campos obligatorios
- Nombre de usuario
- Email
- Contraseña
- Nombre real
- País
- Fecha de nacimiento

#### Validaciones específicas
- **Nombre de usuario**:
  - Obligatorio
  - Único en el sistema
  - Longitud: entre 3 y 20 caracteres
  - Solo alfanuméricos, guiones y guiones bajos
  - No puede empezar con número
  
- **Email**:
  - Obligatorio
  - Único en el sistema
  - Formato válido de email
  
- **Contraseña**:
  - Obligatorio
  - Longitud mínima: 8 caracteres
  - Debe contener al menos una mayúscula, una minúscula y un número
  
- **Nombre real**:
  - Obligatorio
  - Longitud: entre 2 y 50 caracteres
  
- **País**:
  - Obligatorio
  - Debe ser un país válido de una lista predefinida
  
- **Fecha de nacimiento**:
  - Obligatoria
  - El usuario debe tener al menos 13 años
  - No puede ser fecha futura
  
- **Fecha de registro**:
  - Generada automáticamente (fecha actual)
  - No puede ser modificada por el usuario
  
- **Avatar**:
  - Opcional
  - Si se proporciona, longitud máxima: 100 caracteres
- **Saldo de la cartera Steam**:
  - Valor por defecto: 0.00
  - Debe ser positivo o cero
  - Máximo 2 decimales
  
- **Estado de cuenta**:
  - Valor por defecto: ACTIVA
  - Debe ser uno de: {ACTIVA, SUSPENDIDA, BANEADA}

### Restricciones de Juego

#### Campos obligatorios
- Título
- Desarrollador
- Fecha de lanzamiento
- Precio base
- Clasificación por edad

#### Validaciones específicas
- **Título**:
  - Obligatorio
  - Longitud: entre 1 y 100 caracteres
  - Debe ser único en el sistema
  
- **Descripción**:
  - Opcional
  - Longitud máxima: 2000 caracteres
  
- **Desarrollador**:
  - Obligatorio
  - Longitud: entre 2 y 100 caracteres
  
- **Fecha de lanzamiento**:
  - Obligatoria
  - Puede ser fecha futura (para preventas)
  
- **Precio base**:
  - Obligatorio
  - Debe ser positivo o cero (juegos gratuitos)
  - Máximo 2 decimales
  - Rango: 0.00 a 999.99
  
- **Descuento actual**:
  - Opcional
  - Rango: 0 a 100
  - Solo números enteros
  - Valor por defecto: 0
  
- **Clasificación por edad**:
  - Obligatoria
  - Debe ser uno de: {PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18}
  
- **Idiomas disponibles**:
  - Opcional
  - Al menos un idioma si se proporciona
  - Longitud máxima: 200 caracteres
  
- **Estado**:
  - Valor por defecto: DISPONIBLE
  - Debe ser uno de: {DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE}

### Restricciones de Biblioteca

#### Campos obligatorios
- Usuario (referencia)
- Juego (referencia)
- Fecha de adquisición

#### Validaciones específicas
- **Usuario**:
  - Obligatorio
  - Debe existir en el sistema
  
- **Juego**:
  - Obligatorio
  - Debe existir en el sistema
  - Un usuario no puede tener el mismo juego dos veces en su biblioteca
  
- **Fecha de adquisición**:
  - Obligatoria
  - No puede ser fecha futura
  - No puede ser anterior a la fecha de registro del usuario
  
- **Tiempo de juego total**:
  - Valor por defecto: 0.0
  - Debe ser positivo o cero
  - Máximo 1 decimal
  
- **Última fecha de juego**:
  - Opcional
  - No puede ser fecha futura
  - No puede ser anterior a la fecha de adquisición
  
- **Estado de instalación**:
  - Valor por defecto: NO_INSTALADO
  - Debe ser uno de: {INSTALADO, NO_INSTALADO}

### Restricciones de Compra

#### Campos obligatorios
- Usuario (referencia)
- Juego (referencia)
- Fecha de compra
- Método de pago
- Precio sin descuento
- Precio final

#### Validaciones específicas
- **Usuario**:
  - Obligatorio
  - Debe existir en el sistema
  - La cuenta debe estar ACTIVA
  
- **Juego**:
  - Obligatorio
  - Debe existir en el sistema
  - El juego debe estar en estado DISPONIBLE, PREVENTA o ACCESO_ANTICIPADO
  
- **Fecha de compra**:
  - Generada automáticamente (fecha y hora actual)
  - No puede ser modificada por el usuario
  
- **Método de pago**:
  - Obligatorio
  - Debe ser uno de: {TARJETA_CREDITO, PAYPAL, CARTERA_STEAM, TRANSFERENCIA, OTROS}
  
- **Precio sin descuento**:
  - Obligatorio
  - Debe ser positivo
  - Máximo 2 decimales
  
- **Descuento aplicado**:
  - Opcional
  - Rango: 0 a 100
  - Solo números enteros
  - Valor por defecto: 0
  
- **Estado**:
  - Valor por defecto: PENDIENTE
  - Debe ser uno de: {PENDIENTE, COMPLETADA, CANCELADA, REEMBOLSADA}

### Restricciones de Reseña

#### Campos obligatorios
- Usuario (referencia)
- Juego (referencia)
- Recomendado
- Texto de la reseña

#### Validaciones específicas
- **Usuario**:
  - Obligatorio
  - Debe existir en el sistema
  - El usuario debe tener el juego en su biblioteca
  
- **Juego**:
  - Obligatorio
  - Debe existir en el sistema
  - El usuario solo puede tener una reseña por juego
  
- **Recomendado**:
  - Obligatorio
  - Tipo booleano (true/false)
  
- **Texto de la reseña**:
  - Obligatorio
  - Longitud: entre 50 y 8000 caracteres
  
- **Horas jugadas al momento de la reseña**:
  - Generada automáticamente desde la biblioteca
  - Debe ser positivo o cero
  - Máximo 1 decimal
  
- **Fecha de publicación**:
  - Generada automáticamente (fecha y hora actual)
  - No puede ser modificada por el usuario
  
- **Fecha de última edición**:
  - Opcional
  - Actualizada automáticamente al editar
  - Debe ser posterior a la fecha de publicación
  
- **Estado**:
  - Valor por defecto: PUBLICADA
  - Debe ser uno de: {PUBLICADA, OCULTA, ELIMINADA}


## Relaciones entre entidades

### Usuario ↔ Biblioteca (1:1)
- Un usuario puede tiene una biblioteca
- Cada entrada de biblioteca pertenece a **un único usuario**
- Tipo: Uno a muchos

### Juego ↔ Biblioteca (N:M)
- Un juego puede estar en **múltiples bibliotecas** (de diferentes usuarios)
- Una biblioteca puede contener **múltiples juegos**
- Tipo: Muchos a muchos

### Usuario ↔ Compra (1:N)
- Un usuario puede realizar **múltiples compras**
- Cada compra pertenece a **un único usuario**
- Tipo: Uno a muchos

### Juego ↔ Compra (1:N)
- Un juego puede ser adquirido en **múltiples compras** (por diferentes usuarios o por el mismo usuario)
- Cada compra referencia a **un único juego**
- Tipo: Uno a muchos

### Usuario ↔ Reseña (1:N)
- Un usuario puede escribir **múltiples reseñas** (de diferentes juegos)
- Cada reseña pertenece a **un único usuario**
- Tipo: Uno a muchos

### Juego ↔ Reseña (1:N)
- Un juego puede tener **múltiples reseñas** (de diferentes usuarios)
- Cada reseña referencia a **un único juego**
- Tipo: Uno a muchos


### Relaciones indirectas

#### Usuario ↔ Juego (N:M a través de Biblioteca)
- Los usuarios y juegos tienen una relación muchos a muchos mediada por la entidad Biblioteca
- Un usuario puede tener muchos juegos en su biblioteca
- Un juego puede estar en las bibliotecas de muchos usuarios

## Acciones necesarias

### Gestión de Usuarios

#### Registrar nuevo usuario
- **Descripción**: Crear una nueva cuenta de usuario en la plataforma
- **Entrada**: Datos del formulario de registro (nombre de usuario, email, contraseña, nombre real, país, fecha de nacimiento)
- **Validaciones**: Aplicar todas las restricciones definidas en la sección de validación de Usuario
- **Objeto devuelto**: UsuarioDTO

#### Consultar perfil
- **Descripción**: Mostrar la información de un usuario específico
- **Entrada**: ID o nombre del usuario a consultar
- **Objeto devuelto**: UsuarioDTO

#### Añadir saldo a cartera
- **Descripción**: Recargar dinero en la cartera virtual de Steam del usuario
- **Entrada**: ID del usuario, cantidad a añadir
- **Validaciones**: Cantidad > 0, cuenta activa, rango entre 5.00 y 500.00
- **Objeto devuelto**: UsuarioDTO

#### Consultar saldo
- **Descripción**: Mostrar el saldo disponible en la cartera Steam de un usuario
- **Entrada**: ID del usuario
- **Validaciones**: Usuario debe existir en el sistema
- **Objeto devuelto**: UsuarioDTO

### Gestión de Juegos

#### Añadir juego al catálogo
- **Descripción**: Registrar un nuevo videojuego en el catálogo de Steam
- **Entrada**: Datos del formulario del juego (título, descripción, desarrollador, fecha de lanzamiento, precio base, categorías, etc.)
- **Validaciones**: Aplicar todas las restricciones definidas en la sección de validación de Juego
- **Objeto devuelto**: JuegoDTO

#### Buscar juegos
- **Descripción**: Filtrar y buscar juegos en el catálogo según múltiples criterios
- **Entrada**: Criterios de búsqueda (texto, categoría, rango de precio, clasificación, estado)
- **Objeto devuelto**: List\<JuegoDTO\>

#### Consultar catálogo completo
- **Descripción**: Listar todos los juegos disponibles en la plataforma
- **Entrada**: orden (opcional: alfabético, precio, fecha)
- **Objeto devuelto**: List\<JuegoDTO\>

#### Consultar detalles de juego
- **Descripción**: Mostrar toda la información completa de un juego específico
- **Entrada**: ID del juego
- **Objeto devuelto**: JuegoDTO

#### Aplicar descuento
- **Descripción**: Establecer un porcentaje de descuento temporal a un juego
- **Entrada**: ID del juego, porcentaje de descuento (0-100)
- **Validaciones**: Juego existe, descuento en rango válido
- **Objeto devuelto**: JuegoDTO

#### Cambiar estado del juego
- **Descripción**: Modificar el estado de disponibilidad de un juego
- **Entrada**: ID del juego, nuevo estado
- **Objeto devuelto**: JuegoDTO

### Gestión de Biblioteca

#### Ver biblioteca personal
- **Descripción**: Listar todos los juegos que posee un usuario en su biblioteca
- **Entrada**: ID del usuario, orden opcional (alfabético, tiempo de juego, última sesión, fecha de adquisición)
- **Objeto devuelto**: List\<BibliotecaDTO\>

#### Añadir juego a biblioteca
- **Descripción**: Agregar un juego adquirido a la biblioteca del usuario
- **Entrada**: ID del usuario, ID del juego
- **Validaciones**: Usuario existe, juego existe, no duplicado, compra verificada
- **Objeto devuelto**: BibliotecaDTO

#### Eliminar juego de biblioteca
- **Descripción**: Quitar un juego de la biblioteca del usuario
- **Entrada**: ID del usuario, ID del juego
- **Validaciones**: Entrada existe en la biblioteca
- **Objeto devuelto**: BibliotecaDTO

#### Actualizar tiempo de juego
- **Descripción**: Registrar y actualizar las horas jugadas de un juego
- **Entrada**: ID del usuario, ID del juego, horas a añadir
- **Validaciones**: Biblioteca existe, horas positivas
- **Objeto devuelto**: BibliotecaDTO

#### Consultar última sesión
- **Descripción**: Ver la última vez que se jugó a un juego específico
- **Entrada**: ID del usuario, ID del juego
- **Objeto devuelto**: BibliotecaDTO

#### Filtrar biblioteca (Ficheros)
- **Descripción**: Buscar juegos en la biblioteca personal según criterios
- **Entrada**: ID del usuario, filtros (estado de instalación, texto de búsqueda)
- **Objeto devuelto**: ArchivoInfoDTO

#### Ver estadísticas de biblioteca
- **Descripción**: Mostrar métricas generales de la biblioteca del usuario
- **Entrada**: ID del usuario
- **Estadísticas**: Total juegos, horas totales, juegos instalados, juego más jugado, valor total, juegos nunca jugados
- **Objeto devuelto**: EstadisticaBibliotecaDTO

### Gestión de Compras

#### Realizar compra
- **Descripción**: Crear una nueva transacción para adquirir un juego
- **Entrada**: ID del usuario, ID del juego, método de pago
- **Validaciones**: Usuario activo, juego comprable, no duplicado, saldo suficiente si usa cartera
- **Objeto devuelto**: CompraDTO

#### Procesar pago
- **Descripción**: Completar la transacción con el método de pago seleccionado
- **Entrada**: ID de compra, datos de pago según el método
- **Validaciones**: Compra existe, estado válido para procesar, pago válido
- **Objeto devuelto**: CompraDTO

#### Consultar historial de compras (Ficheros)
- **Descripción**: Ver todas las compras realizadas por un usuario
- **Entrada**: ID del usuario, filtro de estado opcional, rango de fechas opcional
- **Datos mostrados**: Fecha, juego, precio sin descuento, descuento aplicado, método de pago, estado
- **Objeto devuelto**: ArchivoInfoDTO

#### Consultar detalles de compra
- **Descripción**: Ver información completa de una transacción específica
- **Entrada**: ID de compra, ID del usuario (para verificar pertenencia)
- **Objeto devuelto**: CompraDTO

#### Solicitar reembolso
- **Descripción**: Devolver una compra y reintegrar el dinero a la cartera
- **Entrada**: ID de compra, motivo del reembolso
- **Validaciones**: Compra completada, dentro del plazo, pocas horas jugadas
- **Objeto devuelto**: CompraDTO

#### Generar factura (Ficheros)
- **Descripción**: Crear un comprobante de compra en formato imprimible
- **Entrada**: ID de compra
- **Validaciones**: Compra completada
- **Objeto devuelto**: ArchivoInfoDTO

### Gestión de Reseñas

#### Escribir reseña
- **Descripción**: Crear una nueva reseña para un juego que el usuario posee
- **Entrada**: ID del usuario, ID del juego, recomendado (boolean), texto de reseña
- **Validaciones**: Usuario propietario del juego, no duplicada, texto válido
- **Objeto devuelto**: ReseniaDTO

#### Eliminar reseña
- **Descripción**: Cambiar el estado de una reseña a eliminada
- **Entrada**: ID de reseña, ID del usuario (para verificar pertenencia)
- **Validaciones**: Reseña existe, pertenece al usuario
- **Objeto devuelto**: ReseniaDTO

#### Ver reseñas de un juego
- **Descripción**: Listar todas las reseñas publicadas de un juego específico
- **Entrada**: ID del juego, filtro opcional (positivas/negativas), orden (recientes/útiles) (Crea tu un algoritmo para determinar la utilidad de una reseña)
- **Objeto devuelto**: List\<ReseniaDTO\>

#### Ocultar reseña
- **Descripción**: Cambiar la visibilidad de una reseña a oculta
- **Entrada**: ID de reseña, ID del usuario
- **Validaciones**: Reseña existe, pertenece al usuario, está publicada
- **Objeto devuelto**: ReseniaDTO

#### Consultar estadísticas de reseñas (Ficheros)
- **Descripción**: Calcular y mostrar métricas de las reseñas de un juego
- **Entrada**: ID del juego
- **Estadísticas**: Total reseñas, % positivas, % negativas, promedio horas, tendencia reciente
- **Objeto devuelto**: ArchivoInfoDTO

#### Ver reseñas de un usuario
- **Descripción**: Listar todas las reseñas escritas por un usuario específico
- **Entrada**: ID del usuario, filtro de estado opcional
- **Objeto devuelto**: List\<ReseniaDTO\>

### Funcionalidades Generales del Sistema (Ficheros)

#### Generar reportes de ventas (Ficheros)
- **Descripción**: Crear estadísticas sobre las ventas de juegos en la plataforma
- **Entrada**: Rango de fechas, filtro por juego/desarrollador opcional
- **Métricas**: Total ventas, ingresos, top juegos, tendencias, comparativa periodos
- **Objeto devuelto**: ArchivoInfoDTO

#### Generar reportes de usuarios (Ficheros)
- **Descripción**: Crear estadísticas sobre la actividad y crecimiento de usuarios
- **Entrada**: Rango de fechas
- **Métricas**: Nuevos usuarios, usuarios activos, retención, distribución geográfica, actividad media
- **Objeto devuelto**: ArchivoInfoDTO

#### Consultar juegos más populares (Ficheros)
- **Descripción**: Listar los juegos con mejor rendimiento según diferentes criterios
- **Entrada**: Criterio de popularidad (tiempo de juego, mejores reseñas, más vendidos), límite de resultados
- **Datos mostrados**: Posición, título, desarrollador, métrica principal, métricas secundarias
- **Objeto devuelto**: ArchivoInfoDTO
