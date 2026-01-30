import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the game board containing tiles, nodes, and edges.
 */
public class Board {
    private List<Tile> tiles;
    private List<Node> nodes;
    private List<Edge> edges;
    private Map<AxialPosition, Tile> tileMap;
    private Map<NodePosition, Node> nodeMap;
    private Map<EdgePosition, Edge> edgeMap;

    /**
     * Constructs a Board and initializes the game layout.
     */
    public Board() {
        this.tiles = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.tileMap = new HashMap<>();
        this.nodeMap = new HashMap<>();
        this.edgeMap = new HashMap<>();
        initializeBoard();
    }

    /**
     * Initializes the board with tiles, nodes, and edges.
     */
    private void initializeBoard() {
        // Board initialization logic would go here
        // This would create 19 tiles, 54 nodes, and 72 edges
        // arranged in the standard Catan hexagonal pattern
    }

    /**
     * Gets the tile at the specified index.
     * 
     * @param index the tile index
     * @return the tile at the index
     */
    public Tile getTile(int index) {
        if (index >= 0 && index < tiles.size()) {
            return tiles.get(index);
        }
        return null;
    }

    /**
     * Gets a tile by its position.
     * 
     * @param position the axial position
     * @return the tile at that position, or null
     */
    public Tile getTileByPosition(AxialPosition position) {
        return tileMap.get(position);
    }

    /**
     * Gets a node by its position.
     * 
     * @param position the node position
     * @return the node at that position, or null
     */
    public Node getNodeByPosition(NodePosition position) {
        return nodeMap.get(position);
    }

    /**
     * Gets an edge by its position.
     * 
     * @param position the edge position
     * @return the edge at that position, or null
     */
    public Edge getEdgeByPosition(EdgePosition position) {
        return edgeMap.get(position);
    }

    /**
     * Places a road on the board.
     * 
     * @param road the road to place
     */
    public void placeRoad(Road road) {
        Edge edge = edgeMap.get(road.getLocation());
        if (edge != null) {
            edge.placeRoad(road);
        }
    }

    /**
     * Places a structure on the board.
     * 
     * @param structure the settlement or city to place
     */
    public void placeStructure(SettlementStructure structure) {
        NodePosition location = null;
        if (structure instanceof Settlement) {
            location = ((Settlement) structure).getLocation();
        } else if (structure instanceof City) {
            location = ((City) structure).getLocation();
        }
        
        if (location != null) {
            Node node = nodeMap.get(location);
            if (node != null) {
                node.setStructure(structure);
            }
        }
    }

    /**
     * Gets all tiles on the board.
     * 
     * @return list of all tiles
     */
    public List<Tile> getTiles() {
        return new ArrayList<>(tiles);
    }

    /**
     * Gets all nodes on the board.
     * 
     * @return list of all nodes
     */
    public List<Node> getNodes() {
        return new ArrayList<>(nodes);
    }

    /**
     * Gets all edges on the board.
     * 
     * @return list of all edges
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Returns a string representation of the board.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Board with ").append(tiles.size()).append(" tiles, ");
        sb.append(nodes.size()).append(" nodes, and ");
        sb.append(edges.size()).append(" edges\n");
        
        for (int i = 0; i < tiles.size(); i++) {
            sb.append("Tile ").append(i).append(": ").append(tiles.get(i)).append("\n");
        }
        
        return sb.toString();
    }
}
