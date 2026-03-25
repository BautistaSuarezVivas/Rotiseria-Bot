package com.rotiseria.chatbot.model;

import com.rotiseria.chatbot.model.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "historial_pedido")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    // Puede ser null si es el primer estado (creación del pedido)
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", length = 30)
    private EstadoPedido estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false, length = 30)
    private EstadoPedido estadoNuevo;

    @Column(name = "nota", columnDefinition = "TEXT")
    private String nota;

    @CreationTimestamp
    @Column(name = "cambiado_en", nullable = false, updatable = false)
    private OffsetDateTime cambiadoEn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistorialPedido h)) return false;
        return id != null && id.equals(h.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}