package dev.aschmann.momedit.game.models;

public class ArtifactMapItem {
    private String name;

    private int hexOffsetRelative;

    private int length;

    public ArtifactMapItem() {}

    public static void main() {
        ArtifactMapItem item = new ArtifactMapItem();
    }

    public String getName() {
        return name;
    }

    public int getHexOffsetRelative() {
        return hexOffsetRelative;
    }

    public int getLength() {
        return length;
    }
}
