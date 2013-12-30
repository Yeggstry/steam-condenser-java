package com.github.koraktor.steamcondenser.webapi.playerservice;

public class OwnedGame {
    private final long appId, playtime2Weeks, playtimeForever;

    public OwnedGame(long appId, long playtime2Weeks, long playtimeForever) {
        this.appId = appId;
        this.playtime2Weeks = playtime2Weeks;
        this.playtimeForever = playtimeForever;
    }

    public long getAppId() {
        return appId;
    }

    public long getPlaytime2Weeks() {
        return playtime2Weeks;
    }

    public long getPlaytimeForever() {
        return playtimeForever;
    }
}
