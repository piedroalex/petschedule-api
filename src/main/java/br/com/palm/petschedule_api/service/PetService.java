package br.com.palm.petschedule_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.palm.petschedule_api.dto.PetAtualizaDTO;
import br.com.palm.petschedule_api.dto.PetCadastroDTO;
import br.com.palm.petschedule_api.dto.PetRespostaDTO;
import br.com.palm.petschedule_api.exception.ResourceNotFoundException;
import br.com.palm.petschedule_api.model.Pet;
import br.com.palm.petschedule_api.model.Tutor;
import br.com.palm.petschedule_api.repository.PetRepository;
import br.com.palm.petschedule_api.repository.TutorRepository;

@Service
public class PetService {

	@Autowired
    private PetRepository petRepository;
	
	@Autowired
    private TutorRepository tutorRepository;

    @Transactional
    public PetRespostaDTO cadastrar(PetCadastroDTO dto) {
        Tutor tutor = tutorRepository.findById(dto.tutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com o ID: " + dto.tutorId()));

        Pet pet = new Pet(
                dto.nome(),
                dto.especie(),
                dto.raca(),
                dto.dataNascimento(),
                dto.peso(),
                tutor
        );

        Pet petSalvo = petRepository.save(pet);
        return PetRespostaDTO.toDTO(petSalvo);
    }

    @Transactional(readOnly = true)
    public List<PetRespostaDTO> listarTodos() {
        return petRepository.findAll()
                .stream()
                .map(PetRespostaDTO::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PetRespostaDTO> listarPorTutor(Long tutorId) {
        if (!tutorRepository.existsById(tutorId)) {
            throw new ResourceNotFoundException("Tutor não encontrado com o ID: " + tutorId);
        }
        return petRepository.findByTutorId(tutorId)
                .stream()
                .map(PetRespostaDTO::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PetRespostaDTO buscarPorId(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com o ID: " + id));
        return PetRespostaDTO.toDTO(pet);
    }

    @Transactional
    public PetRespostaDTO atualizar(Long id, PetAtualizaDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado com o ID: " + id));

        pet.setNome(dto.nome());
        pet.setEspecie(dto.especie());
        pet.setRaca(dto.raca());
        pet.setDataNascimento(dto.dataNascimento());
        pet.setPeso(dto.peso());

        Pet petAtualizado = petRepository.save(pet);
        return PetRespostaDTO.toDTO(petAtualizado);
    }

    @Transactional
    public void remover(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet não encontrado com o ID: " + id);
        }
        petRepository.deleteById(id);
    }
}