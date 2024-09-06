package com.csmtech.SJTA.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {

	private int statusCode;
	private String status;
	private String message;
	private T data;

}
