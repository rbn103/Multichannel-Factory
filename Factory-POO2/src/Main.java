//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Notificador nv = new NotificadorVoz();
        String mensajeLargo = new String(new char[10]).replace('\0', 'A');
        nv.enviarNotificacion("mi casa", mensajeLargo);

        Notificador nws = new NotificadorWhatsApp();
        nws.enviarNotificacion("12345678", "Mensaje urgente por WhatsApp");

        Notificador ns = new NotificadorSirena();
        ns.enviarNotificacion("12345678", "Alerta crítica: evacuación inmediata");
    }
}
