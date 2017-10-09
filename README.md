# rockpaperscissors [![Build Status](https://travis-ci.org/oche-jay/rockpaperscissors.svg?branch=master)](https://travis-ci.org/oche-jay/rockpaperscissors)

A simple, configurable Rock-Paper-Scissors Game.

To test, and run software from the command lines (Mac OSX or Linux assumed)

     git clone https://github.com/oche-jay/rockpaperscissors
     cd rockpaperscissors
     
     mvn test exec:java
     
The game is dependent on and can be easily extended/modified using the 
[game-config.yaml](src/main/resources/game-config.yaml) file located in src/main/resources. 
This is the default configuration, more complex game scenarios can be configured,
e.g. see [5player-game-config.yaml](src/main/resources/5player-game-config.yaml).
A future extension of this codebase can allow users to create, validate and run
thier own configurations.

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
(no_of_players) in the game MUST be defined as an integer value, and 
then each of these players mustbe listed by name. Next, the rules which 
determine how each player can beat other players are specified as shown 
above, with an optional "verb" which is describes the specific term
to use when one player 'beats' another e.g. paper 'covers' rock.

## Tests

The tests are located in [src/test/java](/src/test/java/ConfigTest.java) folder.

[ConfigTest.java](/src/test/java/ConfigTest.java) validates the DSL in the config file 
and makes sure certain rules are followed and constraints are met. Depending on the 
specific constraints of the game, tests may be added or removed here.
Most of the Game's validation is done here, if the config file is properly set up
and passes the validation, then there is less likelihood of unexpected bugs in the Game itself.

[GameTest.java](/src/test/java/ConfigTest.java) ensures that based on the default configurations, the programme is working as expected.

### Summary of Testing Approach

A Test-Driven Development approach was taken in implementing this project. Before I proceeded with
any developement work I created the DSL and wrote the tests to verify and test it, including error
conditions and edge cases. By taking this DSL-based approach, we are not only able to have an extensible, configurable
implementation of Game that can easily be tested, but we are able to seperate 'business rules/concerns' from the
actual functionality of the Game itself.

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





