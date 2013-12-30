package com.github.koraktor.steamcondenser.webapi.playerservice;

import java.util.List;

public class OwnedGames {
    private final int gameCount;
    private final List<? extends OwnedGame> playerOwnedGames;

    public OwnedGames(int gameCount, List<? extends OwnedGame> playerOwnedGames) {
        this.gameCount = gameCount;
        this.playerOwnedGames = playerOwnedGames;
    }

    public int getGameCount() {
        return gameCount;
    }

    @SuppressWarnings("unchecked")
    public List<OwnedGame> getPlayerOwnedGames() {
        return (List<OwnedGame>) playerOwnedGames;
    }

    @SuppressWarnings("unchecked")
    public List<OwnedGameWithAppInfo> getPlayerOwnedGamesWithAppInfo() {
        return (List<OwnedGameWithAppInfo>) playerOwnedGames;
    }

    public boolean doesPlayerOwnedGamesIncludeAppInfo() {
        return playerOwnedGames != null && !playerOwnedGames.isEmpty() && (playerOwnedGames.get(0) instanceof OwnedGameWithAppInfo);
    }
}
