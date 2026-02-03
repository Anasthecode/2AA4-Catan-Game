import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node (intersection) on the board where settlements and cities can be built.
 */
public class Node {
    private NodePosition position;
    private SettlementStructure structure;
    private List<Edge> edges;

    /**
     * Constructs a Node at the specified position.
     * 
     * @param position the position of this node
     */
    public Node(NodePosition position) {
        this.position = position;
        this.structure = null;
        this.edges = new ArrayList<>();
    }

    /**
     * Gets the edge connecting this node to the specified end node.
     * 
     * @param endNode the end node
     * @return the edge connecting the nodes, or null if not connected
     */
    public Edge getEdge(Node endNode) {
        for (Edge edge : edges) {
            if (edge.getEnd(this) == endNode) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Sets the structure at this node.
     * 
     * @param structure the settlement or city to place
     */
    public void setStructure(SettlementStructure structure) {
        this.structure = structure;
    }

    /**
     * Gets the structure at this node.
     * 
     * @return the structure, or null if none exists
     */
    public SettlementStructure getStructure() {
        return structure;
    }

    /**
     * Gets the position of this node.
     * 
     * @return the node position
     */
    public NodePosition getPosition() {
        return position;
    }

    /**
     * Adds an edge to this node.
     * 
     * @param edge the edge to add
     */
    public void addEdge(Edge edge) {
        if (!edges.contains(edge)) {
            edges.add(edge);
        }
    }

    /**
     * Gets all edges connected to this node.
     * 
     * @return list of edges
     */
    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    /**
     * Returns a string representation of this node.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        String structureInfo = structure != null ? structure.toString() : "Empty";
        return "Node at " + position + ": " + structureInfo;
    }
}
