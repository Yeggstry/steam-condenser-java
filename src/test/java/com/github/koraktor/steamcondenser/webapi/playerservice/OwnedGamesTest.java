package com.github.koraktor.steamcondenser.webapi.playerservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGame;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGameWithAppInfo;
import com.github.koraktor.steamcondenser.webapi.playerservice.OwnedGames;

/* 
 * Test class for the check of whether the player owned games include app info
 */
public class OwnedGamesTest {
    private OwnedGames ownedGames;

    @Test
    public void testDoPlayerOwnedGamesIncludeAppInfoNullOwnedGames() {
        ownedGames = new OwnedGames(0, null);
        assertFalse(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
    }

    @Test
    public void testDoPlayerOwnedGamesIncludeAppInfoEmptyOwnedGames() {
        ownedGames = new OwnedGames(0, new ArrayList<OwnedGame>());
        assertFalse(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
    }

    @Test
    public void testDoPlayerOwnedGamesIncludeAppInfoOwnedGames() {
        OwnedGame ownedGame = new OwnedGame(32460, 0, 0);
        List<OwnedGame> ownedGamesList = new ArrayList<OwnedGame>();
        ownedGamesList.add(ownedGame);

        ownedGames = new OwnedGames(0, ownedGamesList);
        assertFalse(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
    }

    @Test
    public void testDoPlayerOwnedGamesIncludeAppInfoOwnedGamesWithAppInfo() {
        OwnedGameWithAppInfo ownedGame = new OwnedGameWithAppInfo(32460, 0, 0, "Monkey Island 2: Special Edition", "3ff954e2286123d0d4cf91dfca57e62a0b7248a9", "520648565e99a6a8afa4f92a55dc4a3d39400768");
        List<OwnedGame> ownedGamesList = new ArrayList<OwnedGame>();
        ownedGamesList.add(ownedGame);

        ownedGames = new OwnedGames(0, ownedGamesList);
        assertTrue(ownedGames.doesPlayerOwnedGamesIncludeAppInfo());
    }
}
