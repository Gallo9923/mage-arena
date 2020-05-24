package customExceptions;

public class SaveNotFoundException extends Exception{

	private static final long serialVersionUID = 9149525632576720719L;
	private String saveName;
	
	public SaveNotFoundException(String saveName) {
		super();
		this.saveName = saveName;
		
	}
	
	public String getSaveName() {
		return saveName;
	}
	
	
}
