package br.com.palm.petschedule_api.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;

public record TutorAtualizaDTO(
	@NotBlank(message = "O nome completo é obrigatório")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
	String cpf,

    @NotBlank(message = "O telefone é obrigatório")
    String telefone
) {}
