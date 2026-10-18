package br.com.palm.petschedule_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.palm.petschedule_api.dto.LoginDTO;
import br.com.palm.petschedule_api.dto.TutorAtualizaDTO;
import br.com.palm.petschedule_api.dto.TutorCadastroDTO;
import br.com.palm.petschedule_api.dto.TutorRespostaDTO;
import br.com.palm.petschedule_api.service.TutorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tutores")
public class TutorController {

	@Autowired
    private TutorService tutorService;

	@PostMapping("/login")
    public ResponseEntity<TutorRespostaDTO> login(@Valid @RequestBody LoginDTO dto) {
		TutorRespostaDTO tutorAutenticado = tutorService.autenticar(dto);
        return ResponseEntity.ok(tutorAutenticado);
    }
	
    @PostMapping
    public ResponseEntity<TutorRespostaDTO> cadastrar(@Valid @RequestBody TutorCadastroDTO dto) {
        TutorRespostaDTO novoTutor = tutorService.cadastrar(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoTutor.id())
                .toUri();

        return ResponseEntity.created(uri).body(novoTutor);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TutorRespostaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TutorAtualizaDTO dto) {
        
        TutorRespostaDTO tutorAtualizado = tutorService.atualizar(id, dto);
        return ResponseEntity.ok(tutorAtualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        tutorService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<TutorRespostaDTO>> listarTodos() {
        List<TutorRespostaDTO> tutores = tutorService.listarTodos();
        return ResponseEntity.ok(tutores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorRespostaDTO> buscarPorId(@PathVariable Long id) {
        TutorRespostaDTO tutor = tutorService.buscarPorId(id);
        return ResponseEntity.ok(tutor);
    }
}