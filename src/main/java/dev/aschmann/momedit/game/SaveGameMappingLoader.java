package dev.aschmann.momedit.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import dev.aschmann.momedit.game.exceptions.DataTypeNotFoundException;
import dev.aschmann.momedit.game.models.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveGameMappingLoader {

    public static final String ABILITIES_TYPE = "abilities";

    public static final String BASE_TYPE = "base";

    public static final String DEATH_SPELLS_TYPE = "death-spells";

    public static final String LIFE_SPELLS_TYPE = "life-spells";
    
    public static final String ARCANE_SPELLS_TYPE = "arcane-spells";
    
    public static final String NATURE_SPELLS_TYPE = "nature-spells";
    
    public static final String CHAOS_SPELLS_TYPE = "chaos-spells";

    public static final String SORCERY_SPELLS_TYPE = "sorcery-spells";

    public static final String ARTIFACT_ENCHANTMENT_TYPE = "item-enchantments";

    public static final String ARTIFACT_SPELL_TYPE = "item-spells";

    private final ObjectMapper mapper;

    SaveGameMappingLoader() {
        mapper = new ObjectMapper(new YAMLFactory());
    }

    public static void main() {
        SaveGameMappingLoader loader = new SaveGameMappingLoader();
    }

    public List<SaveGameEntryInterface> loadMap(String type) throws IOException {
        // easiest for now
        String file = switch (type) {
            case ABILITIES_TYPE -> ABILITIES_TYPE + ".yaml";
            case BASE_TYPE -> BASE_TYPE + ".yaml";
            case DEATH_SPELLS_TYPE -> DEATH_SPELLS_TYPE + ".yaml";
            case LIFE_SPELLS_TYPE -> LIFE_SPELLS_TYPE + ".yaml";
            case NATURE_SPELLS_TYPE -> NATURE_SPELLS_TYPE + ".yaml";
            case ARCANE_SPELLS_TYPE -> ARCANE_SPELLS_TYPE + ".yaml";
            case CHAOS_SPELLS_TYPE -> CHAOS_SPELLS_TYPE + ".yaml";
            case SORCERY_SPELLS_TYPE -> SORCERY_SPELLS_TYPE + ".yaml";
            default -> throw new DataTypeNotFoundException();
        };

        return mapper.readValue(
            new File("src/main/resources/" + file),
            mapper.getTypeFactory().constructCollectionType(List.class, SaveGameItem.class)
        );
    }

    public List<SimpleItemInterface> loadArtifactMap(String type) throws IOException {
        String file = switch (type) {
            case ARTIFACT_ENCHANTMENT_TYPE -> ARTIFACT_ENCHANTMENT_TYPE + ".yaml";
            case ARTIFACT_SPELL_TYPE -> ARTIFACT_SPELL_TYPE + ".yaml";
            default -> throw new DataTypeNotFoundException();
        };

        return mapper.readValue(
                new File("src/main/resources/" + file),
                mapper.getTypeFactory().constructCollectionType(List.class, ArtifactEnchantmentItem.class)
        );
    }
}
