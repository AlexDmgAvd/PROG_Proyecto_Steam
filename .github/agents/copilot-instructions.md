# PROG_Proyecto_Steam Workspace Instructions

## 🎮 Project Overview
**Steam Platform Simulator** (Java educational project)  
A modular, multi-layered Java application simulating Steam's core functionality: user management, game catalog, library, purchases, refunds, and reviews system with comprehensive validation rules.

---

## 🏗️ Architecture & Layer Overview

### **Layer Pattern (Top to Bottom)**
- **Controllers** (`controlador/`): Business logic, validation orchestration, object composition
- **Forms** (`modelo/form/`): Request validation rules, error collection 
- **DTOs** (`modelo/dto/`): Data transfer objects for external layer communication
- **Entities** (`modelo/entidad/`): Persistence models matching database schema
- **Repositories** (`repositorio/`): Data access abstraction with multiple implementations
- **Mappers** (`mapper/`): DTO ↔ Entity transformation layer
- **Exceptions** (`exception/`): `ValidacionException` with `List<ErrorDto>` structure

### **Key Design Patterns**
- **Repository Pattern** with interface-based abstraction (`IUsuarioRepo`, etc.)
- **Data Transfer Objects (DTO)** separating external and internal models  
- **Form Validation** pattern: Forms validate input, return `List<ErrorDto>`
- **Dependency Injection** via constructor (controllers receive repo implementations)
- **Strategy Pattern** for repository implementations (InMemory, Hibernate)

### **Dual Repository Implementations**
- **InMemory** (`repositorio/inmemory/`): Testing, prototyping, no persistence
- **Hibernate** (`repositorio/hibernate/`): Production persistence layer
- Both implement common interfaces: `IUsuarioRepo`, `IJuegoRepo`, `ICompraRepo`, `IBibliotecaRepo`, `IResenhaRepo`

---

## 📂 Key Files & Their Responsibilities

| Path | Purpose |
|------|---------|
| `controlador/IXXXControlador.java` | Service interface contract |
| `controlador/XXXControlador.java` | Business logic: validation, coordination, state changes |
| `modelo/form/XXXForm.java` | Input object with `validar()` method returning errors |
| `modelo/dto/XXXDto.java` | External data representation (API/UI layer) |
| `modelo/entidad/XXXEntidad.java` | Database/storage representation |
| `modelo/enums/` | Closed sets of values (EstadoCuentaEnum, PegiEnum, etc.) |
| `mapper/Mapper.java` | Static methods for Entity↔DTO mapping |
| `repositorio/interfaces/IXXX.java` | Repository contracts |
| `exception/ValidacionException.java` | Runtime exception wrapping `List<ErrorDto>` |

---

## 🎯 Core Domain Entities & Relationships

### **Usuario (User)**
- **Key Fields**: id, nombreUsuario (unique), email (unique), contraseña, nombreReal, pais, fechaNacimiento, avatar, saldo, estadoCuenta
- **States**: ACTIVA, SUSPENDIDA, BANEADA
- **Validations** (in `UsuarioForm`):
  - Username: alphanumeric with hyphens only, must be unique
  - Email: valid format, must be unique
  - Password: ≥8 chars, uppercase, lowercase, digit
  - Age: ≥13 years old
  - Balance: 0 to ∞, max 2 decimals
  - Add funds: 5.00-500.00 range

### **Juego (Game)**
- **Key Fields**: id, titulo (unique), descripcion, desarrollador, fechaLanzamiento, precioBase, descuentoActual (0-100%), categoria, pegi, idiomas, estado
- **States**: DISPONIBLE, PREVENTA, ACCESO_ANTICIPADO, NO_DISPONIBLE
- **Validations**:
  - Title: 1-100 chars, unique
  - Price: ≥0, max 2 decimals
  - Discount: 0-100 integer
  - PEGI: PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18

### **Compra (Purchase)**
- **Relations**: Usuario → many Compra; Juego → many Compra
- **States**: PENDIENTE, COMPLETADA, CANCELADA, REEMBOLSADA
- **Key Validations**:
  - User must be ACTIVA
  - Game must be available/preventa/early-access
  - User has sufficient balance
  - Payment method valid

### **Biblioteca (Library)**
- **Relations**: Usuario → many Biblioteca; Juego → many Biblioteca (N:M through this table)
- **Constraint**: User can own same game only once
- **Fields**: horasJugadasTotal, ultimaFechaDeJuego, estadoInstalacion, fechaAdquisicion

### **Reseña (Review)**
- **Constraint**: One review per user per game
- **Validations**:
  - User must own game (in Biblioteca)
  - Text: 50-8000 chars
  - Only published reviews visible (states: PUBLICADA, OCULTA, ELIMINADA)

---

## 🔄 Workflow Patterns

### **Adding/Creating an Entity**
1. User submits Form (e.g., `UsuarioForm`)
2. Controller calls `form.validar()` → collects `List<ErrorDto>`
3. If errors exist, throw `ValidacionException(errores)`
4. If valid, create Entity, persist via Repository
5. Mapper transforms Entity → DTO for response

