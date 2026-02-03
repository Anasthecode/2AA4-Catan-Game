# Catan Game Implementation

This is a Java implementation of the Settlers of Catan board game based on a UML class diagram.

## Project Structure

The project contains the following classes and enumerations:

### Enumerations
- **TileType**: Represents different tile types (FOREST, PASTURE, FIELD, HILLS, MOUNTAIN, DESERT)
- **Resource**: Represents resource types (WOOD, SHEEP, WHEAT, BRICK, ORE)
- **RelativeNodeLocation**: Node positions on tiles (NORTH, SOUTH)
- **RelativeEdgeLocation**: Edge positions on tiles (WEST, NORTHWEST, NORTHEAST)

### Core Game Classes
- **Game**: Main game controller that manages turns, players, and game flow
- **Board**: Manages the game board with tiles, nodes, and edges
- **Dice**: Handles dice rolling mechanics
- **Player** (abstract): Base class for all players
- **ComputerPlayer**: AI-controlled player implementation

### Board Components
- **Tile**: Hexagonal tiles with resource types and number tokens
- **Node**: Intersection points where settlements/cities can be built
- **Edge**: Paths where roads can be built

### Positioning System
- **AxialPosition**: Coordinates for tiles in hexagonal grid
- **NodePosition**: Coordinates for node positions
- **EdgePosition**: Coordinates for edge positions

### Structures
- **Structure** (abstract): Base class for all structures
- **SettlementStructure** (abstract): Base for settlement-type structures
- **Settlement**: Basic settlement structure (1 VP)
- **City**: Advanced settlement structure (2 VP)
- **Road**: Path structure

### Actions (Command Pattern)
- **Action** (interface): Interface for game actions
- **BuildRoad**: Action to build a road
- **BuildSettlement**: Action to build a settlement
- **BuildCity**: Action to build a city
- **GenerateResources**: Action to distribute resources based on dice roll
- **EndTurn**: Action to end current player's turn

### Main Class
- **Demonstrator**: Contains the main method to demonstrate the game

## Key Features

1. **Hexagonal Board System**: Uses axial coordinates for hexagonal tile positioning
2. **Resource Management**: Players collect and spend resources
3. **Structure Building**: Players can build roads, settlements, and cities
4. **Dice Rolling**: Two 6-sided dice determine resource generation
5. **Turn-Based Gameplay**: Players take turns in sequence
6. **Victory Points**: Settlements worth 1 VP, Cities worth 2 VP
7. **Action System**: Uses Command pattern for game actions

## Game Rules Implemented

- 3-4 players supported
- Players collect resources based on dice rolls and adjacent structures
- Resource costs:
  - Road: 1 wood + 1 brick
  - Settlement: 1 wood + 1 brick + 1 sheep + 1 wheat
  - City: 3 ore + 2 wheat
- Settlements worth 1 victory point
- Cities worth 2 victory points
- Robber blocks resource generation when activated (on roll of 7)

## How to Compile and Run

```bash
# Compile all Java files
javac *.java

# Run the demonstrator
java Demonstrator
```

## Design Patterns Used

1. **Abstract Factory**: For creating different player types
2. **Command Pattern**: For game actions (BuildRoad, BuildSettlement, etc.)
3. **Template Method**: Player class with abstract makeMove method
4. **Composite**: Board structure with tiles, nodes, and edges

## Class Relationships

- Game has a Board and manages Players
- Board contains Tiles, Nodes, and Edges
- Players own Structures (Roads, Settlements, Cities)
- Actions operate on Players, Board, and Game
- Positions define locations on the hexagonal grid

## Notes

- The Board initialization is simplified and would need full implementation for a complete game
- AI logic in ComputerPlayer.makeMove() is basic and could be enhanced
- Some advanced Catan features (development cards, trading, longest road, etc.) are not implemented
- The robber mechanism is acknowledged but not fully implemented

## UML Compliance

This implementation faithfully follows the provided UML diagram including:
- All classes, attributes, and methods as specified
- Proper inheritance hierarchies
- Association multiplicities
- Visibility modifiers (private, public)
- Abstract classes and interfaces
- Enumerations with specified literals

## Future Enhancements

- Full board initialization with proper hexagonal layout
- Advanced AI for computer players
- Trading between players
- Development cards
- Longest road and largest army bonuses
- Maritime trade
- Graphical user interface
