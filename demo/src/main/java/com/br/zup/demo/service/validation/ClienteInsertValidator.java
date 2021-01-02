package com.br.zup.demo.service.validation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.br.zup.demo.dto.InsertClienteDTO;
import com.br.zup.demo.repository.ClienteRepository;
import com.br.zup.demo.util.FieldMessage;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, InsertClienteDTO> {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public boolean isValid(InsertClienteDTO dto, ConstraintValidatorContext context) {
				
		List<FieldMessage> erros = new ArrayList<>();
		
		if (existsEmail(dto.getEmail())) {
			erros.add(fieldMessageBuilder("email", "Email já cadastrado"));
		}
		
		if (existsCPF(dto.getCpf())) {
			erros.add(fieldMessageBuilder("cpf", "CPF já cadastrado"));
		}
		
		if (dto.getDataDeNascimento() != null && !ageValid(dto.getDataDeNascimento())) {
			erros.add(fieldMessageBuilder("dataDeNascimento", "Necessário ser maior de idade"));
		}
				
		for (FieldMessage x : erros) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(x.getMessage()).addPropertyNode(x.getField())
					.addConstraintViolation();
		}	
		
		return erros.isEmpty();		
	}

	private boolean existsEmail(String email) {
		return clienteRepository.findByEmail(email) != null;
	}

	private boolean existsCPF(String cpf) {
		return clienteRepository.findByCpf(cpf) != null;
	}

	private boolean ageValid(LocalDate age) {
		LocalDate today = LocalDate.now().minusYears(18);
		return age.compareTo(today) <= 0;
	}
	
	private FieldMessage fieldMessageBuilder(String field, String message) {
		return FieldMessage.builder().field(field).message(message).build();
	}
}