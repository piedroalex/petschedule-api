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
import org.springframework.web.util.UriComponentsBuilder;

import br.com.palm.petschedule_api.dto.PetAtualizaDTO;
import br.com.palm.petschedule_api.dto.PetCadastroDTO;
import br.com.palm.petschedule_api.dto.PetRespostaDTO;
import br.com.palm.petschedule_api.service.PetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {

	@Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetRespostaDTO> cadastrar(
            @Valid @RequestBody PetCadastroDTO dto,
            UriComponentsBuilder uriBuilder) {        
        PetRespostaDTO petCriado = petService.cadastrar(dto);
        URI location = uriBuilder.path("/api/pets/{id}").buildAndExpand(petCriado.id()).toUri();
        return ResponseEntity.created(location).body(petCriado);
    }

    @GetMapping
    public ResponseEntity<List<PetRespostaDTO>> listarTodos() {        
        List<PetRespostaDTO> lista = petService.listarTodos();                
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<PetRespostaDTO>> listarPorTutor(@PathVariable Long tutorId) {        
        List<PetRespostaDTO> lista = petService.listarPorTutor(tutorId);                
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetRespostaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(petService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetRespostaDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PetAtualizaDTO dto) {        
        PetRespostaDTO atualizado = petService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        petService.remover(id);
        return ResponseEntity.noContent().build();
    }
}