package com.br.zup.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.br.zup.demo.domain.Cliente;
import com.br.zup.demo.dto.InsertClienteDTO;
import com.br.zup.demo.service.ClienteService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(value = "/cliente")
@RequiredArgsConstructor
public class ClienteController {
	
	@Autowired
	private final ClienteService clienteService;
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView formCadastro(InsertClienteDTO dto) {		
		return new ModelAndView("/form/cadastro");
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public ModelAndView cadastrarCliente(@Valid InsertClienteDTO dto, BindingResult result, RedirectAttributes redirectAttributes) {	
		ModelAndView mv = new ModelAndView();
		
		if (!result.hasErrors()) {			
			clienteService.checkFields(dto, result);
		}
		
		if(result.hasErrors()) {		
			mv.setViewName("form/cadastro");
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}	
			
		clienteService.insert(dto);	
		mv.setViewName("redirect:/cliente/cadastro");
		mv.setStatus(HttpStatus.CREATED);		
		redirectAttributes.addFlashAttribute("message", "Cliente cadastrado com sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		return mv;
	}
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView listarClientes() {
		ModelAndView mv = new ModelAndView("listagemCliente");		
		mv.addObject("clientes", new ArrayList<Cliente>());
		return mv;
	}
	
	@RequestMapping(value = "/deletar/{id}", method = RequestMethod.GET)
	public String deleteCliente(@PathVariable Integer id) {
		clienteService.deleteById(id);
		return "redirect:/cliente/listar";
	}
	
	@RequestMapping(value = "/sort", method = RequestMethod.POST)
	public String listar(String orderBy, String direction, Model model) {		
		List<Cliente> clientes = clienteService.findAllCustom(orderBy, direction);
		model.addAttribute("clientes", clientes);
		return "listagemCliente";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String redirect() {
		return "redirect:/";
	}
}