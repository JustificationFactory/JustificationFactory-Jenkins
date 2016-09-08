package fr.axonic.avek.graph;

/**
 * Created by nathael on 08/09/16.
 */
enum VertexType {
    CONCLUSION("C"), EVIDENCE("E"), STRATEGY("S");

    final String value;
    VertexType(String c) {
        this.value = c;
    }

    public String getValue() {
        return value;
    }
}
