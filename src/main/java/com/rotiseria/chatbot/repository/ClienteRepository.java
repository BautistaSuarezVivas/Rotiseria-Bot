package com.rotiseria.chatbot.repository;

import com.rotiseria.chatbot.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Spring Data deriva el SQL de los nombres de métodos
    Optional<Cliente> findByTelefono(String telefono);

    boolean existsByTelefono(String telefono);
}