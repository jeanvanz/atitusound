package br.edu.atitus.poo.atitusound.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.atitus.poo.atitusound.entities.GenericEntity;
import br.edu.atitus.poo.atitusound.services.GenericService;

public abstract class GenericController<TEntidade extends GenericEntity, TDTO> {
	
	protected abstract GenericService<TEntidade> getService();
	
	protected abstract TEntidade convertDTO2Entity(TDTO dto);

	@PostMapping
	public ResponseEntity<TEntidade> postSave(@RequestBody TDTO dto){
		TEntidade entity = convertDTO2Entity(dto);
		try {
			getService().save(entity);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}
	
	@GetMapping
	public ResponseEntity<Page<List<TEntidade>>> getFind(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.ASC) Pageable pageable,
			@RequestParam String name){
		Page<List<TEntidade>> lista;
		try {
			lista = getService().findByNameContainingIgnoreCase(name, pageable);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<TEntidade> getFindById(@PathVariable UUID uuid){
		Optional<TEntidade> entity;
		try {
			entity = getService().findById(uuid);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		if (entity.isEmpty())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(entity.get());
	}
	
	@PutMapping("/{uuid}")
	public ResponseEntity<TEntidade> put(@PathVariable UUID uuid, @RequestBody TDTO dto){
		TEntidade entity = convertDTO2Entity(dto);
		entity.setUuid(uuid);
		try {
			getService().save(entity);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(entity);
	}
	
	@DeleteMapping("/{uuid}")
	public ResponseEntity<?> delete(@PathVariable UUID uuid){
		try {
			getService().deleteById(uuid);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok().build();
	}
}
