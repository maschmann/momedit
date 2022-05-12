package dev.aschmann.momedit.game.models;

public class Spell extends SaveGameEntry implements SaveGameEntryInterface {

    public Spell(String name, String hexOffset, int value) {
        this.name = name;;
        this.hexOffset = hexOffset;
        this.value = value;
    }

    public static void main(String name, String hexOffset, int value) {
        SaveGameEntryInterface spell = new Spell(name, hexOffset, value);
    }
}
