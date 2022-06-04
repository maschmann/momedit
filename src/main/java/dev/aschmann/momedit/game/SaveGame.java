package dev.aschmann.momedit.game;

import com.google.common.io.BaseEncoding;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    private static final int ARTIFACT_MAX_AMOUNT = 132;

    private static final String CITY_OFFSET_START = "8AAC";

    private static final String CITY_OFFSET_LENGTH = "72";

    private final SaveGameMappingLoader loader;

    private Path path;

    /**
     * byte array of binary savegame
     */
    private byte[] fileBytes;

    public SaveGame() {
        loader = new SaveGameMappingLoader();
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

    private String readStringFromOffset(int offsetStart, int length) {
        byte[] byteValues = new byte[length];
        System.arraycopy(fileBytes, offsetStart, byteValues, 0, length);

        return new String(byteValues, StandardCharsets.ISO_8859_1).trim();
    }

    private int findOffsetInt(String offsetStart, int length) {
        // use a "#" to make sure it's handled as a hex value, otherwise it might break
        return Integer.decode("#" + findOffset(offsetStart, length));
    }

    public void writeOffset(String offsetStart, int length, int value) {
        try {
            byte[] byteValues = BaseEncoding.base16().decode(intToPaddedHex(value));
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
        return loadMapWithData(SaveGameMappingLoader.BASE_TYPE);
    }

    public List<SaveGameEntryInterface> getAbilities() {
        return loadMapWithData(SaveGameMappingLoader.ABILITIES_TYPE);
    }

    public List<SaveGameEntryInterface> getNature() {
        return loadMapWithData(SaveGameMappingLoader.NATURE_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getSorcery() {
        return loadMapWithData(SaveGameMappingLoader.SORCERY_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getChaos() {
        return loadMapWithData(SaveGameMappingLoader.CHAOS_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getLife() {
        return loadMapWithData(SaveGameMappingLoader.LIFE_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getDeath() {
        return loadMapWithData(SaveGameMappingLoader.DEATH_SPELLS_TYPE);
    }

    public List<SaveGameEntryInterface> getArcane() {
        return loadMapWithData(SaveGameMappingLoader.ARCANE_SPELLS_TYPE);
    }

    public Map<String, String> getAbilityMap() {
        try {
            return loader.loadMap(SaveGameMappingLoader.ABILITIES_TYPE).stream()
                .collect(Collectors.toMap(
                    SaveGameEntryInterface::getName, SaveGameEntryInterface::getHexOffset
                ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Artifact> getArtifacts() {
        int offsetStart = Integer.parseInt(ARTIFACT_OFFSET_START, 16);
        int artifactSize = Integer.parseInt(ARTIFACT_OFFSET_LENGTH, 16);

        List<Artifact> artifacts = new ArrayList<>();
        // get all artifacts
        for (int i = 0; i < ARTIFACT_MAX_AMOUNT; i++) {
            // calculate the offsets per iteration
            int currentOffsetStart = (i * artifactSize) + offsetStart;
            Artifact artifact = loadArtifact(currentOffsetStart, i);
            if (!artifact.getName().isEmpty()) {
                artifacts.add(artifact);
            }
        }

        return artifacts;
    }

    private Artifact loadArtifact(int artifactOffset, int id) {
        Artifact artifact = new Artifact(id);

        artifact.setName(readStringFromOffset(artifactOffset, 29));

        artifact.setGraphics(
            findOffsetInt(intToPaddedHex(artifactOffset + 30), 1)
        );
        artifact.setType(
            findOffsetInt(intToPaddedHex(artifactOffset + 32), 1)
        );
        artifact.setManaPrice(
            findOffsetInt(intToPaddedHex(artifactOffset + 34), 2)
        );
        artifact.setAttackBonus(
            findOffsetInt(intToPaddedHex(artifactOffset + 36), 1)
        );
        artifact.setHitBonus(
            findOffsetInt(intToPaddedHex(artifactOffset + 37), 1)
        );
        artifact.setDefenseBonus(
            findOffsetInt(intToPaddedHex(artifactOffset + 38), 1)
        );
        artifact.setMovementBonus(
            findOffsetInt(intToPaddedHex(artifactOffset + 39), 1)
        );
        artifact.setResistanceBonus(
            findOffsetInt(intToPaddedHex(artifactOffset + 40), 1)
        );
        artifact.setSpellSkill(
            findOffsetInt(intToPaddedHex(artifactOffset + 41), 1)
        );
        artifact.setSpellSave(
            findOffsetInt(intToPaddedHex(artifactOffset + 42), 1)
        );
        artifact.setSpell(
            findOffsetInt(intToPaddedHex(artifactOffset + 43), 1)
        );
        artifact.setSpellCharges(
            findOffsetInt(intToPaddedHex(artifactOffset + 44), 1)
        );

        return artifact;
    }

    public void saveArtifact(Artifact artifact) {

    }

    private List<SaveGameEntryInterface> loadMapWithData(String type) {
        try {
            return loader.loadMap(type).stream()
                .peek(item -> item.setValue(
                    Integer.parseInt(findOffset(item.getHexOffset(), item.getLength()), 16)
                ))
                .collect(Collectors.toCollection(ArrayList<SaveGameEntryInterface>::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String intToPaddedHex(int value) {
        // for ... reasons it always has to be uppercase
        String hexval = Integer.toHexString(value).toUpperCase();
        if ((hexval.length() % 2) != 0) { // add padding or BaseEncoding will break
            hexval = "0" + hexval;
        }

        return hexval;
    }

    private String addIntToHex(int intval, String hexval) {
        return intToPaddedHex(
            intval + Integer.parseInt(hexval, 16)
        );
    }
}
