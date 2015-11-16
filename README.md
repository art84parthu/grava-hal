# grava-hal

Design choices :
 - Made with Spark web framework for Java for simplicity 
 - in-memory HSQL database (simple for dev purpose)
 - Spring for dependency injection

Restriction/Assumption:
 - will have 2 players always since this is a board game.

Current Implementation (scope):
 - Allows 2 players to play the board game in the same browser session. 
 - Default player/game names are automatically given.
 - System chooses which player will start the game.
 
Future goals:
 - allow player discovery.
 - allow any 2 players who are online to connect and start playing.
 - save games and continue from saved spot. 
 - allow users to give names for themselves and the games.
 - full fledged database. 
 
Known Issue(s) :
 - Need to fix the autogenerate primary key id for HSQL.
 
Unit Testing:
 - JUnits covered. 
 - Integration testing pending. 
