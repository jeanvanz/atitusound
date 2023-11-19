package br.edu.atitus.poo.atitusound.servicesimpl;

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
		if (repository.existsByName(entity.getName()))
			throw new Exception("Já existe registro com este nome!");
	}

	@Override
	public ArtistEntity save(ArtistEntity entity) throws Exception {
		validate(entity);
		repository.save(entity);
		return entity;
	}
	
}
