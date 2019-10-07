package kr.co.itcen.mysite.dto;

public class JSONResult {
	private String result; // success pr fail;
	private Object data; // if success,set
	private String message;//if fail
	
	public static JSONResult success(Object data) {
		return new JSONResult(data);
	}
	public static JSONResult fail(String data) {
		return new JSONResult(data);
	}
	private JSONResult() {
		
	}
	private JSONResult(Object data) {
		this.result="success";
		this.data =data;
	}
	private JSONResult(String message) {
		this.result="fail";
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
