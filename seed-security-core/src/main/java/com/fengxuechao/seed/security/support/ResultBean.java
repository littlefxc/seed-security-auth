package com.fengxuechao.seed.security.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author fengxuechao
 */
@Data
public class ResultBean<T> {

	private Integer code;

	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public ResultBean(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultBean(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static ResultBean<Object> ok(Object data) {
		return new ResultBean<>(200, "success", data);
	}
}