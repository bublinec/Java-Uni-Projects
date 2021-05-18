package oose.vcs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;

import vehicle.types.Airplane;
import vehicle.types.Bicycle;
import vehicle.types.Boat;
import vehicle.types.Bus;
import vehicle.types.Car;
import vehicle.types.Helicopter;
import vehicle.types.Motorcycle;
import vehicle.types.Ship;
import vehicle.types.Train;
import vehicle.types.Tram;
import vehicle.types.Truck;
import vehicle.types.Vehicle;

public class Controller {

	private Vehicle vehicle;
	private String[] vehicles = { "Boat", "Ship", "Truck", "Motorcycle", "Bus", "Car", "Bicycle", "Helicopter",
			"Airplane", "Tram", "Train" };
	private Simulator simulationPane;
	private JLabel speedlabel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JComboBox<String> combobox;
	private JFrame frame;

	private boolean accelerate, decelerate, cruise, stop;
	public int currentVelocity = 1;
	public int maximumVelocity = 300;

	public static void main(String args[]) {
		new Controller();
	}

	public Controller() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				frame = new JFrame("Vehicle Control System");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLayout(new BorderLayout());

				combobox = new JComboBox<String>(vehicles);
				combobox.setSelectedIndex(6);
				combobox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedIndex = combobox.getSelectedIndex();
						String vehicleName = vehicles[selectedIndex];
						initialiseVehicle(vehicleName);
					}
				});

				speedlabel = new JLabel("          ");

				configStart();
				configAccelerate();
				configDecelerate();
				configCruise();
				configStop();

				JToolBar toolBar = new JToolBar();
				toolBar.setRollover(true);

				toolBar.add(combobox);
				toolBar.add(speedlabel);
				toolBar.add(button1);
				toolBar.add(button2);
				toolBar.add(button3);
				toolBar.add(button4);
				toolBar.add(button5);

				frame.add(toolBar, BorderLayout.NORTH);
				frame.setPreferredSize(new Dimension(800, 200));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}

		});
	}

	private void configStart() {
		button1 = new JButton("start");
		button1.setBackground(Color.lightGray);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (vehicle == null) {
					int selectedIndex = combobox.getSelectedIndex();
					String vehicleName = vehicles[selectedIndex];
					initialiseVehicle(vehicleName);
					speedlabel.setText(vehicle.printSpeed());
				}
				if (simulationPane != null) {
					frame.remove(simulationPane);
				}
				accelerate = false;
				decelerate = false;
				cruise = false;
				stop = false;

				setActiveButton(1);

				final String vehicleName = vehicle.getClass().getSimpleName().toLowerCase();
				simulationPane = new Simulator(maximumVelocity, currentVelocity, vehicleName);
				frame.add(simulationPane, BorderLayout.CENTER);
				frame.revalidate();
				frame.repaint();
			}

		});
	}

	private void configAccelerate() {
		button2 = new JButton("accelerate");
		button2.setBackground(Color.lightGray);
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accelerate = true;
				decelerate = false;
				cruise = false;
				stop = false;

				setActiveButton(2);

				Thread thread = new Thread() {
					public void run() {
						try {
							while (accelerate) {
								Thread.sleep(2 * 1000);

								if (currentVelocity <= maximumVelocity) {
									currentVelocity = currentVelocity + 1;
									vehicle.setCurrentSpeed(currentVelocity);
									speedlabel.setText(vehicle.printSpeed());
									simulationPane.updateTimer(maximumVelocity, currentVelocity);
								}
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};

				thread.start();
			}
		});

	}

	private void configCruise() {
		button3 = new JButton("cruise");
		button3.setBackground(Color.lightGray);
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accelerate = false;
				decelerate = false;
				cruise = true;
				stop = false;

				setActiveButton(3);
			}
		});
	}

	private void configDecelerate() {
		button4 = new JButton("decelerate");
		button4.setBackground(Color.lightGray);
		button4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accelerate = false;
				decelerate = true;
				cruise = false;
				stop = false;

				setActiveButton(4);

				Thread thread = new Thread() {
					public void run() {
						try {
							while (decelerate) {
								Thread.sleep(2 * 1000);

								if (currentVelocity > 1) {
									currentVelocity = currentVelocity - 1;
									vehicle.setCurrentSpeed(currentVelocity);
									speedlabel.setText(vehicle.printSpeed());
									simulationPane.updateTimer(maximumVelocity, currentVelocity);
								}
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};

				thread.start();
			}
		});
	}

	private void configStop() {
		button5 = new JButton("stop");
		button5.setBackground(Color.lightGray);
		button5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				accelerate = false;
				decelerate = false;
				cruise = false;
				stop = true;

				setActiveButton(1);

				currentVelocity = 1;
				vehicle.setCurrentSpeed(currentVelocity);
				speedlabel.setText(vehicle.printSpeed());
				simulationPane.updateTimer(maximumVelocity, currentVelocity);
			}
		});
	}

	private void setActiveButton(int number) {
//		reset all buttons
		button1.setBackground(Color.lightGray);
		button2.setBackground(Color.lightGray);
		button3.setBackground(Color.lightGray);
		button4.setBackground(Color.lightGray);
		button5.setBackground(Color.lightGray);

//		set the right one to be active
		switch (number) {
		case 1:
			button1.setBackground(Color.green);
			break;
		case 2:
			button2.setBackground(Color.green);
			break;
		case 3:
			button3.setBackground(Color.green);
			break;
		case 4:
			button4.setBackground(Color.green);
			break;
		case 5:
			button5.setBackground(Color.green);
			break;
		default:
			System.out.println("Something went wrong!");
		}
	};

	private void initialiseVehicle(String vehicleName) {
//		neater than if-else, doesn't affect metrics
		switch (vehicleName) {
		case "Boat":
			vehicle = new Boat("Apollo ");
			break;
		case "Ship":
			vehicle = new Ship("Cruizz");
			break;
		case "Truck":
			vehicle = new Truck("Ford F-650");
			break;
		case "Motorcycle":
			vehicle = new Motorcycle("Suzuki");
			break;
		case "Bus":
			vehicle = new Bus("Aero");
			break;
		case "Car":
			vehicle = new Car("BMW");
			break;
		case "Bicycle":
			vehicle = new Bicycle("A-bike");
			break;
		case "Helicopter":
			vehicle = new Helicopter("Eurocopter");
			break;
		case "Airplane":
			vehicle = new Airplane("BA");
			break;
		case "Tram":
			vehicle = new Tram("EdinburghTram");
			break;
		case "Train":
			vehicle = new Train("Virgin", 4);
			break;
		default:
			System.out.println("Somtehing went Wrong");
		}
	}
}
