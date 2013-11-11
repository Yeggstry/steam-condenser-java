package com.github.koraktor.steamcondenser.steam.community.playerservice;

import java.util.Date;

public class AppBadge extends Badge {
	private final long appId, borderColor, communityItemId;

	public AppBadge(long badgeId, int level, Date completionTime, int xp, long scarcity, long appId, long communityItemId, long borderColor) {
		super(badgeId, level, completionTime, xp, scarcity);

		this.appId = appId;
		this.communityItemId = communityItemId;
		this.borderColor = borderColor;
	}

	public long getAppId() {
		return appId;
	}

	public long getBorderColor() {
		return borderColor;
	}

	public long getCommunityItemId() {
		return communityItemId;
	}
}
