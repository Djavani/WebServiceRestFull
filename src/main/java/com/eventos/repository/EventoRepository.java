package com.eventos.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventos.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, String> {

	Evento findByCodigo(long codigo);
}
