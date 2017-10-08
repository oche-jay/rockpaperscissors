# rockpaperscissors

A simple, configurable Rock-Paper-Scissors Game.

To test, and run software from the command lines (Mac OSX or Linux assumed)

     git clone https://github.com/oche-jay/rockpaperscissors
     cd rockpaperscissors
     
     mvn test exec:java
     
The game is dependent on and can be easily extended/modified using the 
game-config.yaml file located in src/main/resources. This is the 
default configuration more complex game scenarios can be configured,
e.g. see 5player-game-config.yaml.

     no_of_players: 3
     players:
       - rock
       - paper
       - scissors
    rules:
      -
        player: rock
        beats:
          - player: scissors
            verb: "smashes"
      -
        player: paper
        beats:
          - player: rock
            verb: "covers"
      -
         player: scissors
         beats:
           - player: paper
             verb: "cuts"



This is a simple, easily extensible DSL based on the YAML format.
Some simple rules must be followed though, the number of players 
(no_of_players) in the game MUST be defined
as an integer value, and then each of these players must
be listed by name. Next, the rules which determine how each player can 
beat other
players are specified as shown above, with an optional "verb"
which describes.

## Tests

The tests are located in src/test/java

ConfigTest.java validates the config file and makes sure certain rules, constraints are followed.

GameTest.java ensures that based on the default configurations, the programme is working as expected.

