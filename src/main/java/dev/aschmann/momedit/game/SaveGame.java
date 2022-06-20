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
import java.util.stream.Stream;

public class SaveGame {

    /* bytes per hex line */
    private static final int BYTES_PER_LINE = 16;

    private static final String ARTIFACT_OFFSET_START = "6FB8";

    private static final String ARTIFACT_OFFSET_LENGTH = "32";

    private static final String VAULT_ARTIFACT_STORAGE_START = "0B08";

    private static final int ARTIFACT_MAX_AMOUNT = 132;

    //private static final String CITY_OFFSET_START = "8AAC";

    //private static final String CITY_OFFSET_LENGTH = "72";

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

    public Map<String, String> getEnchantments() {
        try {
            return loader.loadArtifactEnchantments().stream()
                .collect(Collectors.toMap(
                        SimpleItemInterface::getName, SimpleItemInterface::getCompoundId
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
        artifact.setOffset(artifactOffset);

        artifact.setGraphics(findOffsetInt(artifactOffset + 30, 1));
        artifact.setType(findOffsetInt(artifactOffset + 32, 1));
        artifact.setManaPrice(findOffsetInt(artifactOffset + 34, 2));
        artifact.setAttackBonus(findOffsetInt(artifactOffset + 36, 1));
        artifact.setHitBonus(findOffsetInt(artifactOffset + 37, 1));
        artifact.setDefenseBonus(findOffsetInt(artifactOffset + 38, 1));
        artifact.setMovementBonus(findOffsetInt(artifactOffset + 39, 1));
        artifact.setResistanceBonus(findOffsetInt(artifactOffset + 40, 1));
        artifact.setSpellSkill(findOffsetInt(artifactOffset + 41, 1));
        artifact.setSpellSave(findOffsetInt(artifactOffset + 42, 1));
        artifact.setSpell(findOffsetInt(artifactOffset + 43, 1));
        artifact.setSpellCharges(findOffsetInt(artifactOffset + 44, 1));
        artifact.setEnchantments1(findOffsetInt(artifactOffset + 46, 1));
        artifact.setEnchantments2(findOffsetInt(artifactOffset + 47, 1));
        artifact.setEnchantments3(findOffsetInt(artifactOffset + 48, 1));
        artifact.setEnchantments4(findOffsetInt(artifactOffset + 49, 1));

        // double list stream, maybe not too performant. Worry later.
        if (readVaultData().contains(id)) {
            artifact.setVaultStorage(readVaultData().indexOf(id));
        }

        return artifact;
    }

    public void saveArtifact(Artifact artifact) {
        if (artifact.getOffset() == 0) { // new items only
            int offsetStart = Integer.parseInt(ARTIFACT_OFFSET_START, 16);
            int artifactSize = Integer.parseInt(ARTIFACT_OFFSET_LENGTH, 16);
            artifact.setOffset((artifact.getId() * artifactSize) + offsetStart);
        }

        byte[] name = artifact.getName().getBytes(StandardCharsets.ISO_8859_1);
        System.arraycopy(name, 0, fileBytes, artifact.getOffset(), name.length);
        writeOffset(artifact.getOffset(30), 1, artifact.getGraphics());
        writeOffset(artifact.getOffset(32), 1, artifact.getType());
        writeOffset(artifact.getOffset(34), 2, artifact.getManaPrice());
        writeOffset(artifact.getOffset(36), 1, artifact.getAttackBonus());
        writeOffset(artifact.getOffset(37), 1, artifact.getHitBonus());
        writeOffset(artifact.getOffset(38), 1, artifact.getDefenseBonus());
        writeOffset(artifact.getOffset(39), 1, artifact.getMovementBonus());
        writeOffset(artifact.getOffset(40), 1, artifact.getResistanceBonus());
        writeOffset(artifact.getOffset(41), 1, artifact.getSpellSkill());
        writeOffset(artifact.getOffset(42), 1, artifact.getSpellSave());
        writeOffset(artifact.getOffset(43), 1, artifact.getSpell());
        writeOffset(artifact.getOffset(44), 1, artifact.getSpellCharges());
        writeOffset(artifact.getOffset(46), 1, artifact.getEnchantments1());
        writeOffset(artifact.getOffset(47), 1, artifact.getEnchantments2());
        writeOffset(artifact.getOffset(48), 1, artifact.getEnchantments3());
        writeOffset(artifact.getOffset(49), 1, artifact.getEnchantments4());
        if (artifact.getVaultStorage() > 0) {
            writeOffset(getVaultOffset(artifact.getVaultStorage()), 2, artifact.getId());
        }
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

    private List<Integer> readVaultData() {
        // there are four vault storage spaces
        return Stream.of(1, 2, 3, 4)
            .map(id -> findOffsetInt(getVaultOffset(id), 2))
            .collect(Collectors.toList());
    }

    private int getVaultOffset(int vaultId) {
        return Integer.parseInt(VAULT_ARTIFACT_STORAGE_START, 16) + ((vaultId - 1) * 2);
    }

    private String intToPaddedHex(int value) {
        // for ... reasons it always has to be uppercase
        String hexval = Integer.toHexString(value).toUpperCase();
        if ((hexval.length() % 2) != 0) { // add padding or BaseEncoding will break
            hexval = "0" + hexval;
        }

        return hexval;
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

    private int findOffsetInt(String offsetStart, int length) {
        // use a "#" to make sure it's handled as a hex value, otherwise it might break
        return Integer.decode("#" + findOffset(offsetStart, length));
    }

    private int findOffsetInt(int offsetStart, int length) {
        return findOffsetInt(intToPaddedHex(offsetStart), length);
    }

    private String readStringFromOffset(int offsetStart, int length) {
        byte[] byteValues = new byte[length];
        System.arraycopy(fileBytes, offsetStart, byteValues, 0, length);

        return new String(byteValues, StandardCharsets.ISO_8859_1).trim();
    }

    public void writeOffset(int offsetStart, int length, int value) {
        try {
            byte[] tmpValues = BaseEncoding.base16().decode(intToPaddedHex(value));
            // fix for 1 byte values in 2+ byte slots with little endian
            byte[] byteValues = new byte[length];
            System.arraycopy(tmpValues, 0, byteValues, (byteValues.length - tmpValues.length), tmpValues.length);

            if (length > 1) { // little endian again
                reverseByteArray(byteValues);
            }

            System.arraycopy(byteValues, 0, fileBytes, offsetStart, byteValues.length);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void writeOffset(String offsetStart, int length, int value) {
        int offsetInt = Integer.parseInt(offsetStart, 16);
        writeOffset(offsetInt, length, value);
    }

    private void reverseByteArray(byte[] byteArray) {
        for (int i = 0; i < byteArray.length / 2; i++) {
            byte temp = byteArray[i];
            byteArray[i] = byteArray[byteArray.length - i - 1];
            byteArray[byteArray.length - i - 1] = temp;
        }
    }
}
