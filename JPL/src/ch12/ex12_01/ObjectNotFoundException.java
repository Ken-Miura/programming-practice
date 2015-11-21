/* Copyright (C) 2015 Ken Miura */
package ch12.ex12_01;

/**
 * @author Ken Miura
 *
 */
public class ObjectNotFoundException extends Exception {

	/**
	 * V1.0
	 */
	private static final long serialVersionUID = -1786347299376197657L;
	/* 保持する追加データ：見つからなかったオブジェクト */
	private final Object object;

	public ObjectNotFoundException(Object object) {
		this.object = object;
	}
	
	public ObjectNotFoundException(String message, Object object) {
		super(message);
		this.object = object;
	}

	public ObjectNotFoundException(Throwable cause, Object object) {
		super(cause);
		this.object = object;
	}

	public ObjectNotFoundException(String message, Throwable cause, Object object) {
		super(message, cause);
		this.object = object;
	}

	public ObjectNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace, Object object) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.object = object;
	}

	/**
	 * @return object
	 */
	public Object getObject() {
		return object;
	}

}
