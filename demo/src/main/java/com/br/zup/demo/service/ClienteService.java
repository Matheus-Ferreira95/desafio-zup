package com.br.zup.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.br.zup.demo.domain.Cliente;
import com.br.zup.demo.dto.InsertClienteDTO;
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
	
	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public void deleteById(Integer id) {
		clienteRepository.deleteById(id);		
	}	
	
	private Cliente toEntity(InsertClienteDTO clienteDTO) {
		Cliente entity = new Cliente();
		entity.setCpf(clienteDTO.getCpf());
		entity.setDataDeNascimento(clienteDTO.getDataDeNascimento());
		entity.setEmail(clienteDTO.getEmail());
		entity.setNome(clienteDTO.getNome());
		return entity;
	}		
	
	public void checkFields(InsertClienteDTO dto, BindingResult result) {
		if (existsCPF(dto.getCpf())) {			
			result.rejectValue("cpf", "cpf já cadastrado", "CPF já cadastrado");
		}
		if (existsEmail(dto.getEmail())) {			
			result.rejectValue("email", "email já cadastrado", "Email já cadastrado");
		}
		if (!ageValid(dto.getDataDeNascimento())) {
			result.rejectValue("dataDeNascimento", "idade inválida", "Necessário der maior de idade");
		}			
	}	
	
	public boolean existsEmail(String email) {
		return clienteRepository.findByEmail(email) != null;
	}

	public boolean existsCPF(String cpf) {
		return clienteRepository.findByCpf(cpf) != null;
	}

	public boolean ageValid(LocalDate age) {
		LocalDate today = LocalDate.now().minusYears(18);
		return age.compareTo(today) <= 0;
	}
}