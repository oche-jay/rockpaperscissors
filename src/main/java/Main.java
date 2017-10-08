import exceptions.CycleConditionException;
import exceptions.RuleNotFoundException;
import exceptions.UnexpectedGameModeException;
import util.Util;

import java.util.Scanner;

public class Main {


    public static String DEFAULT_CONFIG = "game-config.yaml";

    public static void main(String[] args) throws CycleConditionException, RuleNotFoundException, UnexpectedGameModeException {
        GamePlay gamePlay = new GamePlay();

        System.out.println(gamePlay.WELCOME_MSG);
        while (true) {
            gamePlay.game = Util.loadGameFromConfig(DEFAULT_CONFIG);
            gamePlay.play(gamePlay.getGameMode());
            wouldYouLikeToContinue(gamePlay);
        }
    }

    public static void wouldYouLikeToContinue(GamePlay gamePlay) {
        Scanner sc;
        sc = new Scanner(System.in);
        System.out.println(gamePlay.CONTINUE_MSG);
        String s = sc.nextLine();
        if (s.equalsIgnoreCase("n") || s.equalsIgnoreCase("no")) {
            System.exit(0);
        } else if (s.equalsIgnoreCase("y") || s.equalsIgnoreCase("yes")) {
            return;
        } else {
            System.err.println(gamePlay.YES_OR_NO);
            wouldYouLikeToContinue(gamePlay);
        }
    }


}
