package ar.uba.fi.cb100.guia.i01_intro.i04_dificil.e10;

/**
 * Ejercicio 10 - Validador de contraseña.
 * Valida que una contraseña cumpla: minimo 8 caracteres, al menos un digito
 * y al menos una mayuscula. Lanza una excepcion segun la regla que falla.
 */
public class ValidadorPassword {

    /** Constructor privado: clase de utilidades, no se instancia. */
    private ValidadorPassword() {
    }

    /**
     * Valida la contraseña segun las reglas del ejercicio.
     * Lanza PasswordInvalidaException con un mensaje descriptivo si falla.
     *
     * @param p contraseña a validar (no debe ser null)
     */
    public static void validar(String p) {
        if (p == null) {
            throw new PasswordInvalidaException("La contraseña no puede ser null");
        }
        if (p.length() < 8) {
            throw new PasswordInvalidaException("La contraseña debe tener al menos 8 caracteres");
        }

        boolean tieneDigito = false;
        boolean tieneMayuscula = false;

        // Recorremos buscando al menos un digito y una mayuscula
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            if (c >= '0' && c <= '9') {
                tieneDigito = true;
            }
            if (c >= 'A' && c <= 'Z') {
                tieneMayuscula = true;
            }
        }

        if (!tieneDigito) {
            throw new PasswordInvalidaException("La contraseña debe tener al menos un digito");
        }
        if (!tieneMayuscula) {
            throw new PasswordInvalidaException("La contraseña debe tener al menos una mayuscula");
        }
    }

    /** Demostracion de uso. */
    public static void main(String[] args) {
        validar("Segura123");
        System.out.println("'Segura123' es valida");
        try {
            validar("corta1");
        } catch (PasswordInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
