import java.util.ArrayList;
import java.util.List;

interface Notificacion {
    void enviar();
}

abstract class Notificador {
    public void enviarNotificacion(String destinatario, String mensaje) {
        Notificacion notificacion = crearNotificacion(destinatario, mensaje);
        notificacion.enviar();
    }

    protected abstract Notificacion crearNotificacion(String destinatario, String mensaje);
}

class LlamadaEstadistica {
    private int duracion;

    public LlamadaEstadistica(int duracion) {
        this.duracion = duracion;
    }

    public int getDuracion() {
        return duracion;
    }
}

class EmailNotificacion implements Notificacion {
    private String email;
    private String mensaje;

    public EmailNotificacion(String email, String mensaje) {
        this.email = email;
        this.mensaje = mensaje;
    }

    @Override
    public void enviar() {
        System.out.println("Enviando email a " + email + ": " + mensaje);
    }
}

class SMSNotificacion implements Notificacion {
    private String telefono;
    private String mensaje;

    public SMSNotificacion(String telefono, String mensaje) {
        this.telefono = telefono;
        this.mensaje = mensaje;
    }

    @Override
    public void enviar() {
        System.out.println("Enviando SMS a " + telefono + ": " + mensaje);
    }
}

class PushNotificacion implements Notificacion {
    private String dispositivoId;
    private String mensaje;

    public PushNotificacion(String dispositivoId, String mensaje) {
        this.dispositivoId = dispositivoId;
        this.mensaje = mensaje;
    }

    @Override
    public void enviar() {
        System.out.println("Enviando notificación push a " + dispositivoId + ": " + mensaje);
    }
}

class VozNotificacion implements Notificacion {
    private String telefono;
    private String mensaje;
    private final int MAX_REINTENTOS = 3;
    private List<LlamadaEstadistica> estadisticas;

    public VozNotificacion(String telefono, String mensaje) {
        this.telefono = telefono;
        this.mensaje = mensaje;
        this.estadisticas = new ArrayList<>();
    }

    @Override
    public void enviar() {
        List<String> partes = dividirMensajeEnPartes(mensaje, 500);
        for (String parte : partes) {
            int intentosRestantes = MAX_REINTENTOS;
            boolean exitoParte = false;
            while (intentosRestantes > 0 && !exitoParte) {
                exitoParte = realizarLlamada(parte);
                if (!exitoParte) {
                    intentosRestantes--;
                    System.out.println("No hay respuesta, intentos restantes: " + intentosRestantes);
                }
            }
            if (exitoParte) {
                System.out.println("Llamada de voz exitosa a " + telefono + ": " + parte);
            } else {
                System.out.println("Agotados intentos para la parte: " + parte);
            }
        }
        int totalDuracion = estadisticas.stream().mapToInt(LlamadaEstadistica::getDuracion).sum();
        System.out.println("Total duración de llamadas: " + totalDuracion + " segundos");
    }

    private boolean realizarLlamada(String texto) {
        boolean exito = Math.random() > 0.5;
        if (exito) {
            convertirTextoAVoz(texto);
            registrarEstadisticaLlamada(30);
        }
        return exito;
    }

    private void convertirTextoAVoz(String texto) {
        System.out.println("Convirtiendo a voz: " + texto);
    }

    private List<String> dividirMensajeEnPartes(String msg, int maxLen) {
        List<String> partes = new ArrayList<>();
        for (int i = 0; i < msg.length(); i += maxLen) {
            partes.add(msg.substring(i, Math.min(i + maxLen, msg.length())));
        }
        return partes;
    }

    private void registrarEstadisticaLlamada(int duracion) {
        estadisticas.add(new LlamadaEstadistica(duracion));
    }
}

class WhatsAppNotificacion implements Notificacion {
    private String numero;
    private String mensaje;

    public WhatsAppNotificacion(String numero, String mensaje) {
        this.numero = numero;
        this.mensaje = mensaje;
    }

    @Override
    public void enviar() {
        System.out.println("Enviando WhatsApp a " + numero + ": " + mensaje);
    }
}

class SirenaNotificacion implements Notificacion {
    private String ubicacion;
    private String mensaje;

    public SirenaNotificacion(String ubicacion, String mensaje) {
        this.ubicacion = ubicacion;
        this.mensaje = mensaje;
    }

    @Override
    public void enviar() {
        System.out.println("Sirena de alerta en " + ubicacion + ": " + mensaje);
    }
}

// Factorías
class VozNotificacionFactory {
    public static VozNotificacion crearVozNotificacion(String tel, String msg) {
        return new VozNotificacion(tel, msg);
    }
}

class WhatsAppNotificacionFactory {
    public static WhatsAppNotificacion crearWhatsAppNotificacion(String num, String msg) {
        return new WhatsAppNotificacion(num, msg);
    }
}

class SirenaNotificacionFactory {
    public static SirenaNotificacion crearSirenaNotificacion(String loc, String msg) {
        return new SirenaNotificacion(loc, msg);
    }
}

class NotificadorEmail extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return new EmailNotificacion(dest, msg);
    }
}

class NotificadorSMS extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return new SMSNotificacion(dest, msg);
    }
}

class NotificadorPush extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return new PushNotificacion(dest, msg);
    }
}

class NotificadorVoz extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return VozNotificacionFactory.crearVozNotificacion(dest, msg);
    }
}

class NotificadorWhatsApp extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return WhatsAppNotificacionFactory.crearWhatsAppNotificacion(dest, msg);
    }
}

class NotificadorSirena extends Notificador {
    @Override
    protected Notificacion crearNotificacion(String dest, String msg) {
        return SirenaNotificacionFactory.crearSirenaNotificacion(dest, msg);
    }
}