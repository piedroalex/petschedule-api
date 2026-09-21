package br.com.palm.petschedule_api.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TutorCadastroDTO(
	@NotBlank(message = "O nome completo é obrigatório")
    String nome,

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    String email,

    @NotBlank(message = "O CPF é obrigatório")
    @CPF(message = "CPF inválido")
	String cpf,

    @NotBlank(message = "O telefone é obrigatório")
    String telefone,

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String senha
) {}
