package com.example.demo.modelo.repositorio;

import com.example.demo.modelo.entidad.Rol;
import com.example.demo.modelo.entidad.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(RolNombre nombre);
}

