package model;
import java.io.Serializable;

import javafx.scene.image.Image;

public class AnimatedImage implements Serializable{
  
	private static final long serialVersionUID = 2507031481538731540L;
	
	// assumes animation loops,
    //  each image displays for equal time
    public Image[] frames;
    public double duration;
    
    public Image getFrame(double time)
    {
        int index = (int)((time % (frames.length * duration)) / duration);
        return frames[index];
    }
    
}