package com.github.koraktor.steamcondenser.steam.community.playerservice;

import java.util.List;

public class PlayerBadgeDetails {
	private final long currentXp, xpNeededToLevelUp, xpNeededForCurrentLevel;
	private final int level;
	private List<Badge> badges;
	
	public PlayerBadgeDetails(long currentXp, int level, long xpNeededToLevelUp, long xpNeededForCurrentLevel) {
		this.currentXp = currentXp;
		this.level = level;
		this.xpNeededToLevelUp = xpNeededToLevelUp;
		this.xpNeededForCurrentLevel = xpNeededForCurrentLevel;
	}

	public long getCurrentXp() {
		return currentXp;
	}

	public int getLevel() {
		return level;
	}

	public long getXpNeededToLevelUp() {
		return xpNeededToLevelUp;
	}

	public long getXpNeededForCurrentLevel() {
		return xpNeededForCurrentLevel;
	}

	public List<Badge> getBadges() {
		return badges;
	}

	public void setBadges(List<Badge> badges) {
		this.badges = badges;
	}
}
