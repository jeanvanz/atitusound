package br.edu.atitus.poo.atitusound.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.atitus.poo.atitusound.entities.ArtistEntity;

public interface ArtistService {

	ArtistEntity save(ArtistEntity entity) throws Exception;
	
	List<ArtistEntity> findAll() throws Exception;
	
	Page<List<ArtistEntity>> findByNameContainingIgnoreCase(String name, Pageable pageable) throws Exception;
	
	Optional<ArtistEntity> findById(UUID uuid) throws Exception;
	
	void deleteById(UUID uuid) throws Exception;
}
