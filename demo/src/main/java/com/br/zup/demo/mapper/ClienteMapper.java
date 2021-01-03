package com.br.zup.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.br.zup.demo.domain.Cliente;
import com.br.zup.demo.dto.InsertClienteDTO;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
	
	public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);
	
	@Mapping(target = "id", ignore = true)
	public abstract Cliente toCliente(InsertClienteDTO clienteDTO);	
}
