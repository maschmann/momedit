package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    private int mana;
    private int castingSkill;
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

    public int gold() {
        return gold;
    }

    public int mana() {
        return mana;
    }

    public int castingSkill() {
        return castingSkill;
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
        loadOverallValues();
        loadAbilities();
    }

    private String findOffset(String offsetStart, int length) {
        byte[] byteValues = new byte[length];
        int offsetInt = Integer.parseInt(offsetStart,16);
        System.arraycopy(fileBytes, offsetInt, byteValues, 0, length);
        if (length > 1) { // little endian handling, reverse byte[]
            byteValues = reverseByteArray(byteValues);
        }
        String hex = BaseEncoding.base16().encode(byteValues).toString();

        return hex;
    }

    private void writeOffset(String offsetStart, int length, int value) {
        byte[] byteValues = BaseEncoding.base16().decode(Integer.toHexString(value));
        int offsetInt = Integer.parseInt(offsetStart,16);
        if (length > 1) {
            byteValues = reverseByteArray(byteValues);
        }

        //fileBytes[offsetInt] = byteValues[];
    }

    private byte[] reverseByteArray(byte[] byteArray) {
        for(int i = 0; i < byteArray.length / 2; i++)
        {
            byte temp = byteArray[i];
            byteArray[i] = byteArray[byteArray.length - i - 1];
            byteArray[byteArray.length - i - 1] = temp;
        }

        return byteArray;
    }

    private void loadOverallValues() {
         /*
            C44-C45 Change Mana (enter 3075)
            C46-C48 Casting Skill(Personal)
            D3E-D3F Change Money or Gold (enter 3075)
            D40-D42 Casting Skill(Adjusted)
         */
        mana = Integer.parseInt(findOffset("C44", 2), 16);
        castingSkill = Integer.parseInt(findOffset("C46", 2), 16);
        gold = Integer.parseInt(findOffset("D3E", 2), 16);
    }

    private void loadAbilities() {
        abilities = new StringBuilder();
        addressMap.abilities().forEach((k, v) ->
                abilities.append(k + " (offset " + v + "): " + Integer.parseInt(findOffset(v, 1), 16) + "\n")
        );
    }

    public StringBuilder getAbilities() {
        return abilities;
    }
}
