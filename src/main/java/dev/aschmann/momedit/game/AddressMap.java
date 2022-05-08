package dev.aschmann.momedit.game;

import java.util.HashMap;
import java.util.Map;

public class AddressMap {

    private Map<String, String> abilities;

    public AddressMap() {
        initAbilities();
    }

    public static void main() {
        AddressMap map = new AddressMap();
    }

    private void initAbilities() {
        abilities = new HashMap<String, String>();
        abilities.put("Alchemy", "A4C");
        abilities.put("Warlord", "A4D");
        abilities.put("Chaos Master", "A4E");
        abilities.put("Nature Master", "A4F");
        abilities.put("Sorcery Master", "A50");
        abilities.put("Infernal Power", "A51");
        abilities.put("Divine Power", "A52");
        abilities.put("Sage Master", "A53");
        abilities.put("Channeller", "A54");
        abilities.put("Myrran", "A55");
        abilities.put("Archmage", "A56");
        abilities.put("Mana Focusing", "A57");
        abilities.put("Node Mastery", "A58");
        abilities.put("Famous", "A59");
        abilities.put("Runemaster", "A5A");
        abilities.put("Conjurer", "A5B");
        abilities.put("Charismatic", "A5C");
        abilities.put("Artificer", "A5D");
    }

    public Map<String, String> abilities() {
        return abilities;
    }
}
