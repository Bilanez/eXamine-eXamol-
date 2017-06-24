package org.cwi.examine.model;

abstract public class NetworkElement {

    public final String identifier;
    public final String name;
    public final String url;
    public final double score;

    public NetworkElement(final String identifier, final String name, final String url, final double score) {
        this.identifier = identifier;
        this.name = name;
        this.url = url;
        this.score = score;
    }
}