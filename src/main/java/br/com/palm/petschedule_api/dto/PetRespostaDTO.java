package br.com.palm.petschedule_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.palm.petschedule_api.enumerated.Especie;
import br.com.palm.petschedule_api.model.Pet;

public record PetRespostaDTO(Long id,
    String nome,
    Especie especie,
    String raca,
    LocalDate dataNascimento,
    BigDecimal peso,
    Long tutorId,
    String tutorNome,

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    LocalDateTime criadoEm
) {
    public static PetRespostaDTO toDTO(Pet pet) {
        return new PetRespostaDTO(
            pet.getId(),
            pet.getNome(),
            pet.getEspecie(),
            pet.getRaca(),
            pet.getDataNascimento(),
            pet.getPeso(),
            pet.getTutor().getId(),
            pet.getTutor().getNome(),
            pet.getCriadoEm()
        );
    }
}