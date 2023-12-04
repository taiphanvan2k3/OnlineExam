package model.BEAN;

public class ResponseInfo {
	private int code;
	private String message;

	public ResponseInfo() {
		this.code = 200;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
