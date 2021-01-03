package com.br.zup.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertClienteDTO {
			
	@NotBlank(message = "O nome não deve estar em branco")
	@Pattern(regexp = "^[[ ]|\\p{L}*]+$", message = "Nome inválido")	
	@Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres")
	private String nome;
		
	@Pattern(regexp="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[AZa-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email inválido")
	private String email;
	
	@CPF(message = "CPF inválido")
	private String cpf;			
	
	@NotNull(message = "A data de nascimento deve ser informada")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	private LocalDate dataDeNascimento;
}