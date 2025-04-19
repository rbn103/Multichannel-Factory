¿Cómo el patrón Factory ayuda a agregar el canal de voz?
Porque separa la lógica de creación.
Solo creas la clase VozNotificacion y la registras en la factory.
No tocas el resto del código.
 ¿Qué cambios mínimos necesitas para añadir WhatsApp?
Solo dos cosas:
Crear la clase WhatsAppNotificacion.
Agregar el tipo "WHATSAPP" en la factory.¿Cómo mandamos por varios canales a la vez?
Creamos una clase MultiCanalNotificacion que recibe una lista de notificaciones.
