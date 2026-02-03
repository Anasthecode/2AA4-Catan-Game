# Catan Game - Complete Class Implementation Summary

This document provides a complete overview of all classes generated from the UML diagram.

## Total Files Created: 24

### Enumerations (4)
1. **TileType.java** - Tile types for the game board
2. **Resource.java** - Resource types players can collect
3. **RelativeNodeLocation.java** - Node positions on tiles
4. **RelativeEdgeLocation.java** - Edge positions on tiles

### Position Classes (3)
5. **AxialPosition.java** - Hexagonal coordinate system for tiles
6. **NodePosition.java** - Position system for nodes/intersections
7. **EdgePosition.java** - Position system for edges/paths

### Board Component Classes (3)
8. **Tile.java** - Hexagonal tile with resource type and number token
9. **Node.java** - Intersection where settlements/cities can be built
10. **Edge.java** - Path where roads can be built

### Structure Classes (5)
11. **Structure.java** - Abstract base class for all structures
12. **SettlementStructure.java** - Abstract base for settlement-type structures
13. **Settlement.java** - Basic settlement (1 VP)
14. **City.java** - Advanced city (2 VP)
15. **Road.java** - Road structure

### Game Management Classes (4)
16. **Board.java** - Game board managing all tiles, nodes, and edges
17. **Dice.java** - Dice rolling functionality
18. **Player.java** - Abstract base class for players
19. **ComputerPlayer.java** - AI player implementation

### Action Classes (6)
20. **Action.java** - Interface for all game actions
21. **BuildRoad.java** - Action to build a road
22. **BuildSettlement.java** - Action to build a settlement
23. **BuildCity.java** - Action to build a city
24. **GenerateResources.java** - Action to distribute resources
25. **EndTurn.java** - Action to end a turn

### Main Classes (2)
26. **Game.java** - Main game controller
27. **Demonstrator.java** - Demo/test program

## Class Details

### 1. TileType (Enumeration)
```
Values: FOREST, PASTURE, FIELD, HILLS, MOUNTAIN, DESERT
Purpose: Defines the six types of terrain tiles in Catan
```

### 2. Resource (Enumeration)
```
Values: WOOD, SHEEP, WHEAT, BRICK, ORE
Purpose: The five resource types players collect
```

### 3. RelativeNodeLocation (Enumeration)
```
Values: NORTH, SOUTH
Purpose: Relative position of nodes on a tile
```

### 4. RelativeEdgeLocation (Enumeration)
```
Values: WEST, NORTHWEST, NORTHEAST
Purpose: Relative position of edges on a tile
```

### 5. AxialPosition
```
Fields:
  - int q (q coordinate)
  - int r (r coordinate)

Methods:
  - AxialPosition(int q, int r)
  - int getQ()
  - int getR()
  - boolean equals(Object obj)
  - int hashCode()

Purpose: Represents position in hexagonal coordinate system
```

### 6. NodePosition
```
Fields:
  - int q
  - int r
  - RelativeNodeLocation location

Methods:
  - NodePosition(int q, int r, RelativeNodeLocation location)
  - int getQ()
  - int getR()
  - RelativeNodeLocation getRelativeLocation()
  - boolean equals(Object obj)
  - int hashCode()

Purpose: Unique identifier for node positions on board
```

### 7. EdgePosition
```
Fields:
  - int q
  - int r
  - RelativeEdgeLocation location

Methods:
  - EdgePosition(int q, int r, RelativeEdgeLocation location)
  - int getQ()
  - int getR()
  - RelativeEdgeLocation getRelativeLocation()
  - boolean equals(Object obj)
  - int hashCode()

Purpose: Unique identifier for edge positions on board
```

### 8. Tile
```
Fields:
  - AxialPosition position
  - TileType tileType
  - boolean blockedByRobber
  - int numberTokenValue
  - List<Node> intersections (6 nodes)

Methods:
  - Tile(AxialPosition, int token, TileType, List<Node>)
  - TileType getTileType()
  - int getToken()
  - void setBlockedByRobber(boolean)
  - boolean getBlockedByRobber()
  - List<Node> getNodes()
  - AxialPosition getPosition()
  - String toString()

Purpose: Represents one hexagonal tile on the board
```

### 9. Node
```
Fields:
  - NodePosition position
  - SettlementStructure structure
  - List<Edge> edges (2-3 edges)

Methods:
  - Node(NodePosition)
  - Edge getEdge(Node endNode)
  - void setStructure(SettlementStructure)
  - SettlementStructure getStructure()
  - NodePosition getPosition()
  - void addEdge(Edge)
  - List<Edge> getEdges()
  - String toString()

Purpose: Intersection point where settlements/cities are built
```

### 10. Edge
```
Fields:
  - EdgePosition position
  - Node[] connectedNodes (2 nodes)
  - Road structure

Methods:
  - Edge(EdgePosition, Node, Node)
  - Node getEnd(Node startNode)
  - void placeRoad(Road)
  - Road getRoad()
  - EdgePosition getPosition()
  - Node[] getConnectedNodes()
  - String toString()

Purpose: Path between nodes where roads are built
```

### 11. Structure (Abstract)
```
Fields:
  - Player owner

Methods:
  - Structure(Player owner)
  - Player getOwner()

Purpose: Base class for all game structures
```

### 12. SettlementStructure (Abstract)
```
Extends: Structure

Fields:
  - int victoryPoints

Methods:
  - SettlementStructure(Player, int VP)
  - int getVP()

Purpose: Base class for settlements and cities
```

