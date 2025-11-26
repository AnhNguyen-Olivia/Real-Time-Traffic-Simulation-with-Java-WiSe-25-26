package core;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrafficLight {
    public double x, y;
    public String state = "R";

    public TrafficLight(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    Image image;
	
//	public TrafficLight(double x1, double y1) {
//		super(x1, y1);
//		try {
//			this.image = ImageIO.read(new File("img/greenlight.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void draw(Graphics g) {
//		g.drawImage(this.image, (int)(this.x - 10), (int)(this.y - 10), 20, 20, null);
//	}
}

