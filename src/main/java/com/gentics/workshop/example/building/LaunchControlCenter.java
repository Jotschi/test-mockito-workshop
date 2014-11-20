package com.gentics.workshop.example.building;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.IOUtils;

import com.gentics.workshop.example.SpacePort;
import com.gentics.workshop.example.rocket.Rocket;
import com.gentics.workshop.example.rocket.RocketException;

/**
 * The control center delegates commands to the rocket on the launchpad.
 * 
 * @author johannes2
 *
 */
public class LaunchControlCenter {

	private LaunchPad pad;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public Future<Rocket> launch() throws ControlException, RocketException, InterruptedException, IOException {
		LaunchKey keyA = getLaunchKeyA();
		LaunchKey keyB = getLaunchKeyB();
		if (keyA.compareTo(keyB) > 0) {
			throw new ControlException("The launchkeys were not accepted. Can't launch.");
		}

		return executor.submit(new Callable<Rocket>() {
			public Rocket call() throws Exception {
				checkPad(pad);
				Rocket rocket = pad.getRocket();
				checkRocket(rocket);
				pad.startWaterSuppressionSystem();
				for (int i = 3; i > 1; i--) {
					System.out.println("T-" + i);
					Thread.sleep(1000);
				}
				pad.launch();
				System.out.println("T-" + 1);
				System.out.println("LAUNCH! LAUNCH! LAUNCH!");
				System.out.println();
				pad.addDamage();
				System.out.println("Launching rocket");
				for (int i = 1; i < 30; i++) {
					System.out.println("T+" + i);
					rocket.check();
					waitOneSecond();
				}
				return rocket;
			}
		});
	}

	private void waitOneSecond() throws InterruptedException {
		Thread.sleep(1000);
	}

	private void checkRocket(Rocket rocket) throws ControlException {
		if (rocket.getFuel() < 100 && rocket.getSnacks() < 100) {
			throw new ControlException("The Rocket is not ready. Either fuel {" + rocket.getFuel() + " / 100} or snacks {" + rocket.getSnacks()
					+ " / 100} are not fully loaded.");
		}

	}

	private void checkPad(LaunchPad pad) throws ControlException {
		if (pad.getRocket() == null) {
			throw new ControlException("There is no rocket on pad {" + pad + "}. Did it silently explode?");
		}
		if (pad.getDamage() > 0) {
			throw new ControlException("The pad is damaged. Aborting procedure.");
		}
	}

	public void fuel() throws ControlException, InterruptedException {
		checkPad(pad);
		pad.getRocket().fuel();
		System.out.println("Fueling rocket");
	}

	public void prepare() throws ControlException, InterruptedException {
		checkPad(pad);
		pad.getRocket().prepare();
		System.out.println("Preparing rocket");
	}

	public void setPad(LaunchPad pad) {
		this.pad = pad;
	}

	private LaunchKey getLaunchKeyA() throws IOException {
		URL url = new URL("https://raw.githubusercontent.com/Jotschi/test-mockito-workshop/" + System.getProperty("SECRET_BRANCHNAME")
				+ "/src/main/resources/com/gentics/workshop/example/launchkeyA");
		String key = IOUtils.toString(url.openConnection().getInputStream());
		return new LaunchKey(key);
	}

	/**
	 * Return the second launchkey
	 * 
	 * @return
	 * @throws IOException
	 */
	private LaunchKey getLaunchKeyB() throws IOException {
		InputStream ins = SpacePort.class.getResourceAsStream("launchkeyB");
		String key = IOUtils.toString(ins);
		return new LaunchKey(key);
	}

}
