package com.gentics.workshop.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gentics.workshop.example.building.LaunchControlCenter;
import com.gentics.workshop.example.building.LaunchPad;
import com.gentics.workshop.example.building.VehicleAssemblyBuilding;

/**
 * The space port is something similar like the kennedy space center.
 * 
 * @author johannes2
 *
 */
public class SpacePort {

	private static List<LaunchPad> launchPads = new ArrayList<>();

	private static LaunchControlCenter control;
	
	static {
		init();
	}

	private static void init() {
		launchPads.add(new LaunchPad("39A"));
		launchPads.add(new LaunchPad("39B"));
	}

	public static VehicleAssemblyBuilding getVAB() {
		return VehicleAssemblyBuilding.getInstance();
	}

	public static LaunchPad getRandomLaunchpad() {
		int idx = new Random().nextInt(launchPads.size());
		return launchPads.get(idx);
	}

	public static LaunchControlCenter getLaunchControlCenter() {
		if (control == null) {
			control = new LaunchControlCenter();
		}
		return control;
	}

	public static long getAltitude() {
		return 150;
	}

}
