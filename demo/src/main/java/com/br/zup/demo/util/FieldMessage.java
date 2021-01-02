package com.br.zup.demo.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FieldMessage {
	
	private String field;
	private String message;
}
