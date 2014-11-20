package com.gentics.workshop.example.rocket;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.gentics.workshop.example.SpacePort;
import com.gentics.workshop.example.building.LaunchPad;
import com.gentics.workshop.example.building.Project;

public class Rocket implements RocketPart {

	private final AtomicInteger fuel = new AtomicInteger(0);
	private final AtomicInteger snacks = new AtomicInteger(0);
	private boolean destroyed = false;
	private long speed = 0;
	private long alt = SpacePort.getAltitude();
	private Project project = null;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	private final AtomicReference<Rocket> rocketRef = new AtomicReference<Rocket>(this);

	public Rocket(Project project) {
		this.project = project;
		System.out.println("Creating rocket from project with {" + project.getBoosterCount() + "} boosters.");
	}

	public Future<Rocket> launch() throws RocketException {

		return executor.submit(new Callable<Rocket>() {
			public Rocket call() throws Exception {

				System.out.println("Engines starting..");
				while (true) {
					if (getFuel() > 0) {
						fuel.set(getFuel() - 1);
						speed += (int) (speed * 0.1);
						alt += 1000;
					} else {
						System.out.println("Fuel comsumed");
						break;
					}
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return rocketRef.get();
			}
		});
	}

	public void moveTo(LaunchPad pad) {
		System.out.println("Moving Rocket to pad {" + pad + "}");
		pad.setRocket(this);
	}

	public void destruct() {
		System.out.println("Booom... Rocket was remotely destroyed.");
		fuel.set(0);
		destroyed = true;
	}

	public int getFuel() {
		return fuel.get();
	}

	public int getSnacks() {
		return snacks.get();
	}

	public void fuel() throws InterruptedException {
		if (getFuel() >= 100) {
			System.out.println("Rocket already fueled.");
			return;
		}
		System.out.println("Fueling rocket..");
		for (int i = 0; i <= 100; i += 10) {
			System.out.println("Fueling..." + i + "/" + 100);
			Thread.sleep(250);
			fuel.set(i);
		}
		System.out.println("Rocket is now fueled.");

	}

	public void prepare() throws InterruptedException {
		if (snacks.get() >= 100) {
			System.out.println("Rocket already loaded with snacks.");
			return;
		}
		System.out.println("Preparing rocket..");
		for (int i = 0; i <= 100; i += 10) {
			System.out.println("Loading snacks..." + i + "/" + 100);
			Thread.sleep(125);
			snacks.set(i);
		}
		System.out.println("Rocket is now prepared.");
	}

	public void check() throws RocketException {
		System.out.println("Fuel: " + fuel + " Snacks: " + snacks + " Speed: " + speed + " Altitude: " + alt);
		if (destroyed) {
			throw new RocketException("The rocket was destroyed");
		}
	}
}
