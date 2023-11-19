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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.poo.atitusound.dtos.ArtistDTO;
import br.edu.atitus.poo.atitusound.entities.ArtistEntity;
import br.edu.atitus.poo.atitusound.services.ArtistService;

@RestController
@RequestMapping("/artists")
public class ArtistController {
	
	private final ArtistService service;
	public ArtistController(ArtistService service) {
		super();
		this.service = service;
	}

	protected ArtistEntity convertDTO2Entity(ArtistDTO dto) {
		ArtistEntity entity = new ArtistEntity();
		entity.setName(dto.getName());
		entity.setImage(dto.getImage());
		return entity;
	}

	@PostMapping
	public ResponseEntity<ArtistEntity> postSave(@RequestBody ArtistDTO dto){
		ArtistEntity entity = convertDTO2Entity(dto);
		try {
			service.save(entity);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}
	
	@GetMapping
	public ResponseEntity<Page<List<ArtistEntity>>> getFind(
			@PageableDefault(page = 0, size = 10, sort = "name", direction = Direction.ASC) Pageable pageable,
			@RequestParam String name){
		Page<List<ArtistEntity>> lista;
		try {
			lista = service.findByNameContainingIgnoreCase(name, pageable);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		return ResponseEntity.ok(lista);
	}
	
	@GetMapping("/{uuid}")
	public ResponseEntity<ArtistEntity> getFindById(@PathVariable UUID uuid){
		Optional<ArtistEntity> entity;
		try {
			entity = service.findById(uuid);
		} catch (Exception e) {
			return ResponseEntity.badRequest().header("error", e.getMessage()).build();
		}
		if (entity.isEmpty())
			return ResponseEntity.notFound().build();
		else
			return ResponseEntity.ok(entity.get());
	}
}
