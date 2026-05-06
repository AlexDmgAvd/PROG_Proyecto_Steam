package org.alexyivan.form;

import org.alexyivan.modelo.enums.EstadoCuentaEmun;
import org.alexyivan.modelo.form.ErrorDto;
import org.alexyivan.modelo.form.UsuarioForm;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests para validación de restricciones del formulario UsuarioForm
 * Cubre todos los casos de validación de Usuario especificados en steam.md
 */
public class UsuarioFormTest {

    private UsuarioForm formularioValido;
    private LocalDate fechaNacimientoValida;

    @Before
    public void setup() {
        // Fecha de nacimiento válida (usuario con más de 13 años)
        fechaNacimientoValida = LocalDate.now().minusYears(20);
    }

    /**
     * Test 1: Nombre de usuario - Obligatorio
     */
    @Test
    public void testNombreUsuarioObligatorio() {
        UsuarioForm formulario = new UsuarioForm(
                null, // nombreUsuario
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de nombre de usuario requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreUsuario")));
    }

    /**
     * Test 2: Nombre de usuario - Longitud mínima (3 caracteres)
     */
    @Test
    public void testNombreUsuarioLongitudMinima() {
        UsuarioForm formulario = new UsuarioForm(
                "ab", // Menos de 3 caracteres
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud inválida para nombre de usuario", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreUsuario")));
    }

    /**
     * Test 3: Nombre de usuario - Longitud máxima (20 caracteres)
     */
    @Test
    public void testNombreUsuarioLongitudMaxima() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario_muy_largo_12345", // Más de 20 caracteres
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud inválida para nombre de usuario", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreUsuario")));
    }

    /**
     * Test 4: Nombre de usuario - Solo alfanuméricos, guiones y guiones bajos
     */
    @Test
    public void testNombreUsuarioCaracteresValidos() {
        UsuarioForm formulario = new UsuarioForm(
                "user@#$%", // Caracteres inválidos
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de formato inválido para nombre de usuario", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreUsuario")));
    }

    /**
     * Test 5: Nombre de usuario - No puede empezar con número
     */
    @Test
    public void testNombreUsuarioNoEmpiezaConNumero() {
        UsuarioForm formulario = new UsuarioForm(
                "1usuario", // Empieza con número
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error para nombre de usuario que empieza con número", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreUsuario")));
    }

    /**
     * Test 6: Email - Obligatorio
     */
    @Test
    public void testEmailObligatorio() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                null, // email
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de email requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("email")));
    }

    /**
     * Test 7: Email - Formato válido
     */
    @Test
    public void testEmailFormatoValido() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "email_invalido", // Email sin @
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de formato email inválido", 
                errores.stream().anyMatch(e -> e.campo().equals("email")));
    }

    /**
     * Test 8: Contraseña - Obligatoria
     */
    @Test
    public void testContrasenaObligatoria() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                null, // contraseña
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de contraseña requerida", 
                errores.stream().anyMatch(e -> e.campo().equals("contrasena")));
    }

    /**
     * Test 9: Contraseña - Longitud mínima (8 caracteres)
     */
    @Test
    public void testContrasenaLongitudMinima() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Pass123", // 7 caracteres
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud mínima de contraseña", 
                errores.stream().anyMatch(e -> e.campo().equals("contrasena")));
    }

    /**
     * Test 10: Contraseña - Debe contener mayúsculas
     */
    @Test
    public void testContrasenaConMayusculas() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "password123", // Sin mayúsculas
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de contraseña sin mayúsculas", 
                errores.stream().anyMatch(e -> e.campo().equals("contrasena")));
    }

    /**
     * Test 11: Contraseña - Debe contener minúsculas
     */
    @Test
    public void testContrasenaConMinusculas() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "PASSWORD123", // Sin minúsculas
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de contraseña sin minúsculas", 
                errores.stream().anyMatch(e -> e.campo().equals("contrasena")));
    }

    /**
     * Test 12: Contraseña - Debe contener números
     */
    @Test
    public void testContrasenaConNumeros() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "PasswordABC", // Sin números
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de contraseña sin números", 
                errores.stream().anyMatch(e -> e.campo().equals("contrasena")));
    }

    /**
     * Test 13: Nombre real - Obligatorio
     */
    @Test
    public void testNombreRealObligatorio() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                null, // nombreReal
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de nombre real requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreReal")));
    }

    /**
     * Test 14: Nombre real - Longitud mínima (2 caracteres)
     */
    @Test
    public void testNombreRealLongitudMinima() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "A", // 1 carácter
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud mínima de nombre real", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreReal")));
    }

    /**
     * Test 15: Nombre real - Longitud máxima (50 caracteres)
     */
    @Test
    public void testNombreRealLongitudMaxima() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez García Martínez López de la vega San", // Más de 50
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de nombre real", 
                errores.stream().anyMatch(e -> e.campo().equals("nombreReal")));
    }

    /**
     * Test 16: País - Obligatorio
     */
    @Test
    public void testPaisObligatorio() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                null, // país
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de país requerido", 
                errores.stream().anyMatch(e -> e.campo().equals("pais")));
    }

    /**
     * Test 17: Fecha de nacimiento - Obligatoria
     */
    @Test
    public void testFechaNacimientoObligatoria() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                null, // fechaNacimiento
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha de nacimiento requerida", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaNacimiento")));
    }

    /**
     * Test 18: Fecha de nacimiento - Usuario debe tener al menos 13 años
     */
    @Test
    public void testEdadMinimaRequerida() {
        LocalDate fechaMenor13 = LocalDate.now().minusYears(12);
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaMenor13,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de edad mínima", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaNacimiento")));
    }

    /**
     * Test 19: Fecha de nacimiento - No puede ser fecha futura
     */
    @Test
    public void testFechaNacimientoNoFutura() {
        LocalDate fechaFutura = LocalDate.now().plusYears(1);
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaFutura,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de fecha futura", 
                errores.stream().anyMatch(e -> e.campo().equals("fechaNacimiento")));
    }

    /**
     * Test 20: Avatar - Opcional pero con longitud máxima (100 caracteres)
     */
    @Test
    public void testAvatarLongitudMaxima() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "a".repeat(101), // Más de 100 caracteres
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de longitud máxima de avatar", 
                errores.stream().anyMatch(e -> e.campo().equals("avatar")));
    }

    /**
     * Test 21: Saldo - Debe ser positivo o cero
     */
    @Test
    public void testSaldoNegativo() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                -10f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("Debe contener error de saldo negativo", 
                errores.stream().anyMatch(e -> e.campo().equals("saldo")));
    }

    /**
     * Test 22: Estado de cuenta - Debe ser válido
     */
    @Test
    public void testEstadoCuentaValido() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertFalse("No debe contener error de estado de cuenta para estado válido", 
                errores.stream().anyMatch(e -> e.campo().equals("estado")));
    }

    /**
     * Test 23: Registro válido - Sin errores
     */
    @Test
    public void testRegistroValido() {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "test@email.com",
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        List<ErrorDto> errores = formulario.validar();
        assertTrue("El registro válido no debe contener errores", errores.isEmpty());
    }
}