### 13. Settlement
```
Extends: SettlementStructure

Fields:
  - NodePosition location

Methods:
  - Settlement(Player, NodePosition, int VP)
  - NodePosition getLocation()
  - String toString()

Purpose: Basic settlement structure (1 victory point)
```

### 14. City
```
Extends: SettlementStructure

Fields:
  - NodePosition location

Methods:
  - City(Player, NodePosition, int VP)
  - NodePosition getLocation()
  - String toString()

Purpose: Advanced city structure (2 victory points)
```

### 15. Road
```
Extends: Structure

Fields:
  - EdgePosition location

Methods:
  - Road(Player, EdgePosition)
  - EdgePosition getLocation()
  - String toString()

Purpose: Road structure connecting nodes
```

### 16. Board
```
Fields:
  - List<Tile> tiles (19 tiles)
  - List<Node> nodes (54 nodes)
  - List<Edge> edges (72 edges)
  - Map<AxialPosition, Tile> tileMap
  - Map<NodePosition, Node> nodeMap
  - Map<EdgePosition, Edge> edgeMap

Methods:
  - Board()
  - void initializeBoard()
  - Tile getTile(int index)
  - Tile getTileByPosition(AxialPosition)
  - Node getNodeByPosition(NodePosition)
  - Edge getEdgeByPosition(EdgePosition)
  - void placeRoad(Road)
  - void placeStructure(SettlementStructure)
  - List<Tile> getTiles()
  - List<Node> getNodes()
  - List<Edge> getEdges()
  - String toString()

Purpose: Manages the entire game board
```

### 17. Dice
```
Fields:
  - int sides
  - Random random

Methods:
  - Dice(int sides)
  - int[] rollDice(int numDice)
  - int getSides()

Purpose: Handles dice rolling mechanics
```

### 18. Player (Abstract)
```
Fields:
  - String playerName
  - Map<Resource, Integer> inventory (EnumMap)
  - List<Structure> structures

Methods:
  - Player(String name)
  - String getName()
  - Map<Resource, Integer> getInventory()
  - void addResource(Resource)
  - void addResource(Resource, int quantity)
  - boolean removeResource(Resource)
  - boolean removeResource(Resource, int quantity)
  - void addSettlement(Structure)
  - List<Structure> getStructures()
  - abstract void makeMove()
  - String toString()

Purpose: Base class for all player types
```

### 19. ComputerPlayer
```
Extends: Player

Methods:
  - ComputerPlayer(String name)
  - String getName()
  - Map<Resource, Integer> getResourceList()
  - void addResource(Resource)
  - void build(Structure)
  - void makeMove()
  - String toString()

Purpose: AI-controlled player implementation
```

### 20. Action (Interface)
```
Methods:
  - void execute()

Purpose: Interface for command pattern actions
```

### 21. BuildRoad
```
Implements: Action

Fields:
  - Player player
  - Board board
  - EdgePosition edgePosition

Methods:
  - BuildRoad(Player, Board, EdgePosition)
  - void execute()

Purpose: Action to build a road (costs 1 wood + 1 brick)
```

### 22. BuildSettlement
```
Implements: Action

Fields:
  - Player player
  - Board board
  - NodePosition nodePosition

Methods:
  - BuildSettlement(Player, Board, NodePosition)
  - void execute()

Purpose: Action to build settlement (costs 1 wood + 1 brick + 1 sheep + 1 wheat)
```

### 23. BuildCity
```
Implements: Action

Fields:
  - Player player
  - Board board
  - NodePosition nodePosition

Methods:
  - BuildCity(Player, Board, NodePosition)
  - void execute()

Purpose: Action to build city (costs 3 ore + 2 wheat)
```

### 24. GenerateResources
```
Implements: Action

Fields:
  - Game game
  - int diceRoll

Methods:
  - GenerateResources(Game, int diceRoll)
  - void execute()

Purpose: Distributes resources based on dice roll
```

### 25. EndTurn
```
Implements: Action

Fields:
  - Game game
  - Player player

Methods:
  - EndTurn(Game, Player)
  - void execute()

Purpose: Ends current player's turn
```

### 26. Game
```
Fields:
  - int turns
  - List<Player> players (3-4 players)
  - Board board
  - Dice[] dice (2 dice)
  - static Random rng
  - int currentPlayerIndex

Methods:
  - Game(int turns, Board, List<Player>)
  - void addPlayer(Player)
  - List<Player> getPlayers()
  - Board getBoard()
  - String searchPlayer(String name)
  - Player getCurrentPlayer()
  - void nextPlayer()
  - int rollDice()
  - void playTurn()
  - void play()
  - void displayResults()
  - int calculateVictoryPoints(Player)
  - String toString()

Purpose: Main game controller managing game flow
```

### 27. Demonstrator
```
Methods:
  - static void main(String[] args)

Purpose: Demonstration program to show game functionality
```

## Resource Costs Summary

- **Road**: 1 Wood + 1 Brick
- **Settlement**: 1 Wood + 1 Brick + 1 Sheep + 1 Wheat
- **City**: 3 Ore + 2 Wheat

## Victory Points

- **Settlement**: 1 VP
- **City**: 2 VP

## Design Patterns Implemented

1. **Command Pattern**: Action interface with concrete implementations
2. **Template Method**: Player.makeMove() is abstract
3. **Factory**: Player creation
4. **Strategy**: Different player types (Computer vs Human potential)

## Compilation Instructions

```bash
javac *.java
java Demonstrator
```

All 27 Java files have been generated and are ready to compile and run!
