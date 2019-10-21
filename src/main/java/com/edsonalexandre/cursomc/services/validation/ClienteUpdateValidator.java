package com.edsonalexandre.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.edsonalexandre.cursomc.domain.Cliente;
import com.edsonalexandre.cursomc.dto.ClienteDTO;
import com.edsonalexandre.cursomc.repositories.ClienteRepository;
import com.edsonalexandre.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer id = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();

		Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(id)) {
			list.add(new FieldMessage("email", "E-mail já cadastrado"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}
}