package com.eventos.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eventos.model.Evento;
import com.eventos.repository.EventoRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "API Rest Eventos")
@RestController
@RequestMapping("/evento")
public class EventoResource {

	@Autowired
	private EventoRepository eventoRepository;
	
	@ApiOperation(value = "Return a list of events")
	@GetMapping(produces = "application/json")
	public @ResponseBody ArrayList<Evento> listaEventos() {
		Iterable<Evento> listaEventos = eventoRepository.findAll();		
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		
		for(Evento evento : listaEventos) {
			long codigo = evento.getCodigo();			
			evento.add(linkTo(methodOn(EventoResource.class).evento(codigo)).withSelfRel());
			eventos.add(evento);
		}
		
		return eventos;
	}
	
	@ApiOperation(value = "Return a single event")
	@GetMapping(value = "/{codigo}", produces = "application/json")
	public @ResponseBody Evento evento(@PathVariable(value = "codigo") long codigo) {
		Evento evento = eventoRepository.findByCodigo(codigo);
		evento.add(linkTo(methodOn(EventoResource.class).listaEventos()).withRel("Lista de Eventos"));
		return evento;
	}
	
	@ApiOperation(value = "Post a single event")
	@PostMapping
	public Evento cadastraEvento(@RequestBody @Valid Evento evento) {
		return eventoRepository.save(evento);
	}
	
	@ApiOperation(value = "Remove a single event, that was declared on the request body")
	@DeleteMapping
	public Evento deletaEvento(@RequestBody Evento evento) {
		eventoRepository.delete(evento);
		return evento;
	}
	
}
