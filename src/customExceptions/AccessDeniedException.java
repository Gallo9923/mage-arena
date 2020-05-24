package customExceptions;

public class AccessDeniedException extends Exception {

	private static final long serialVersionUID = 8994720822051654284L;
	private String username;
	
	public AccessDeniedException(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
