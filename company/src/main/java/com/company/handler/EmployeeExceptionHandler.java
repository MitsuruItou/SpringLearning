package com.company.handler;

import static com.company.constants.MsgConst.*;
import static com.company.constants.PathConst.*;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeExceptionHandler {

	@Autowired
	private MessageSource msg;

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String allExceptionHandler(Exception exception, Model model) {
		model.addAttribute("errMessage", msg.getMessage(MSG_EXCEPTION_OCCURRED, null, Locale.JAPAN));
		model.addAttribute("errDetail",
				msg.getMessage(MSG_EXCEPTION_CLASS, new String[] { exception.getClass().getName() }, Locale.JAPAN));
		return EMP_ERROR;
	}
}
