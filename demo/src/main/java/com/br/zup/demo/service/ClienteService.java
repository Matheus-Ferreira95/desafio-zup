package com.br.zup.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.br.zup.demo.domain.Cliente;
import com.br.zup.demo.dto.InsertClienteDTO;
import com.br.zup.demo.mapper.ClienteMapper;
import com.br.zup.demo.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClienteRepository clienteRepository;	
			
	@Transactional
	public Cliente insert(InsertClienteDTO clienteDTO) {		
		return clienteRepository.save(toEntity(clienteDTO));		
	}	
	
//	private Cliente toEntity(InsertClienteDTO clienteDTO) {
//		return Cliente.builder()
//				.cpf(clienteDTO.getCpf())
//				.dataDeNascimento(clienteDTO.getDataDeNascimento())
//				.email(clienteDTO.getEmail())
//				.nome(clienteDTO.getNome())
//				.build();			
//	}	
	
	private Cliente toEntity(InsertClienteDTO clienteDTO) {
		return ClienteMapper.INSTANCE.toCliente(clienteDTO);
	}

	public void deleteById(Integer id) {
		clienteRepository.deleteById(id);		
	}	
	
	public List<Cliente> findAllCustom(String orderBy, String direction) {
		return clienteRepository.findAll(Sort.by(Direction.valueOf(direction), orderBy));
	}
	
	public void checkFields(InsertClienteDTO dto, BindingResult result) {
		if (existsCPF(dto.getCpf())) {			
			result.rejectValue("cpf", "cpf já cadastrado", "CPF já cadastrado");
		}
		if (existsEmail(dto.getEmail())) {			
			result.rejectValue("email", "email já cadastrado", "Email já cadastrado");
		}
		if (!ageValid(dto.getDataDeNascimento())) {
			result.rejectValue("dataDeNascimento", "idade inválida", "Idade inválida, apenas idades entre 18 e 100 anos");
		}			
	}	
	
	private boolean existsEmail(String email) {
		return clienteRepository.findByEmail(email) != null;
	}

	private boolean existsCPF(String cpf) {
		return clienteRepository.findByCpf(cpf) != null;
	}

	private boolean ageValid(LocalDate age) {
		LocalDate today = LocalDate.now();
		return age.compareTo(today.minusYears(18)) <= 0 && age.compareTo(today.minusYears(100)) >= 0;
	}	
}