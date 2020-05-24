package customExceptions;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = -3324950450729414001L;
	String username;
	
	public UserNotFoundException(String username) {
		super();
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
