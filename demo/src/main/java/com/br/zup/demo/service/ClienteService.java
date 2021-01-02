package com.br.zup.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	private Cliente toEntity(InsertClienteDTO clienteDTO) {
		Cliente entity = new Cliente();
		entity.setCpf(clienteDTO.getCpf());
		entity.setDataDeNascimento(clienteDTO.getDataDeNascimento());
		entity.setEmail(clienteDTO.getEmail());
		entity.setNome(clienteDTO.getNome());
		return entity;
	}	
}