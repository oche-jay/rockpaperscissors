import exceptions.CycleConditionException;
import exceptions.RuleNotFoundException;
import exceptions.UnexpectedGameModeException;
import lombok.Getter;
import lombok.Setter;
import model.Beats;
import model.Game;
import model.Player;
import model.Rule;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

@Getter
@Setter
public class GamePlay {
    
    protected int gameMode;
    protected Game game;

    protected String WELCOME_MSG = "Welcome to Rock-Paper-Scissors";
    protected String CHOOSE_GAME_MODE = "Please choose Game Mode";
    protected String UNEXPECTED_GAME_MODE = "Unexpected Input: Please enter 1 or 2";
    protected String MODE1 = "Enter 1 for Human Vs Computer Mode";
    protected String MODE2 = "Enter 2 for Computer Vs Computer Mode";
    protected String MODE1_GAMEPLAY_STRING = "Human Vs Computer Mode\n";
    protected String MODE2_GAMEPLAY_STRING = "Computer Vs Computer Mode\n";
    protected String MODE1_PLAYER1_NAME = "You";
    protected String MODE1_PLAYER2_NAME = "Computer";
    protected String MODE2_PLAYER1_NAME = "AI Player 1";
    protected String MODE2_PLAYER2_NAME = "AI Player 2";
    protected String MODE1_CHOICE = "Please choose a player\n";
    protected String MODE2_CHOICE = "\nComputer making choice\n";
    protected String CONTINUE_MSG = "Would you like to play again? Y(es) / N(o)?";
    protected String UNEXPECTED_MODE = "Unexpected mode";
    protected String TIE = "We have a tie!\n";
    protected String CHOOSE = "chose";
    protected String ENTER = "Enter";
    protected String FOR = "for";
    protected String YES_OR_NO = "Please choose Yes or No";
    protected String MODE1_WINS = "win!\n";
    protected String WINS = "wins!\n";


    public void play(int mode) throws CycleConditionException, RuleNotFoundException {
        String player1name, player2name, gamePlayMode, choice, win;

        switch (mode) {
            case 1:
                gamePlayMode = MODE1_GAMEPLAY_STRING;
                player1name = MODE1_PLAYER1_NAME;
                player2name = MODE1_PLAYER2_NAME;
                choice = MODE1_CHOICE;
                win = MODE1_WINS;
                break;
            case 2:
                gamePlayMode = MODE2_GAMEPLAY_STRING;
                player1name = MODE2_PLAYER1_NAME;
                player2name = MODE2_PLAYER2_NAME;
                choice = MODE2_CHOICE;
                win = WINS;
                break;
            default:
                System.out.println(UNEXPECTED_MODE);
                return;
        }

        int randomNum1 = new Random().nextInt(game.getNo_of_players() - 1);
        int randomNum2 = new Random().nextInt(game.getNo_of_players() - 1);

        Player player1 = game.getPlayers().get(randomNum1);
        Player player2 = game.getPlayers().get(randomNum2);

        printMessageWithPauseForEffect(gamePlayMode, 500);
        printPlayers();
        System.out.println(choice);

        if (gameMode == 1) {
            player1 = game.getPlayers().get(getHumanChoice());
        }

        printMessageWithPauseForEffect("*", 500);
        printMessageWithPauseForEffect("  *", 500);
        printMessageWithPauseForEffect("    *", 500);

        System.out.printf("%s %s: %s\n", player1name, CHOOSE, player1);
        System.out.printf("%s %s: %s\n\n", player2name, CHOOSE, player2);

        Player winner = checkWinner(player1, player2);

        if (winner != null) {
            if (winner.equals(player1)) {
                System.out.printf("%s %s\n", player1name, win);
            } else if (winner.equals(player2)) {
                System.out.printf("%s %s\n", player2name, WINS);
            }
        }
    }

    private void printMessageWithPauseForEffect(String message, int pause) {
        System.out.println(message);
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getHumanChoice() {
        Scanner sc;
        sc = new Scanner(System.in);
        int humanChoice = sc.nextInt();

        if (humanChoice > game.getPlayers().size() - 1 || humanChoice < 0) {
            System.out.println("Invalid Choice");
            printPlayers();
            return getHumanChoice();
        } else {
            return humanChoice;
        }

    }

    public void printPlayers() {
        for (int i = 0; i < game.getPlayers().size(); i++) {
            System.out.printf("%s %d %s %s\n", ENTER, i, FOR, game.getPlayers().get(i));
        }
    }

    public int getGameMode() throws UnexpectedGameModeException {
        int mode;

        System.out.println(CHOOSE_GAME_MODE);
        System.out.println(MODE1);
        System.out.println(MODE2);

        try {

            mode = new Scanner(System.in).nextInt();

        } catch (InputMismatchException e) {
            System.err.println(UNEXPECTED_GAME_MODE);
            mode = -1;
        }

        if (mode != 1 && mode != 2) {

            System.err.println(UNEXPECTED_GAME_MODE);
            mode = -1;

        }
        if (mode != -1) {
            gameMode = mode;
            return gameMode;
        } else {
            return getGameMode();
        }

    }

    public Player checkWinner(Player p1, Player p2) throws CycleConditionException, RuleNotFoundException {
        if (p1.equals(p2)) {
            System.out.println(TIE);
        } else {
            Rule player1Rule = game.getRules().stream()
                    .filter(k -> k.getPlayer().equals(p1))
                    .findFirst().orElse(null);

            Rule player2Rule = game.getRules().stream()
                    .filter(k -> k.getPlayer().equals(p2))
                    .findFirst().orElse(null);

            checkForNull(player1Rule, p1);
            checkForNull(player2Rule, p2);

            boolean p1wins = player1Rule.getBeats().stream().
                    map(Beats::getPlayer)
                    .anyMatch(p2::equals);

            boolean p2wins = player2Rule.getBeats().stream()
                    .map(Beats::getPlayer)
                    .anyMatch(p1::equals);

            if (p1wins && !p2wins) {
                player1Rule.getBeats().stream()
                        .filter(b -> b.getPlayer().equals(p2))
                        .findFirst()
                        .ifPresent(b -> System.out.println(p1 + " " + b));

                return p1;
            } else if (p2wins && !p1wins) {
                player2Rule.getBeats().stream()
                        .filter(beat -> beat.getPlayer().equals(p1))
                        .findFirst()
                        .ifPresent(beat -> System.out.println(p2 + " " + beat));

                return p2;
            } else {
//               Should never get here, do we have a cycle?"
                throw new CycleConditionException(p1, p2);
            }


        }
        return null;
    }


    public void checkForNull(Rule rule, Player player) throws RuleNotFoundException {
        if (rule == null) {
            throw new RuleNotFoundException("Please check config - No Rule defined for " + player);
        }
    }

}
