package dev.aschmann.momedit.game.models;

abstract class SaveGameEntry {

    protected String name;

    protected String hexOffset;

    protected int value;

    public String getName() {
        return name;
    }

    public String getHexOffset() {
        return hexOffset;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
