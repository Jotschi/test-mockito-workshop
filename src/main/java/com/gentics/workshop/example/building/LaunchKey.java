package com.gentics.workshop.example.building;

import org.apache.commons.lang3.StringUtils;

public class LaunchKey implements Comparable<LaunchKey> {

	private String code;

	public LaunchKey(String code) {
		this.code = code;
	}

	@Override
	public int compareTo(LaunchKey o) {
		String cypherA = o.computeCypher();
		String cypherB = this.computeCypher();
		return cypherA.compareTo(cypherB);
	}

	/**
	 * Super secret cypher computing algorithm.
	 * 
	 * @return
	 */
	private String computeCypher() {

		char[] keys = { 'a', 'e', 'i', 'o', 'u' };
		StringBuilder builder = new StringBuilder();
		for (char key : keys) {
			builder.append(key + ":" + StringUtils.countMatches(code, String.valueOf(key)));
		}
		String cypher = builder.toString();
		return cypher;
	}

}
