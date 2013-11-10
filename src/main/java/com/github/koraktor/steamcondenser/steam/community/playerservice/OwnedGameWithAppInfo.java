package com.github.koraktor.steamcondenser.steam.community.playerservice;

public class OwnedGameWithAppInfo extends OwnedGame {
    private final String name, iconUrl, logoUrl;
	
	public OwnedGameWithAppInfo(long appId, long playtime2Weeks, long playtimeForever, String name, String iconUrl, String logoUrl) {
		super(appId, playtime2Weeks, playtimeForever);
		this.name = name;
		this.iconUrl = iconUrl;
		this.logoUrl = logoUrl;
	}

	public String getName() {
		return name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getLogoUrl() {
		return logoUrl;
	}
}
