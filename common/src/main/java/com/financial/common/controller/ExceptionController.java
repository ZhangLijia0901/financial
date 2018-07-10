package com.financial.common.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.financial.common.exception.CommonException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {

	@ExceptionHandler({ CommonException.class })
	public String commonException(Exception e) {
		log.error("CommonException 异常： ", e);
		return "/error";
	}

	@ExceptionHandler({ Exception.class })
	public String exception(Exception e) {
		log.error("Exception 异常： ", e);
		return "/error";
	}

}
