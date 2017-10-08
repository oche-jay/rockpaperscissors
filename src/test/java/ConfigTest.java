import model.Beats;
import model.Game;
import model.Player;
import model.Rule;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import util.Util;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigTest {

    Game game;
    String DEFAULT_CONFIG =  "game-config.yaml";
    String FIVE_PLAYER_CONFIG =  "5player-game-config.yaml";

    @DataProvider
    public Object[][] gameConfig(){
        return new Object[][]{
                {Util.loadGameFromConfig(DEFAULT_CONFIG)},
                {Util.loadGameFromConfig(FIVE_PLAYER_CONFIG)}
        };
    }

    /**
     * Quick checks on numbers and sizes from config file
     *  i.e.
     *  Config File must list at 3 players
     *  Number of Players must always be ODD
     *  Number of Players must be declared and must be equal to the number of
     *  players configured
     *  There must be one Rule set for each player
     * @param game
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatWeHaveCorrectNumbersofItemsInConfig(Game game){
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            softly.assertThat(game.getNo_of_players())
                    .as("no_of_players must be 3 or more")
                    .isGreaterThanOrEqualTo(3);

            softly.assertThat(game.getNo_of_players() % 2)
                    .as("no_of_players must be an odd number")
                    .isNotEqualTo(0);

            softly.assertThat(game.getNo_of_players())
                    .as("no_of_players must match size of list of players")
                    .isEqualTo(game.getPlayers().size());

            softly.assertThat(game.getNo_of_players())
                    .as("no_of_players must be equal to number of rules")
                    .isEqualTo(game.getRules().size());
         }
    }

    /**
     * No Player should be able to beat itself i.e Paper cannot beat Paper
     * @param game
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatAnItemCannotBeatItself(Game game){
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            for (Rule rule : game.getRules()) {
               softly.assertThat(rule.getBeats())
                       .extracting(Beats::getPlayer)
                       .as("Rule must not allow Player %s to beat itself", rule.getPlayer().getName())
                       .doesNotContain(rule.getPlayer());
            }
        }
    }

    /**
     *  Under normal rules, each item should only be able to beat (no_of_players - 1) / 2 other players.
     *  There are variations to the game that violate this constraint, but not in this MVP
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatAnItemCanOnlyBeatNOthers(Game game){
        int expectedNoOfBeats = (game.getNo_of_players() - 1) / 2;

        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            for (Rule rule : game.getRules()) {
                softly.assertThat(rule.getBeats().size())
                        .as("Rule must allow Player %s to beat exactly %d player(s)", rule.getPlayer().getName(), expectedNoOfBeats )
                        .isEqualTo(expectedNoOfBeats);
            }
        }

    }


    /**
     * It must be possible to beat each player at least once, so we don't have any  'God' players
     * Furthermore, under default rules every player must be beatable (no_of_players - 1) / 2 times
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatAnItemCanBeBeaten1orNTimes(Game game){
        long expectedNoOfBeats = (game.getNo_of_players() - 1) / 2;
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {

            for (Player p : game.getPlayers()) {
                long actualNooFBeats = game.getRules().stream()
                        .filter(k -> k.getBeats().stream()
                                .anyMatch(w -> w.getPlayer().equals(p)))
                        .count();

                softly.assertThat(actualNooFBeats)
                        .as("Rules must allow Player %s to be beaten exactly %d time(s)", p.getName(), expectedNoOfBeats)
                        .isEqualTo(expectedNoOfBeats);


                softly.assertThat(actualNooFBeats)
                        .as("Unbeatable player: Rules should allow Player %s to be beatable at least once", p.getName())
                        .isGreaterThanOrEqualTo(1);
            }
        }
    }

    /**
     * Every Player defined in the Config must have exactly one set of Rules defined for it
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatEachPlayerHasAnEquivalentRule(Game game) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {

            for (Player p : game.getPlayers()) {
                long actualCount = game.getRules().stream().filter(k -> k.getPlayer().equals(p)).count();
                softly.assertThat(actualCount)
                        .as("Player %s should have exactly 1 RuleSet", p)
                        .isEqualTo(1);
            }
        }
    }


    /**
     *  There should be no cycles i.e. if Paper beats Rock, then Rock MUST NOT be able to beat Paper
     */
    @Test(dataProvider = "gameConfig")
    public void assertThatThereAreNoCycles(Game game) {
        try (AutoCloseableSoftAssertions softly = new AutoCloseableSoftAssertions()) {
            for (Rule rule : game.getRules()) {
//                Player currentPlayer = rule.getPlayer();
                List<Player> playersICanBeat = rule.getBeats().stream().map(k -> k.getPlayer()).collect(Collectors.toList());

                for (Player eachPlayerICanBeat : playersICanBeat ){
                    List playersYouCanBeat = game.getRules().stream()
                            .filter($rule -> $rule.getPlayer().equals(eachPlayerICanBeat))
                            .filter($rxle -> $rxle.getBeats().stream()
                                    .anyMatch($beats -> $beats.getPlayer().equals(rule.getPlayer())))
                            .collect(Collectors.toList());

                    //I can beat you, but you can also beat me.
                    String msg = "cycle detected: " + rule.getPlayer() + " beats " + eachPlayerICanBeat +
                            " but also: " + playersYouCanBeat.toString();

                    softly.assertThat(playersYouCanBeat).as(msg).isEmpty();

                }

            }
        }

    }

}
