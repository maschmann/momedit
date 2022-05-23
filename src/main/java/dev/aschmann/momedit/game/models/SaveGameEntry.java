package dev.aschmann.momedit.game.models;

abstract class SaveGameEntry {

    protected String name;

    protected String hexOffset;

    protected int value;

    protected int maxValue;

    protected int length;

    protected int defaultValue;

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

    public int getLength() {
        return length;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getDefaultValue() {
        return defaultValue;
    };
}
