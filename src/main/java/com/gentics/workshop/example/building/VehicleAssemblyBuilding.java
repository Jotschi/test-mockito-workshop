package com.gentics.workshop.example.building;

import com.gentics.workshop.example.rocket.Rocket;

/**
 * The vehicle assembly building is responsible for assembling the rocket and preparing it for every launch.
 * 
 * @author johannes2
 *
 */
public class VehicleAssemblyBuilding {

	private static VehicleAssemblyBuilding instance;

	public static VehicleAssemblyBuilding getInstance() {

		if (instance == null) {
			return new VehicleAssemblyBuilding();
		}
		return instance;
	}

	public Rocket assemble(Project project) throws ProductionException {
		return new Rocket(project);
	}

}
