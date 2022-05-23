package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class SaveGame {

    /* bytes per hex line */
    private static final int BYTES_PER_LINE = 16;

    private static final String ARTIFACT_OFFSET_START = "6FB8";

    private static final String ARTIFACT_OFFSET_LENGTH = "32";

    private static final String CITY_OFFSET_START = "8AAC";

    private static final String CITY_OFFSET_LENGTH = "72";

    private final SaveGameDataLoader loader;

    private final AddressMap addressMap;

    private Path path;

    /**
     * byte array of binary savegame
     */
    private byte[] fileBytes;

    public SaveGame() {
        addressMap = new AddressMap();
        loader = new SaveGameDataLoader();
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

    private String findOffset(String offsetStart, int length) {
        byte[] byteValues = new byte[length];
        int offsetInt = Integer.parseInt(offsetStart, 16);
        System.arraycopy(fileBytes, offsetInt, byteValues, 0, length);
        if (length > 1) { // little endian handling, reverse byte[]
            reverseByteArray(byteValues);
        }

        return BaseEncoding.base16().encode(byteValues);
    }

    public void writeOffset(String offsetStart, int length, int value) {
        // decode only takes uppercase hex values for ... reasons
        String hexval = Integer.toHexString(value).toUpperCase();
        if (((length * 2) - hexval.length()) == 1) { // add padding or BaseEncoding will break
            hexval = "0" + hexval;
        }
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

    public List<SaveGameEntryInterface> getBase() {
        return loadDataMapList(SaveGameDataLoader.BASE_TYPE);
    }

    public List<SaveGameEntryInterface> getAbilities() {
        return loadDataMapList(SaveGameDataLoader.ABILITIES_TYPE);
    }

    public List<SaveGameEntryInterface> getNature() {
        return loadDataMapList(SaveGameDataLoader.NATURE_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getSorcery() {
        return loadDataMapList(SaveGameDataLoader.SORCERY_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getChaos() {
        return loadDataMapList(SaveGameDataLoader.CHAOS_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getLife() {
        return loadDataMapList(SaveGameDataLoader.LIFE_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getDeath() {
        return loadDataMapList(SaveGameDataLoader.DEATH_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getArcane() {
        return loadDataMapList(SaveGameDataLoader.ARCANE_SPELLS_TYPE);
    }

    public Map<String, String> getAbilityMap() {
        // refactor? maybe better to move to same logic
        return addressMap.abilities();
    }

    private List<SaveGameEntryInterface> loadDataMapList(String type) {
        try {
            return loader.loadData(type).stream()
                .peek(item -> item.setValue(
                    Integer.parseInt(findOffset(item.getHexOffset(), item.getLength()), 16)
                ))
                .collect(Collectors.toCollection(ArrayList<SaveGameEntryInterface>::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<SaveGameEntryInterface> createList(Map<String, String> map, int length) {
        return map.entrySet()
            .stream()
            .map(entry -> new ListItem(
                entry.getKey(),
                entry.getValue(),
                Integer.parseInt(findOffset(entry.getValue(), length), 16)
            ))
            .collect(Collectors.toCollection(ArrayList<SaveGameEntryInterface>::new));
    }
}
