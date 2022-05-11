package dev.aschmann.momedit.game.models;

public interface SaveGameEntryInterface {
    public String getName();

    public String getHexOffset();

    public int getValue();

    public void setValue(int value);
}
