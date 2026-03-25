package com.rotiseria.chatbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Getter
@Setter	
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    // Precio snapshot: el precio del producto al momento del pedido
    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    // Factory method: construye el item calculando el subtotal automáticamente
    
    public static ItemPedido crear(Producto producto, Integer cantidad) {
        BigDecimal subtotal = producto.getPrecio()
                .multiply(BigDecimal.valueOf(cantidad));
        return ItemPedido.builder()
                .producto(producto)
                .cantidad(cantidad)
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemPedido i)) return false;
        return id != null && id.equals(i.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}