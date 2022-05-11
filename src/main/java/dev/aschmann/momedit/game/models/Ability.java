package dev.aschmann.momedit.game.models;

import dev.aschmann.momedit.game.SaveGame;

import java.io.File;

public class Ability extends SaveGameEntry implements SaveGameEntryInterface {

    public Ability(String name, String hexOffset, int value) {
        this.name = name;;
        this.hexOffset = hexOffset;
        this.value = value;
    }

    public static void main(String name, String hexOffset, int value) {
        SaveGameEntryInterface ability = new Ability(name, hexOffset, value);
    }
}
