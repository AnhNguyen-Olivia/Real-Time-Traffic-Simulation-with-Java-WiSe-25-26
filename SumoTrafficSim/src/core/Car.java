package core;

import java.awt.*;
import java.io.*;
import javax.imageio.*;

public class Car extends Vehicle {
	Image image;
	
	public Car(double x, double y, double speed) {
		super(x, y, speed);
		try {
			this.image = ImageIO.read(new File("img/car.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(this.image, (int)(this.x - 10), (int)(this.y - 10), 20, 20, null);
	}

}
