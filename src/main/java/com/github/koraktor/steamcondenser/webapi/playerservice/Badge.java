package com.github.koraktor.steamcondenser.webapi.playerservice;

import java.util.Date;

public class Badge {
	private final long badgeId, scarcity;
	private final int level, xp;
	private final Date completionTime;
	
	public Badge(long badgeId, int level, Date completionTime, int xp, long scarcity) {
		this.badgeId = badgeId;
		this.level = level;
		this.completionTime = completionTime;
		this.xp = xp;
		this.scarcity = scarcity;
	}


	public long getBadgeId() {
		return badgeId;
	}

	public long getScarcity() {
		return scarcity;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public Date getCompletionTime() {
		return completionTime;
	}
}
