package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveGame {

    /* bytes per hex line */
    private static final int BYTES_PER_LINE = 16;

    private AddressMap addressMap;

    private Wizard wizard;
    private List<Item> items;
    private List<Spell> spells;
    private List<Hero> heroes;
    private List<City> cities;

    // debug
    private StringBuilder abilities;

    private int gold;
    private int landSize;
    private int magicIntensity;
    private int difficulty;

    /**
     * byte array of binary savegame
     */
    private byte[] fileBytes;

    public SaveGame(File file) {
        //@todo move to bean if DI added
        addressMap = new AddressMap();
        load(file);
        loadBaseValues();
    }

    public static void main(File file) {
        SaveGame saveGame = new SaveGame(file);
    }

    /**
     * Just for reference, Hexmap as StringBuilder instance and debugging
     * @return StringBuilder
     */
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

    private void loadBaseValues() {
        /*
            C44-C45 Change Mana (enter 3075)
            C46-C48 Casting Skill(Personal)
            D3E-D3F Change Money or Gold (enter 3075)
            D40-D42 Casting Skill(Adjusted)
         */
        //String value = findOffset(2653, 1);
        initAbilities();
        //String value = findOffset("A4C", 1);
        //value = findOffset("A4D", 1);
        //value = findOffset("A4E", 1);
        //value = findOffset(2654, 1);
        //value = findOffset(2655, 1);
    }

    private String findOffset(String offsetStart, int length) {
        byte[] byteValues = new byte[length];
        // convert offset from hex string to int
        /*byte[] offset = BaseEncoding.base16().decode(offsetStart);
        ByteBuffer wrapped = ByteBuffer.wrap(offset); // big-endian by default
        short offsetInt = wrapped.getShort(); // 1*/
        int offsetInt = Integer.parseInt(offsetStart,16);

        System.arraycopy(fileBytes, offsetInt, byteValues, 0, length);
        return BaseEncoding.base16().encode(byteValues).toString();
    }
    
    private void initAbilities() {
        abilities = new StringBuilder();
        addressMap.abilities().forEach((k, v) ->
                abilities.append(k + " (offset " + v + "): " + Integer.parseInt(findOffset(v, 1), 16) + "\n")
        );
    }

    public StringBuilder getAbilities() {
        return abilities;
    }
}
