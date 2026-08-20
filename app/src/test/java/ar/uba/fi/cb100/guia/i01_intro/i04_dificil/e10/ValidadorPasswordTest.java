package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e10;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/** Tests del ejercicio 10 - Validador de contraseña. */
class ValidadorPasswordTest {

    @Test
    @DisplayName("Contraseña valida no lanza excepcion")
    void passwordValida() {
        assertDoesNotThrow(() -> ValidadorPassword.validar("Segura123"));
    }

    @Test
    @DisplayName("Menos de 8 caracteres lanza excepcion")
    void demasiadoCorta() {
        PasswordInvalidaException ex = assertThrows(
                PasswordInvalidaException.class,
                () -> ValidadorPassword.validar("Ab1"));
        assertTrue(ex.getMessage().contains("8"));
    }

    @Test
    @DisplayName("Sin digito lanza excepcion")
    void sinDigito() {
        assertThrows(PasswordInvalidaException.class,
                () -> ValidadorPassword.validar("SoloLetras"));
    }

    @Test
    @DisplayName("Sin mayuscula lanza excepcion")
    void sinMayuscula() {
        assertThrows(PasswordInvalidaException.class,
                () -> ValidadorPassword.validar("minuscula123"));
    }
}
