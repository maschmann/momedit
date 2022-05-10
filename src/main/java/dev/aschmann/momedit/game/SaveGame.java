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

    private Path path;

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

    public int gold() {
        return gold;
    }

    public void setGold(int gold) {
        writeOffset("D3E", 2, gold);
    }

    public int mana() {
        return mana;
    }

    public int castingSkill() {
        return castingSkill;
    }

    public void save() {
        try {
            Files.write(path, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load(File file) {
        try {
            path = Paths.get(file.getAbsolutePath());
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
        int offsetInt = Integer.parseInt(offsetStart, 16);
        System.arraycopy(fileBytes, offsetInt, byteValues, 0, length);
        if (length > 1) { // little endian handling, reverse byte[]
            reverseByteArray(byteValues);
        }

        return BaseEncoding.base16().encode(byteValues).toString();
    }

    private void writeOffset(String offsetStart, int length, int value) {
        String hexval = Integer.toHexString(value);
        byte[] byteValues = BaseEncoding.base16().upperCase().decode(hexval);
        int offsetInt = Integer.parseInt(offsetStart, 16);
        if (length > 1) { // little endian again
            reverseByteArray(byteValues);
        }

        for (int i = 0; i < byteValues.length; i++) {
            fileBytes[offsetInt + i] = byteValues[i];
        }
    }

    private void reverseByteArray(byte[] byteArray) {
        for (int i = 0; i < byteArray.length / 2; i++) {
            byte temp = byteArray[i];
            byteArray[i] = byteArray[byteArray.length - i - 1];
            byteArray[byteArray.length - i - 1] = temp;
        }
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
