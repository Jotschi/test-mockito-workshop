package com.gentics.workshop.example;

import static com.gentics.workshop.example.rocket.PartType.BOOSTER;
import static com.gentics.workshop.example.rocket.PartType.PAYLOAD;
import static com.gentics.workshop.example.rocket.PartType.STAGE;

import java.io.IOException;

import com.gentics.workshop.example.building.ControlException;
import com.gentics.workshop.example.building.LaunchControlCenter;
import com.gentics.workshop.example.building.LaunchPad;
import com.gentics.workshop.example.building.ProductionException;
import com.gentics.workshop.example.building.Project;
import com.gentics.workshop.example.rocket.Rocket;
import com.gentics.workshop.example.rocket.RocketException;

public class SpaceMissionStarter {

	public static void main(String[] args) throws ProductionException, ControlException, InterruptedException, RocketException, IOException {

		System.setProperty("SECRET_BRANCHNAME", "master");
		Project project = new Project();
		project.addPart(BOOSTER);
		project.addPart(BOOSTER);
		project.addPart(PAYLOAD);
		project.addPart(STAGE);
		project.addPart(STAGE);
		project.addPart(STAGE);

		Rocket rocket = SpacePort.getVAB().assemble(project);
		LaunchPad pad = SpacePort.getRandomLaunchpad();
		LaunchControlCenter control = SpacePort.getLaunchControlCenter();
		control.setPad(pad);
		rocket.moveTo(pad);

		control.prepare();
		control.fuel();
		control.launch();
	}

}
