package dev.aschmann.momedit.game.models;

import java.io.File;
import java.util.List;

public class SaveGame {

    /* bytes per hex line */
    private static final int BYTES_PER_LINE = 16;

    private Wizard wizard;
    private List<Item> items;
    private List<Spell> spells;
    private List<Hero> heroes;
    private List<City> cities;

    private int gold;
    private int landSize;
    private int magicIntensity;
    private int difficulty;

    private byte[] file;

    public SaveGame(File file) {

    }

    public static void main(File file) {
        SaveGame saveGame = new SaveGame(file);
    }

    private void load(File file) {

    }
}
