package br.edu.atitus.poo.atitusound.servicesimpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.atitus.poo.atitusound.entities.ArtistEntity;
import br.edu.atitus.poo.atitusound.repositories.ArtistRepository;
import br.edu.atitus.poo.atitusound.services.ArtistService;

@Service
public class ArtistServiceImpl implements ArtistService{
	
	private final ArtistRepository repository;
	
	public ArtistServiceImpl(ArtistRepository repository) {
		super();
		this.repository = repository;
	}

	protected void validate(ArtistEntity entity) throws Exception{
		if (entity.getName() == null || entity.getName().isEmpty())
			throw new Exception("Campo nome inválido!");
		if (entity.getUuid() == null) {
			if (repository.existsByName(entity.getName()))
				throw new Exception("Já existe registro com este nome!");
		} else {
			if (!repository.existsById(entity.getUuid()))
				throw new Exception("Não existe registro com este UUID!");
			if (repository.existsByNameAndUuidNot(entity.getName(), entity.getUuid()))
				throw new Exception("Já existe registro com este nome!");
		}
	}

	@Override
	public ArtistEntity save(ArtistEntity entity) throws Exception {
		validate(entity);
		repository.save(entity);
		return entity;
	}

	@Override
	public List<ArtistEntity> findAll() throws Exception {
		return repository.findAll();
	}

	protected void validateByFindName(String name, Pageable pageable) throws Exception{
		
	}
	@Override
	public Page<List<ArtistEntity>> findByNameContainingIgnoreCase(String name, Pageable pageable) throws Exception {
		validateByFindName(name, pageable);
		return repository.findByNameContainingIgnoreCase(name, pageable);
	}

	protected void validateFindById(UUID uuid) throws Exception{
		
	}
	@Override
	public Optional<ArtistEntity> findById(UUID uuid) throws Exception {
		validateFindById(uuid);
		return repository.findById(uuid);
	}

	protected void validateDeleteById(UUID uuid) throws Exception{
		if (!repository.existsById(uuid))
			throw new Exception("Não existe registro com este UUID");
	}
	@Override
	public void deleteById(UUID uuid) throws Exception{
		validateDeleteById(uuid);
		repository.deleteById(uuid);
	}
	
}
