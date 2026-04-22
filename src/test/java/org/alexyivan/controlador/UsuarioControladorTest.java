package org.alexyivan.controlador;

import org.alexyivan.exception.ValidacionException;
import org.alexyivan.modelo.dto.UsuarioDto;
import org.alexyivan.modelo.enums.EstadoCuentaEmun;
import org.alexyivan.modelo.form.UsuarioForm;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Tests para UsuarioControlador
 * Cubre acciones: Registrar usuario, Consultar perfil, Añadir saldo, Consultar saldo
 * Especificadas en steam.md bajo "Gestión de Usuarios"
 */
public class UsuarioControladorTest {

    private IUsuarioControlador controlador;
    private UsuarioForm formularioValido;
    private LocalDate fechaNacimientoValida;

    @Before
    public void setup() {
        controlador = new UsuarioControlador();
        fechaNacimientoValida = LocalDate.now().minusYears(20);

        formularioValido = new UsuarioForm(
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
    }

    /**
     * Acción: Registrar nuevo usuario
     * Debe crear un nuevo usuario con validaciones de formulario
     */
    @Test
    public void testRegistrarUsuarioValido() throws ValidacionException {
        Optional<UsuarioDto> resultado = controlador.registrarUsuario(formularioValido);
        assertTrue("Debe registrar usuario válido", resultado.isPresent());
        assertNotNull("Usuario registrado debe tener ID", resultado.get().getId());
        assertEquals("Nombre de usuario debe coincidir", "usuario123", resultado.get().getNombreUsuario());
    }

    /**
     * Acción: Registrar usuario
     * No debe registrar usuario con datos inválidos
     */
    @Test
    public void testRegistrarUsuarioInvalido() throws ValidacionException {
        UsuarioForm formularioIvalido = new UsuarioForm(
                "ab", // Nombre muy corto
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

        assertThrows("Debe lanzar excepción para usuario inválido",
                ValidacionException.class, () -> controlador.registrarUsuario(formularioIvalido));
    }

    /**
     * Acción: Consultar perfil
     * Debe retornar información completa del usuario por nombre de usuario
     */
    @Test
    public void testConsultarUsuarioPorNombreUsuario() throws ValidacionException {
        // Primero registra un usuario
        controlador.registrarUsuario(formularioValido);

        // Luego consulta por nombre de usuario
        Optional<UsuarioDto> resultado = controlador.consultarUsuarioNombreUsuario("usuario123");
        assertTrue("Debe encontrar usuario por nombre", resultado.isPresent());
        assertEquals("Nombre debe coincidir", "usuario123", resultado.get().getNombreUsuario());
        assertEquals("Email debe coincidir", "test@email.com", resultado.get().getEmail());
    }

    /**
     * Acción: Consultar perfil
     * Debe retornar información completa del usuario por ID
     */
    @Test
    public void testConsultarUsuarioPorId() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        Optional<UsuarioDto> resultado = controlador.consultarUsuarioId(registrado.get().getId());
        assertTrue("Debe encontrar usuario por ID", resultado.isPresent());
        assertEquals("ID debe coincidir", registrado.get().getId(), resultado.get().getId());
    }

    /**
     * Acción: Añadir saldo a cartera
     * Debe recargar dinero en la cartera con validaciones de cantidad
     * Rango: 5.00 a 500.00
     */
    @Test
    public void testAnadirSaldoValido() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        controlador.anhadirSaldo(registrado.get().getId(), 50.0f);

        Optional<UsuarioDto> usuario = controlador.consultarSaldo(registrado.get().getId());
        assertTrue("Debe consultar saldo", usuario.isPresent());
        assertEquals("Saldo debe ser 50.0", 50.0f, usuario.get().getSaldo(), 0.01f);
    }

