📋 Resumen del Proyecto: Gestor de Plataforma de Videojuegos Steam

Este proyecto consiste en el desarrollo de una aplicación en Java que simula las funcionalidades principales de la plataforma Steam, gestionando usuarios, videojuegos, compras, bibliotecas, reseñas y logros. El sistema está diseñado para ser modular, escalable y seguir buenas prácticas de validación y gestión de datos.

🧩 Entidades Principales

👤 Usuario

Datos: ID único, nombre de usuario, email, contraseña, nombre real, país, fecha de nacimiento, fecha de registro, avatar, saldo, estado de cuenta.
Estados de cuenta: ACTIVA, SUSPENDIDA, BANEADA.

🎮 Juego

Datos: ID único, título, descripción, desarrollador, fecha de lanzamiento, precio base, descuento actual, categoría, clasificación por edad, idiomas, estado.

Estados: DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE.

📚 Biblioteca

Datos: ID único, usuario, juego, fecha de adquisición, tiempo de juego total, última fecha de juego, estado de instalación.
Estados de instalación: INSTALADO, NO_INSTALADO.

🛒 Compra

Datos: ID único, usuario, juego, fecha de compra, método de pago, precio sin descuento, descuento aplicado, estado.
Estados: PENDIENTE, COMPLETADA, CANCELADA, REEMBOLSADA.

📝 Reseña

Datos: ID único, usuario, juego, recomendado, texto, horas jugadas, fecha de publicación, fecha de última edición, estado.
Estados: PUBLICADA, OCULTA, ELIMINADA.

✅ Validaciones y Restricciones Clave

Usuario:
Nombre de usuario único, alfanumérico con guiones.
Email único y válido.
Contraseña segura (mínimo 8 caracteres, mayúscula, minúscula, número).
Edad mínima: 13 años.
Saldo positivo o cero, máximo 2 decimales.

Juego:
Título único, entre 1 y 100 caracteres.
Precio base positivo o cero, máximo 2 decimales.
Descuento entre 0 y 100 (entero).
Clasificación por edad: PEGI_3 a PEGI_18.

Biblioteca:
Un usuario no puede tener el mismo juego dos veces.
Fecha de adquisición no futura, posterior a fecha de registro.

Compra:
Usuario debe estar activo.
Juego debe estar disponible, en preventa o acceso anticipado.
Método de pago válido.

Reseña:
Usuario debe poseer el juego en su biblioteca.
Solo una reseña por juego por usuario.
Texto entre 50 y 8000 caracteres.

🔗 Relaciones entre Entidades
Usuario ↔ Biblioteca → 1:N
Juego ↔ Biblioteca → N:M
Usuario ↔ Compra → 1:N
Juego ↔ Compra → 1:N
Usuario ↔ Reseña → 1:N
Juego ↔ Reseña → 1:N

🚀 Funcionalidades Principales

👥 Gestión de Usuarios
Registrar nuevo usuario.
Consultar perfil y saldo.
Añadir saldo a cartera (entre 5.00 y 500.00).

🎮 Gestión de Juegos
Añadir juego al catálogo.
Buscar y filtrar juegos.
Aplicar descuentos y cambiar estado.

📚 Gestión de Biblioteca
Ver biblioteca personal con filtros.
Añadir y eliminar juegos.
Actualizar tiempo de juego y consultar últimas sesiones.
Ver estadísticas de biblioteca.

🛒 Gestión de Compras
Realizar compra con validación de saldo.
Procesar pago y consultar historial.
Solicitar reembolsos bajo condiciones.
Generar facturas.

📝 Gestión de Reseñas
Escribir, editar, eliminar y ocultar reseñas.
Ver reseñas por juego o por usuario.
Consultar estadísticas de reseñas.

📊 Funcionalidades Generales (Ficheros)
Generar reportes de ventas y usuarios.
Consultar juegos más populares por distintos criterios.

📁 Estructura del Proyecto (Sugerida)
src/
├── entities/       # Clases de entidad (Usuario, Juego, Biblioteca, etc.)
├── validators/     # Lógica de validación
├── managers/       # Gestores de cada módulo (UsuarioManager, JuegoManager, etc.)
├── utils/          # Utilidades comunes (fechas, archivos, etc.)
└── main/           # Clase principal y menús

🛠️ Tecnologías y Enfoque
Lenguaje: Java (versión 11 o superior).
Validaciones: manuales, sin frameworks externos obligatorios.
Persistencia: ficheros de texto (simulación de base de datos).

Estructura: modular, orientada a objetos, con separación de responsabilidades.

🎯 Objetivo del Proyecto
Desarrollar una aplicación educativa que simule un sistema real de gestión de plataforma de videojuegos, aplicando conceptos de POO, validación, manejo de archivos y gestión de estados, siguiendo un modelo de entidades bien definido y relaciones claras.
