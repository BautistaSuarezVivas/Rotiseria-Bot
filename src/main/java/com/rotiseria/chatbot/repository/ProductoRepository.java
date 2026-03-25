package com.rotiseria.chatbot.repository;

import com.rotiseria.chatbot.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Solo los productos disponibles, ordenados para el menú
    List<Producto> findByDisponibleTrueOrderByOrdenMenuAsc();

    List<Producto> findByCategoriaAndDisponibleTrue(String categoria);
}