package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;
import javafx.collections.ObservableList;

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

    private int gold;
    private int mana;
    private int castingSkill;

    /**
     * byte array of binary savegame
     */
    private byte[] fileBytes;

    public SaveGame() {
        //@todo move to bean if DI added
        addressMap = new AddressMap();
    }

    public static void main() {
        SaveGame saveGame = new SaveGame();
    }

    public void load(File file) {
        try {
            path = Paths.get(file.getAbsolutePath());
            fileBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadBaseValues();
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

    public void writeSingleValOffset(String offset, int value) {
        writeOffset(offset, 1, value);
    }

    private void loadBaseValues() {
        loadOverallValues();
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
        // decode only takes uppercase hex values for ... reasons
        String hexval = Integer.toHexString(value).toUpperCase();
        try {
            byte[] byteValues = BaseEncoding.base16().decode(hexval);
            int offsetInt = Integer.parseInt(offsetStart, 16);
            if (length > 1) { // little endian again
                reverseByteArray(byteValues);
            }

            for (int i = 0; i < byteValues.length; i++) {
                fileBytes[offsetInt + i] = byteValues[i];
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
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

    /*public  findByName() {
        public static Carnet findByCodeIsIn(Collection<Carnet> listCarnet, String codeIsIn) {
            return listCarnet.stream().filter(carnet-> codeIsIn.equals(carnet.getCodeIsIn()))
                    .findFirst().orElse(null);
        }
    }*/

    public List<SaveGameEntryInterface> getAbilities() {
        List<SaveGameEntryInterface> list = new ArrayList<SaveGameEntryInterface>();
        addressMap.abilities().forEach((name, offset) -> {
            list.add(
                new Ability(
                    name,
                    offset,
                    Integer.parseInt(findOffset(offset, 1), 16)
                )
            );
        });

        return list;
    }

    public List<SaveGameEntryInterface> getNature() {
        List<SaveGameEntryInterface> list = new ArrayList<SaveGameEntryInterface>();
        addressMap.nature().forEach((name, offset) -> {
            list.add(
                new Spell(
                    name,
                    offset,
                    Integer.parseInt(findOffset(offset, 1), 16)
                )
            );
        });

        return list;
    }

    /**
     * for convenience we're breaking some rules and expose inner objects and domain knowledge
     * more or less directly.
     */
    public AddressMap getAddressMap() {
        return addressMap;
    }
}
