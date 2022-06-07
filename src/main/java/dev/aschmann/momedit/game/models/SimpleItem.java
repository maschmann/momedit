package dev.aschmann.momedit.game.models;

abstract class SimpleItem implements SimpleItemInterface {
    private String name;

    private int offset;

    private int value;

    public String getName() {
        return name;
    }

    public int getOffset() {
        return offset;
    }

    public int getValue() {
        return value;
    }
}
