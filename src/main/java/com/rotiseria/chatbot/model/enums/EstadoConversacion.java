package com.rotiseria.chatbot.model.enums;

public enum EstadoConversacion {
    INICIO,             // Cliente acaba de escribir por primera vez
    MENU_MOSTRADO,      // Le enviamos el menú, esperando selección
    AGREGANDO_ITEMS,    // Está eligiendo productos
    CONFIRMANDO,        // Le mostramos el resumen, esperando confirmación
    PEDIDO_REGISTRADO   // Pedido guardado, conversación finalizada
}