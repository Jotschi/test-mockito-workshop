package com.gentics.workshop.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.doAnswer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.gentics.workshop.example.building.LaunchControlCenter;
import com.gentics.workshop.example.building.LaunchKey;
import com.gentics.workshop.example.building.LaunchPad;
import com.gentics.workshop.example.building.Project;
import com.gentics.workshop.example.rocket.Booster;
import com.gentics.workshop.example.rocket.Rocket;
import com.gentics.workshop.example.rocket.RocketException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LaunchControlCenter.class })
public class SimpleMockingTest {

	@Test
	public void testRocketConstruction() {
		// The rocket constructor needs a project. Instead of creating the project ourself we just create a mock.
		Project project = mock(Project.class);

		// We specify that the getBoosterCount method should return 10.
		when(project.getBoosterCount()).thenReturn(10);

		// Create rocket
		new Rocket(project);

		// Lets now verify that the constructor of the rocket did invoke getBoosterCount on the mock.
		verify(project, times(1)).getBoosterCount();

	}

	@Test(expected = RocketException.class)
	public void testRocketDestruction() throws RocketException {
		// The rocket constructor needs a project. Instead of creating the project ourself we just create a mock.
		Project project = mock(Project.class);

		// Create rocket and destroy it
		Rocket rocket = new Rocket(project);
		rocket.destruct();
		rocket.check();
	}

	@Test
	public void testRocketCreationWithSpy() {

		// Create a new project
		Project project = new Project();
		Booster booster = mock(Booster.class);
		project.addBooster(booster);

		// Lets wrap the project with the spy call
		Project spy = spy(project);
		when(spy.getPayLoadCount()).thenReturn(10);

		System.out.println("Boosters: " + spy.getBoosterCount());
		System.out.println("Payloads: " + spy.getPayLoadCount());
		assertEquals("Payloads count should be 10 since we mocked that call", 10, spy.getPayLoadCount());
		assertEquals("Boosters count should be 1 since we just added one booster to the project", 1, spy.getBoosterCount());
	}

	@Test
	public void testLaunchControlCenter() throws Exception {

		// 1. Mock some stuff we need later on
		Rocket rocket = mock(Rocket.class);
		LaunchPad pad = mock(LaunchPad.class);
		when(pad.getRocket()).thenReturn(rocket);

		// 2. Prepare the launch control center
		LaunchControlCenter control = new LaunchControlCenter();
		LaunchControlCenter spy = PowerMockito.spy(control);
		spy.setPad(pad);

		final String FAKED_KEY = "secret";

		// Mock the launch keys
		doAnswer(invocation -> {
			return new LaunchKey(FAKED_KEY);
		}).when(spy, "getLaunchKeyB");
		doAnswer(invocation -> {
			return new LaunchKey(FAKED_KEY);
		}).when(spy, "getLaunchKeyA");

		// Mock away the checkpad call. We can launch rockets without them standing on the pad..
		PowerMockito.doNothing().when(spy, "checkPad", pad);
		// Our rocket does not need to be fueled.. omit the check
		PowerMockito.doNothing().when(spy, "checkRocket", rocket);
		// Speedup the test
		PowerMockito.doNothing().when(spy, "waitOneSecond");
		Future<Rocket> launchFuture = spy.launch();
		assertNotNull(launchFuture);

		assertNotNull("Rocket should not be null", launchFuture.get());
	}

	@Test
	public void testLaunchRocketWithoutFuel() throws RocketException, InterruptedException, ExecutionException {
		Project project = mock(Project.class);
		Rocket rocket = new Rocket(project);
		Rocket spy = spy(rocket);

		// Mock away the fuel indicator
		when(spy.getFuel()).thenReturn(100);
		assertEquals("Fuel should always be 100", 100, spy.getFuel());

		// Launch the rocket
		Future<Rocket> future = spy.launch();
		assertNotNull(future);

		// Now let the rocket fly with its imaginary fuel
		Thread.sleep(4000);

		// Cut the fuel
		when(spy.getFuel()).thenReturn(0);

		assertNotNull("Rocket should not be null", future.get());
		future.get().check();
	}

}
