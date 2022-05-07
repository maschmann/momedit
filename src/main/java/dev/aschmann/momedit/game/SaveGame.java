package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * byte array of binary savegame
     */
    private byte[] fileBytes;

    public SaveGame(File file) {
        load(file);
    }

    public static void main(File file) {
        SaveGame saveGame = new SaveGame(file);
    }

    public StringBuilder createHexMap() {
        StringBuilder concatText = new StringBuilder();
        int linesInFile = (int) Math.round((float) fileBytes.length / BYTES_PER_LINE);

        for (int i = 0; i < linesInFile; i++) { // catch the rest of the byte array
            if ((linesInFile - i) == 1) {
                int restOfArray = fileBytes.length - (i * BYTES_PER_LINE);
                concatText.append(
                        BaseEncoding.base16().lowerCase().encode(
                                fileBytes, (i * BYTES_PER_LINE), restOfArray
                        ).toString()
                );
            } else {
                concatText.append(
                    BaseEncoding.base16().lowerCase().encode(
                        fileBytes, (i * BYTES_PER_LINE), BYTES_PER_LINE
                    ).toString()
                );
                concatText.append("\n");
            }
        }

        return concatText;
    }

    private void load(File file) {
        try {
            Path path = Paths.get(file.getAbsolutePath());
            fileBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
