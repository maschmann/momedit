package dev.aschmann.momedit.game.models;

public class ListItem extends SaveGameEntry implements SaveGameEntryInterface {

    public ListItem(String name, String hexOffset, int value) {
        this.name = name;
        this.hexOffset = hexOffset;
        this.value = value;
    }

    public static void main(String name, String hexOffset, int value) {
        SaveGameEntryInterface item = new ListItem(name, hexOffset, value);
    }
}
