# rockpaperscissors [![Build Status](https://travis-ci.org/oche-jay/rockpaperscissors.svg?branch=master)](https://travis-ci.org/oche-jay/rockpaperscissors)

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

The tests are located in src/test/java folder.

[ConfigTest.java](/src/test/java/ConfigTest.java) validates the [config file] DSL and makes sure certain rules, constraints are followed.
Most of the Game's validation is done here, if the config file is properly set up
and passes the validation, then there is less likelihood of unexpected bugs in the Game itself.

[GameTest.java](/src/test/java/ConfigTest.java) ensures that based on the default configurations, the programme is working as expected.

### Acceptance Tests

#### Title: Waste an Hour Having Fun              
                                                 
As a frequent games player,                      
I'd like to play rock, paper, scissors          
so that I can spend an hour of my day having fun 
                                                 
Acceptance Criteria                             

  - [x] Can I play Player vs Computer?           

  - [x] Can I play Computer vs Computer?              

  - [x] Can I play a different game each time?      
  
  
Note: the acceptance criteria should be validated manually by a human, especially as 
the users of the Game will, be invariably, human.





