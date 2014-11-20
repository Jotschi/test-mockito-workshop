package com.gentics.workshop.example.building;

import com.gentics.workshop.example.rocket.Rocket;
import com.gentics.workshop.example.rocket.RocketException;

public class LaunchPad {
	private Rocket rocket;

	private int damage = 0;
	private String name;

	public LaunchPad(String name) {
		this.name = name;
	}

	public void startWaterSuppressionSystem() {
		System.out.println("Enable sound suppression system!");
	}

	@Override
	public String toString() {
		return "Launchpad " + name;
	}

	public void setRocket(Rocket rocket) {
		this.rocket = rocket;
	}

	public Rocket getRocket() {
		return this.rocket;
	}

	/**
	 * Add a little amount of damage to the pad.
	 */
	public void addDamage() {
		damage += 10;
	}

	public int getDamage() {
		return damage;
	}

	/**
	 * Delegate the rocket launch command to the rocket
	 * @throws RocketException 
	 */
	public void launch() throws RocketException {
		rocket.launch();
		rocket = null;
	}
}
