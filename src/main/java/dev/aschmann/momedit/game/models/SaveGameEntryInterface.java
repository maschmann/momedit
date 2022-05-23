package dev.aschmann.momedit.game.models;

public interface SaveGameEntryInterface {
    public String getName();

    public String getHexOffset();

    public int getValue();

    public int getLength();

    public int getMaxValue();

    public int getDefaultValue();

    public void setValue(int value);
}
