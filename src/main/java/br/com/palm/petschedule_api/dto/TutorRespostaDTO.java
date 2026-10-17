package br.com.palm.petschedule_api.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.palm.petschedule_api.model.Tutor;

public record TutorRespostaDTO(
    Long id,
    String nome,
    String email,
    String cpf,
    String telefone,
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    LocalDateTime dataHoraCadastro
) {
    public static TutorRespostaDTO toDTO(Tutor tutor) {
        return new TutorRespostaDTO(
            tutor.getId(),
            tutor.getNome(),
            tutor.getEmail(),
            tutor.getCpf(),
            tutor.getTelefone(),
            tutor.getDataHoraCadastro()
        );
    }
}