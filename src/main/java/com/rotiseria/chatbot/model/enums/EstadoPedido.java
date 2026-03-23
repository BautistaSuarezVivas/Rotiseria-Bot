package com.rotiseria.chatbot.model.enums;

public enum EstadoPedido {
	
    PENDIENTE,    // Recién creado por el bot
    CONFIRMADO,   // Cliente confirmó
    EN_COCINA,    // El negocio lo tomó
    LISTO,        // Listo para entregar/retirar
    ENTREGADO,    // Completado
    CANCELADO     // Cancelado por cualquier motivo
}