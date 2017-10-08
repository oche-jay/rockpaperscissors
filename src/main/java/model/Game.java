package model;

import lombok.Data;

import java.util.List;

@Data
public class Game {

    private int no_of_players;

    private List<Player> players;
    private List<Rule> rules;
}

