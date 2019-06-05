package com.charge.Exception;

/**
 * <p>异常基类</p>
 * 
 * @since 1.0
 * @version 1.0
 */
public abstract class BaseException extends RuntimeException {

	private static final long serialVersionUID = 777117105632383492L;
	
	/**
	 * 构造器
	 * 
	 */
	public BaseException() {
		super();
	}

	/**
	 * 构造器
	 * 
	 * @param message	异常详细信息
	 * @param cause	异常原因
	 */
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造器
	 * 
	 * @param message	异常详细信息
	 */
	public BaseException(String message) {
		super(message);
	}

	/**
	 * 构造器
	 * 
	 * @param cause	异常原因
	 */
	public BaseException(Throwable cause) {
		super(cause);
	}

}
