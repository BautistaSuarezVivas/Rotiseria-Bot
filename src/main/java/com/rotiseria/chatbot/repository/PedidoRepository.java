package com.rotiseria.chatbot.repository;

import com.rotiseria.chatbot.model.Pedido;
import com.rotiseria.chatbot.model.enums.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByClienteIdOrderByCreadoEnDesc(Long clienteId);

    List<Pedido> findByEstadoOrderByCreadoEnAsc(EstadoPedido estado);

    // JPQL con JOIN FETCH: carga los items en la misma query
    // Evita el problema N+1 cuando necesitamos el pedido con todos sus items
    @Query("""
        SELECT p FROM Pedido p
        JOIN FETCH p.items i
        JOIN FETCH i.producto
        WHERE p.id = :id
    """)
    Optional<Pedido> findByIdConItems(@Param("id") Long id);
}