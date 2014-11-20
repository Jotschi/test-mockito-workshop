package com.gentics.workshop.example;

import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gentics.workshop.example.building.BuildingUtils;
import com.gentics.workshop.example.rocket.PartType;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ BuildingUtils.class })
public class StaticMockingTest {

	@Test
	public void testBuildPart() throws Exception {
		// Spy upon our static utility class
		spy(BuildingUtils.class);

		// We mock away the complicatedBuildStuff
		doAnswer(invocation -> {
			return 42;
		}).when(BuildingUtils.class, "doComplicatedBuildStuff", Mockito.anyString());
		BuildingUtils.buildPart(PartType.BOOSTER);

	}

}
