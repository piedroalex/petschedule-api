package br.com.palm.petschedule_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.palm.petschedule_api.dto.TutorAtualizaDTO;
import br.com.palm.petschedule_api.dto.TutorCadastroDTO;
import br.com.palm.petschedule_api.dto.TutorRespostaDTO;
import br.com.palm.petschedule_api.exception.ResourceNotFoundException;
import br.com.palm.petschedule_api.model.Tutor;
import br.com.palm.petschedule_api.repository.TutorRepository;

@Service
public class TutorService {

	@Autowired
    private TutorRepository tutorRepository;

    @Transactional
    public TutorRespostaDTO cadastrar(TutorCadastroDTO dto) {
        if (tutorRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema.");
        }

        if (tutorRepository.existsByCpf(dto.cpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema.");
        }

        Tutor tutor = new Tutor(
            dto.nome(),
            dto.email(),
            dto.cpf(),
            dto.telefone(),
            dto.senha()
        );

        Tutor tutorSalvo = tutorRepository.save(tutor);
        return TutorRespostaDTO.fromEntity(tutorSalvo);
    }
    
    @Transactional
    public TutorRespostaDTO atualizar(Long id, TutorAtualizaDTO dto) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com o ID: " + id));

        if (tutorRepository.existsByCpfAndIdNot(dto.cpf(), id)) {
            throw new IllegalArgumentException("O CPF informado já está em uso por outro tutor.");
        }

        tutor.setNome(dto.nome());
        tutor.setCpf(dto.cpf());
        tutor.setTelefone(dto.telefone());

        Tutor tutorAtualizado = tutorRepository.save(tutor);
        return TutorRespostaDTO.fromEntity(tutorAtualizado);
    }
    
    @Transactional
    public void remover(Long id) {
        if (!tutorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tutor não encontrado com o ID: " + id);
        }
        tutorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TutorRespostaDTO> listarTodos() {
        return tutorRepository.findAll()
                .stream()
                .map(TutorRespostaDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TutorRespostaDTO buscarPorId(Long id) {
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com o ID: " + id));
        return TutorRespostaDTO.fromEntity(tutor);
    }
}