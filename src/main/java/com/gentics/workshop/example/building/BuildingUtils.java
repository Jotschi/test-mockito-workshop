package com.gentics.workshop.example.building;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.gentics.workshop.example.rocket.PartType;

public final class BuildingUtils {

	public static void buildPart(PartType part) throws IOException {
		int code = doComplicatedBuildStuff("ModeOneBravo");
		if (code == 42) {
			System.out.println("Build part of type {" + part + "}");
		} else {
			System.out.println("Unknown code {" + code + "}");
		}
	}

	private static int doComplicatedBuildStuff(String mode) throws IOException {
		URL url = new URL(
				"https://raw.githubusercontent.com/Jotschi/test-mockito-workshop/master/src/main/resources/com/gentics/workshop/example/launchkyA");
		IOUtils.toString(url.openConnection().getInputStream());
		return 42;
	}

}
