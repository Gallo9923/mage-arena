package model;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;

public class AnimatedImage implements Serializable{
  
	private static final long serialVersionUID = 2507031481538731540L;
	
	// assumes animation loops,
    //  each image displays for equal time
	private String[] frames;
    private  double duration;
    private double width;
    private double height;
    
    public AnimatedImage(String[] frames, double duration, double width, double height) {
    	this.frames = frames;
    	this.duration = duration;
    	this.width = width;
    	this.height = height;
    }
    
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        
        Image image = null;
        
		try {
			image = new Image(new FileInputStream(frames[index]), width, height, false, false);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        
        return image;
    }
    
}