### **Updating/Querying**
1. Repository retrieves entity (often returns `Optional`)
2. Controller applies business logic/validation
3. Mapper transforms to DTO
4. Return DTO (or throw exception on failure)

### **Search/Filter Pattern**
- `BusquedaJuegosForm`, `BusquedaCompraForm` encapsulate filter criteria
- Controllers handle sorting/filtering logic

---

## 📋 Development Patterns & Conventions

### **Naming Conventions**
- **Spanish identifiers**: controlador, repositorio, modelo, entidad (inherited from course)
- **Interfaces**: prefix `I` (e.g., `IUsuarioControlador`, `IUsuarioRepo`)
- **Enums**: suffix `Enum` (e.g., `EstadoCuentaEnum`, `MetodoPagoEnum`)
- **DTOs**: suffix `Dto` (e.g., `UsuarioDto`)
- **Entities**: suffix `Entidad` (e.g., `UsuarioEntidad`)
- **Forms**: suffix `Form` (e.g., `UsuarioForm`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `SALDO_MINIMO`, `CONST_CERO`)

### **Validation Best Practices**
- Form objects validate themselves via `validar()` method
- Return type: `List<ErrorDto>` with specific error types
- Controllers aggregate form errors + business logic errors before throwing exception
- Never throw exceptions mid-validation; collect all errors first

### **Error Handling**
- `ValidacionException` should be caught at API/UI boundary
- Extract `getErrores()` and return meaningful response to user
- Use `ErrorType` enum for standardized error categories

### **Controller Responsibilities**
- Receive validated requests (Forms or DTOs)
- Apply business rules and orchestrate repositories
- Combine field-level validations with domain validations
- Handle state transitions safely
- Map results to DTOs

### **Repository Usage**
- Always use interface injected via constructor
- Implementations handle InMemory vs Hibernate logic
- Return `Optional<T>` for single-item queries
- Return `List<T>` for multi-item queries

---

## 🧪 Testing

### **Test Structure**
- Mirror `src/main/java` structure in `src/test/java`
- Use JUnit 4.12 (configured in pom.xml)
- Test both layers: Forms (validation), Controllers (business logic), Repositories (persistence)

### **Test Naming Convention**
- Controller tests: `XXXControladorTest.java`
- Repository tests: `XXXRepoTest.java`
- Form tests: `XXXFormTest.java`

---

## 🛠️ Build & Execution

### **Build Commands**
```bash
# Compile (target/classes will be generated)
mvn clean compile

# Run tests
mvn test

# Package as JAR (optional, project has main() in App.java)
mvn package

# Full rebuild
mvn clean install
```

### **Java Configuration**
- **Compiler**: Java 24 (source and target)
- **Encoding**: UTF-8
- **Artifact**: `PROG_PROYECTO_STEAM-1.0-SNAPSHOT.jar`

---

## 💡 Common Development Tasks

### **Add a new field to an entity**
1. Add to `XXXEntidad` class
2. Add to corresponding `XXXDto` class
3. Update `Mapper.mapXXXEntidadADto()` method
4. Update `XXXForm` validation if needed
5. Update repository implementations (InMemory, Hibernate)

### **Add a new validation rule**
1. Add logic to `XXXForm.validar()` method
2. Return appropriate `ErrorDto` with `ErrorType` enum
3. Controller may add additional business-logic validations

### **Add a new query method**
1. Define in repository interface (`IXXXRepo.java`)
2. Implement in both `XXXRepoInMemory.java` and `XXXRepoHibernate.java`
3. Call from controller, handle `Optional` or empty result set

### **Create a new review or compile error investigation**
- Check `modelo/form/ErrorType.java` for existing error categories
- Validation errors should be specific and user-facing

---

## ⚠️ Common Pitfalls & Solutions

| Issue | Solution |
|-------|----------|
| `NullPointerException` in Mapper | Check if Entity fields are properly initialized before mapping |
| Validation errors not appearing | Ensure all error cases in Form.validar() add to List<ErrorDto> before returning |
| Repository query returns wrong data | Verify filter logic matches business requirements in controller |
| State transition fails unexpectedly | Review allowed state transitions in enum definitions |
| Duplicate entities created | Repo.obtenerPorNombre/Email checks must happen before persistence |
| ClassNotFoundException (Hibernate) | Ensure hibernate.xml configured with all Entity classes |

---

## 📚 Links to Documentation

- **README.md**: Feature list, entity descriptions, relationship diagram
- **App.java**: Entry point (currently empty, for future UI implementation)
- **Util.java**: Common utility functions for calculations/formatting

---

## 🎬 Quick Start for New Tasks

1. **Understand the requirement** in context of domain entities
2. **Find the Form** (`XXXForm.java`) for input validation
3. **Check the Controller** (`XXXControlador.java`) for business logic patterns
4. **Review existing mappers** (`Mapper.java`) for DTO/Entity patterns
5. **Verify validations** match repository constraints before implementing
6. Build with `mvn clean compile` and test with `mvn test`
