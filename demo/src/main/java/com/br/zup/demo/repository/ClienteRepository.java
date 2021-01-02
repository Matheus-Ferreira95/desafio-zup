package com.br.zup.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.zup.demo.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

//	@Query(value = "SELECT * FROM tb_cliente WHERE email =:email", nativeQuery = true)
//	@Query("SELECT obj FROM Cliente obj WHERE obj.email =:email")
	Cliente findByEmail(String email);
	
//	@Query(value = "SELECT * FROM tb_cliente WHERE cpf =:cpf", nativeQuery = true)
//	@Query("SELECT obj FROM Cliente obj WHERE obj.cpf =:cpf")
	Cliente findByCpf(String cpf);
}
