package com.gentics.workshop.example.building;

import com.gentics.workshop.example.rocket.Booster;
import com.gentics.workshop.example.rocket.PartType;
import com.gentics.workshop.example.rocket.Payload;
import com.gentics.workshop.example.rocket.RocketPart;
import com.gentics.workshop.example.rocket.Stage;

/**
 * The rocket factory will build new rocket parts
 * 
 * @author johannes2
 *
 */
public class RocketPartFactory {

	public static RocketPart producePart(PartType part) throws ProductionException {
		switch (part) {
		case BOOSTER:
			return new Booster();
		case PAYLOAD:
			return new Payload();
		case STAGE:
			return new Stage();
		default:
			throw new ProductionException("This factory can't produce {" + part + "}");
		}
	}
	
}
