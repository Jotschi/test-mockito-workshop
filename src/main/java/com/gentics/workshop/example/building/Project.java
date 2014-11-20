package com.gentics.workshop.example.building;

import java.util.ArrayList;
import java.util.List;

import com.gentics.workshop.example.rocket.Booster;
import com.gentics.workshop.example.rocket.PartType;
import com.gentics.workshop.example.rocket.RocketPart;

public class Project {

	private List<RocketPart> parts = new ArrayList<>();

	private int nPayLoads = 0;
	private int nStages = 0;
	private int nBoosters = 0;

	public void addPart(PartType partType) throws ProductionException {
		parts.add(RocketPartFactory.producePart(partType));
		switch (partType) {
		case BOOSTER:
			nBoosters++;
			break;
		case PAYLOAD:
			nPayLoads++;
			break;
		case STAGE:
			nStages++;
			break;
		}
	}

	@Deprecated
	public void addBooster(Booster booster) {
		parts.add(booster);
		nBoosters++;
	}

	public int getPayLoadCount() {
		return nPayLoads;
	}

	public int getStageCount() {
		return nStages;
	}

	public int getBoosterCount() {
		return nBoosters;
	}

}
