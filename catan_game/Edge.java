/**
 * Represents an edge (path) on the board where roads can be built.
 */
public class Edge {
    private EdgePosition position;
    private Node[] connectedNodes;
    private Road structure;

    /**
     * Constructs an Edge connecting two nodes.
     * 
     * @param position the position of this edge
     * @param node1 the first node
     * @param node2 the second node
     */
    public Edge(EdgePosition position, Node node1, Node node2) {
        this.position = position;
        this.connectedNodes = new Node[2];
        this.connectedNodes[0] = node1;
        this.connectedNodes[1] = node2;
        this.structure = null;
        
        // Add this edge to both nodes
        node1.addEdge(this);
        node2.addEdge(this);
    }

    /**
     * Gets the other end of this edge given a start node.
     * 
     * @param startNode the starting node
     * @return the end node, or null if startNode is not part of this edge
     */
    public Node getEnd(Node startNode) {
        if (connectedNodes[0] == startNode) {
            return connectedNodes[1];
        } else if (connectedNodes[1] == startNode) {
            return connectedNodes[0];
        }
        return null;
    }

    /**
     * Places a road on this edge.
     * 
     * @param road the road to place
     */
    public void placeRoad(Road road) {
        this.structure = road;
    }

    /**
     * Gets the road on this edge.
     * 
     * @return the road, or null if none exists
     */
    public Road getRoad() {
        return structure;
    }

    /**
     * Gets the position of this edge.
     * 
     * @return the edge position
     */
    public EdgePosition getPosition() {
        return position;
    }

    /**
     * Gets the connected nodes.
     * 
     * @return array of two connected nodes
     */
    public Node[] getConnectedNodes() {
        return connectedNodes.clone();
    }

    /**
     * Returns a string representation of this edge.
     * 
     * @return string representation
     */
    @Override
    public String toString() {
        String roadInfo = structure != null ? structure.toString() : "No road";
        return "Edge at " + position + ": " + roadInfo;
    }
}
