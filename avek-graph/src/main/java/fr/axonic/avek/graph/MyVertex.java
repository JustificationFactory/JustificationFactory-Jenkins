package fr.axonic.avek.graph;

/**
 * Created by nathael on 08/09/16.
 */
class MyVertex {
    private final Object linkedObject;
    private final String name;
    int x;
    int y;
    int width;
    VertexType type;

    MyVertex(Object linkedObject, String name, VertexType type) {
        this.linkedObject = linkedObject;
        this.name = name;
        this.type = type;
        this.x = 0;
        this.y = 0;
        this.width = 1;
    }

    void accumulateType(VertexType type) {
        if (type == VertexType.CONCLUSION || this.type == VertexType.CONCLUSION) {
            this.type = VertexType.CONCLUSION;
        } else {
            this.type = type;
        }
    }

    @Override
    public String toString() {
        return "[" + type.getValue() + "] " + name;
    }

    String toDetailedString() {
        return toString() + " at x:" + x + ", y:" + y + ", width:" + width;
    }
}
