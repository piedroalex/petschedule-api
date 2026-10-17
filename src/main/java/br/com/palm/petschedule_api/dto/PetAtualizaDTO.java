package br.com.palm.petschedule_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.palm.petschedule_api.enumerated.Especie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

public record PetAtualizaDTO(
	@NotBlank(message = "O nome do pet é obrigatório")
    String nome,

    @NotNull(message = "A espécie é obrigatória")
    Especie especie,

    String raca,

    @PastOrPresent(message = "A data de nascimento não pode ser no futuro")
    LocalDate dataNascimento,

    @Positive(message = "O peso deve ser um valor positivo")
	BigDecimal peso
) {}
