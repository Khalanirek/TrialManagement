package base;

public class RequestError {

	private String warn;
    private String error;
    private String status;

    public RequestError() {

    }

	public RequestError(String warn, String error, String status) {
		super();
		this.warn = warn;
		this.error = error;
		this.status = status;
	}

	public String getWarn() {
		return warn;
	}

	public void setWarn(String warn) {
		this.warn = warn;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}
