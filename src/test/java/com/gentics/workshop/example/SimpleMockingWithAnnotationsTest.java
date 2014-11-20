package com.gentics.workshop.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.gentics.workshop.example.building.Project;
import com.gentics.workshop.example.rocket.Booster;
import com.gentics.workshop.example.rocket.Rocket;
import com.gentics.workshop.example.rocket.RocketException;

@RunWith(MockitoJUnitRunner.class)
public class SimpleMockingWithAnnotationsTest {

	@Mock
	private Project project;

	@Mock
	private Booster booster;

	@Spy
	private Project spy = new Project();

	@Test
	public void testRocketConstruction() {

		// We specify that the getBoosterCount method should return 10.
		when(project.getBoosterCount()).thenReturn(10);

		// Create rocket
		new Rocket(project);

		// Lets now verify that the constructor of the rocket did invoke getBoosterCount on the mock.
		verify(project, times(1)).getBoosterCount();

	}

	@Test(expected = RocketException.class)
	public void testRocketDestruction() throws RocketException {

		// Create rocket and destroy it
		Rocket rocket = new Rocket(project);
		rocket.destruct();
		rocket.check();
	}

	@Test
	public void testRocketCreationWithSpy() {

		spy.addBooster(booster);

		when(spy.getPayLoadCount()).thenReturn(10);

		System.out.println("Boosters: " + spy.getBoosterCount());
		System.out.println("Payloads: " + spy.getPayLoadCount());
		assertEquals("Payloads count should be 10 since we mocked that call", 10, spy.getPayLoadCount());
		assertEquals("Boosters count should be 1 since we just added one booster to the project", 1, spy.getBoosterCount());
	}

}
