package br.com.palm.petschedule_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.palm.petschedule_api.model.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    
	boolean existsByEmail(String email);
    
	boolean existsByCpf(String cpf);
	
	boolean existsByCpfAndIdNot(String cpf, Long id);
    
	Optional<Tutor> findByEmail(String email);
}