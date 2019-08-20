# Concepts
## Player (entity)
Players play one card each per round. <br/>
Each Player has a playerId and a username. The username might be changed over time. <br/>
Each player gets 13 cards assigned on each round.

## PlayerId (value object)
PlayerIds are unique value objects.

## Card (value object)
There are 13 cards per card color. Card colors are Hearts (red), Spades (black), Clubs (black) and Diamonds (red). <br/>
Each card has a rank, which is one of: Ace, King, Queen, Jack or numbers 10 down to 2.  

## Trick (entity)
A trick consists of one card per player. The player that played the highest card on the tricks initial card color wins the trick.
The order in which the players play one card remains the same throughout the whole game.

## Round (entity)
A round consists of 13 tricks.

## Game (entity)
The game defines the order of players.
Players join a game.

## RoundOrchestrationService (domain service)
Domain service to orchestrate the cards played by the different players. <br/>
