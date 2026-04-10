package com.example.demo.modelo.repositorio;

import com.example.demo.modelo.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreoElectronico(String correoElectronico);

    boolean existsByCorreoElectronico(String correoElectronico);

    List<Usuario> findByHabilitadoTrue();
}

