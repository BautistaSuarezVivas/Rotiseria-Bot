package com.rotiseria.chatbot.model;

import com.rotiseria.chatbot.model.enums.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // LAZY: no cargamos el cliente completo cada vez que leemos un pedido
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // STRING: guarda "PENDIENTE" en lugar de 0, 1, 2...
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Column(name = "tiempo_estimado")
    private Integer tiempoEstimado; // en minutos

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @CreationTimestamp
    @Column(name = "creado_en", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @UpdateTimestamp
    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    // Los items se crean y eliminan junto con el pedido
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPedido> items = new ArrayList<>();

    // El historial se crea junto con el pedido pero nunca se elimina
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY,
               cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<HistorialPedido> historial = new ArrayList<>();

    
    
    // Métodos de negocio en la entidad

    public void agregarItem(ItemPedido item) {
        item.setPedido(this);
        this.items.add(item);
        recalcularTotal();
    }

    public void recalcularTotal() {
        this.total = items.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void cambiarEstado(EstadoPedido nuevoEstado, String nota) {
        HistorialPedido registro = HistorialPedido.builder()
                .pedido(this)
                .estadoAnterior(this.estado)
                .estadoNuevo(nuevoEstado)
                .nota(nota)
                .build();
        this.historial.add(registro);
        this.estado = nuevoEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido p)) return false;
        return id != null && id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}