    /**
     * Acción: Añadir saldo a cartera
     * No debe aceptar cantidad menor a 5.00
     */
    @Test
    public void testAnadirSaldoMenorAlMinimo() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        assertThrows("Debe lanzar excepción para cantidad menor a 5.00",
                ValidacionException.class, 
                () -> controlador.anhadirSaldo(registrado.get().getId(), 3.0f));
    }

    /**
     * Acción: Añadir saldo a cartera
     * No debe aceptar cantidad mayor a 500.00
     */
    @Test
    public void testAnadirSaldoMayorAlMaximo() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        assertThrows("Debe lanzar excepción para cantidad mayor a 500.00",
                ValidacionException.class, 
                () -> controlador.anhadirSaldo(registrado.get().getId(), 501.0f));
    }

    /**
     * Acción: Consultar saldo
     * Debe mostrar el saldo disponible en la cartera
     */
    @Test
    public void testConsultarSaldo() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        Optional<UsuarioDto> usuario = controlador.consultarSaldo(registrado.get().getId());
        assertTrue("Debe consultar saldo", usuario.isPresent());
        assertEquals("Saldo inicial debe ser 0", 0f, usuario.get().getSaldo(), 0.01f);
    }

    /**
     * Acción: Añadir múltiples recargas de saldo
     * Debe acumular correctamente en la cartera
     */
    @Test
    public void testMultiplesRecargas() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        controlador.anhadirSaldo(registrado.get().getId(), 50.0f);
        controlador.anhadirSaldo(registrado.get().getId(), 75.0f);

        Optional<UsuarioDto> usuario = controlador.consultarSaldo(registrado.get().getId());
        assertTrue("Debe consultar saldo", usuario.isPresent());
        assertEquals("Saldo debe ser 125.0", 125.0f, usuario.get().getSaldo(), 0.01f);
    }

    /**
     * Validación: Nombre de usuario único
     * No debe permitir registrar dos usuarios con el mismo nombre
     */
    @Test
    public void testNombreUsuarioUnico() throws ValidacionException {
        controlador.registrarUsuario(formularioValido);

        UsuarioForm formularioDuplicado = new UsuarioForm(
                "usuario123", // Mismo nombre
                "otro@email.com",
                "Password123",
                "Otro Usuario",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        assertThrows("No debe permitir nombres de usuario duplicados",
                ValidacionException.class, () -> controlador.registrarUsuario(formularioDuplicado));
    }

    /**
     * Validación: Email único
     * No debe permitir registrar dos usuarios con el mismo email
     */
    @Test
    public void testEmailUnico() throws ValidacionException {
        controlador.registrarUsuario(formularioValido);

        UsuarioForm formularioDuplicado = new UsuarioForm(
                "otro_usuario",
                "test@email.com", // Mismo email
                "Password123",
                "Otro Usuario",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        assertThrows("No debe permitir emails duplicados",
                ValidacionException.class, () -> controlador.registrarUsuario(formularioDuplicado));
    }

    /**
     * Validación: Edad mínima de 13 años
     * Debe validar que el usuario tenga al menos 13 años
     */
    @Test
    public void testEdadMinima13Anos() throws ValidacionException {
        LocalDate fechaMenor13 = LocalDate.now().minusYears(12);
        UsuarioForm formulario = new UsuarioForm(
                "usuario_nino",
                "nino@email.com",
                "Password123",
                "Niño Usuario",
                "España",
                fechaMenor13,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        assertThrows("Debe rechazar usuarios menores de 13 años",
                ValidacionException.class, () -> controlador.registrarUsuario(formulario));
    }

    /**
     * Validación: Estado de cuenta debe ser ACTIVA para nuevas compras
     * Usuario registrado debe tener estado ACTIVA por defecto
     */
    @Test
    public void testEstadoCuentaActivaPorDefecto() throws ValidacionException {
        Optional<UsuarioDto> registrado = controlador.registrarUsuario(formularioValido);
        assertTrue("Usuario debe registrarse", registrado.isPresent());

        Optional<UsuarioDto> usuario = controlador.consultarUsuarioId(registrado.get().getId());
        assertTrue("Debe consultar usuario", usuario.isPresent());
        assertEquals("Estado debe ser ACTIVA", EstadoCuentaEmun.ACTIVA, usuario.get().getEstado());
    }

    /**
     * Validación: Formato de email válido
     * No debe permitir emails con formato incorrecto
     */
    @Test
    public void testEmailFormatoInvalido() throws ValidacionException {
        UsuarioForm formulario = new UsuarioForm(
                "usuario123",
                "email_invalido", // Sin @
                "Password123",
                "Juan Pérez",
                "España",
                fechaNacimientoValida,
                LocalDate.now(),
                "avatar.png",
                0f,
                EstadoCuentaEmun.ACTIVA
        );

        assertThrows("Debe rechazar email con formato inválido",
                ValidacionException.class, () -> controlador.registrarUsuario(formulario));
    }
}
