package oose.vcs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Simulator extends JPanel {

	private BufferedImage boat;
	private int xPos = 0;
	private int direction = 1;
	private File file;
	private Timer timer;
	int maximumVelocity, currentVelocity;

	public Simulator(int maximumVelocity, int currentVelocity, String vehicleName) {

		try {
			file = new File(System.getProperty("user.dir") + "/img/" + vehicleName + ".png");
			boat = ImageIO.read(file);
			timer = new Timer(maximumVelocity / currentVelocity, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					xPos += direction;
					if (xPos + boat.getWidth() > getWidth()) {
						xPos = 0;
						direction *= -1;

					} else if (xPos < 0) {
						xPos = 0;
						direction *= -1;
					}
					repaint();
				}

			});
			timer.setRepeats(true);
			timer.setCoalesce(true);
			timer.start();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void updateTimer(int maximumVelocity, int currentVelocity) {
		timer.setDelay(maximumVelocity / currentVelocity);
	}

	@Override
	public Dimension getPreferredSize() {
		return boat == null ? super.getPreferredSize() : new Dimension(boat.getWidth() * 4, boat.getHeight());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int y = getHeight() - boat.getHeight();
		g.drawImage(boat, xPos, y, this);

	}

}
