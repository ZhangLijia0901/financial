package com.financial.common.exception;

public class CommonException extends RuntimeException {

	private static final long serialVersionUID = 8801030345841433891L;

	private String code;

	public CommonException(String code, String msg) {
		super(msg);
		this.setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
