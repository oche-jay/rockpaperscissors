import exceptions.CycleConditionException;
import exceptions.RuleNotFoundException;
import model.Game;
import model.Player;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


public class GameTest {

    @Test(dataProvider = "wrongGameModes")
    public void negativeTestGetGameMode(String input) throws Exception {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        assertThatExceptionOfType(Exception.class).isThrownBy(() -> {
            new GamePlay().getGameMode();
        });
    }

    @Test(dataProvider = "correctGameModes")
    public void positiveTestGetGameMode(String input) throws Exception {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        final int[] mode = new int[1];
        assertThatCode(() -> {
            mode[0] = new GamePlay().getGameMode();
        }).doesNotThrowAnyException();

        assertThat(mode[0]).isEqualTo(Integer.parseInt(input));
    }


    @Test(dataProvider = "defaultConfigWinner")
    public void checkWinnerTestforDefault3PlayerConfig(Player p1, Player p2, Player expectedWinner) throws Exception {
        String configFile = "game-config.yaml";
        assertWinner(p1, p2, expectedWinner, configFile);
    }

    @Test(dataProvider = "fivePlayerConfigWinner")
    public void checkWinnerTestforDefault5PlayerConfig(Player p1, Player p2, Player expectedWinner) throws Exception {
        String configFile = "5player-game-config.yaml";
        assertWinner(p1, p2, expectedWinner, configFile);
    }

    private void assertWinner(Player p1, Player p2, Player expectedWinner, String configFile) throws CycleConditionException, RuleNotFoundException {
        Game game = Util.loadGameFromConfig(configFile);
        GamePlay gamePlay = new GamePlay();
        gamePlay.setGame(game);

        assertThat(gamePlay.checkWinner(p1, p2))
                .as("Winner according to rules should be: ", expectedWinner)
                .isEqualTo(expectedWinner);
    }

    //Data Providers
    @DataProvider
    public Object[][] defaultConfigWinner() {

        Player rock = new Player("rock");
        Player paper = new Player("paper");
        Player scissors = new Player("scissors");

        return new Object[][]{
                {rock, paper, rock},
                {paper, rock, paper},
                {scissors, paper, scissors},
                {paper, scissors, scissors},
                {rock, scissors, rock},
                {scissors, rock, rock},
                {rock, rock, null},
                {paper, paper, null},
                {scissors, scissors, null}
        };
    }

    @DataProvider
    public Object[][] fivePlayerConfigWinner() {

        Player rock = new Player("rock");
        Player paper = new Player("paper");
        Player scissors = new Player("scissors");
        Player lizard = new Player("lizard");
        Player spock = new Player("spock");

        return new Object[][]{
                {lizard, paper, lizard},
                {lizard, rock, rock},
                {lizard, scissors, scissors},
                {lizard, spock, lizard},
                {lizard, lizard, null},
                {paper, lizard, lizard},
                {paper, paper, null},
                {paper, rock, paper},
                {paper, scissors, scissors},
                {paper, spock, paper},
                {rock, lizard, rock},
                {rock, paper, paper},
                {rock, rock, null},
                {rock, scissors, rock},
                {rock, spock, spock},
                {scissors, lizard, scissors},
                {scissors, paper, scissors},
                {scissors, rock, rock},
                {scissors, scissors, null},
                {spock, lizard, lizard},
                {spock, paper,  paper},
                {spock, scissors, spock},
                {spock, rock, spock},
                {spock, spock , null}
        };
    }

    @DataProvider
    public Object[][] wrongGameModes() {
        return new Object[][]{
                {"0"},
                {"3"},
                {"5"},
                {"9999"},
                {"potato"},
                {"-5"},
                {"-9999"}
        };
    }

    @DataProvider
    public Object[][] correctGameModes() {
        return new Object[][]{
                {"1"},
                {"2"},
        };
    }
}
