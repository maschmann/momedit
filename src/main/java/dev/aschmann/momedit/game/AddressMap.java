package dev.aschmann.momedit.game;

import java.util.HashMap;
import java.util.Map;

public class AddressMap {

    private final Map<String, String> abilities = new HashMap<String, String>();

    private final Map<String, String> base = new HashMap<String, String>();

    private final Map<String, String> books = new HashMap<String, String>();

    private final Map<String, String> nature = new HashMap<String, String>();

    private final Map<String, String> sorcery = new HashMap<String, String>();

    public AddressMap() {
        initAbilities();
        initBase();
        initBooks();
        initNature();
        initSorcery();
    }

    public static void main() {
        AddressMap map = new AddressMap();
    }

    private void initAbilities() {
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

    private void initBase() {
        /*
            C44-C45 Change Mana (enter 3075)
            C46-C48 Casting Skill(Personal)
            D3E-D3F Change Money or Gold (enter 3075)
            D40-D42 Casting Skill(Adjusted)
        */
        base.put("Mana", "C44");
        base.put("Casting Skill", "C46");
        base.put("Money", "D3E");
    }

    private void initBooks() {
        books.put("Nature Book", "A42");
        books.put("Sorcery Book", "A44");
        books.put("Chaos Book", "A46");
        books.put("Life Book", "A48");
        books.put("Death Book", "A4A");
    }

    private void initNature() {
        nature.put("Earth to Mud", "C4C");
        nature.put("Resist Elements", "C4D");
        nature.put("Wall Of Stone", "C4E");
        nature.put("Giant Strength", "C4F");
        nature.put("Web", "C50");
        nature.put("War Bears", "C51");
        nature.put("Stone Skin", "C52");
        nature.put("Water Walking", "C53");
        nature.put("Sprites", "C54");
        nature.put("Earth lore", "C55");
        nature.put("Cracks Call", "C56");
        nature.put("Natures eye", "C57");
        nature.put("Ice Bolt", "C58");
        nature.put("Giant Spiders", "C59");
        nature.put("Change Terrain", "C5A");
        nature.put("Pathfinding", "C5B");
        nature.put("Cockatrices", "C5C");
        nature.put("Transmute", "C5D");
        nature.put("Natures Cures", "C5E");
        nature.put("Basilisk", "C5F");
        nature.put("Elemental Armor", "C60");
        nature.put("Petrify", "C61");
        nature.put("Stone Giant", "C62");
        nature.put("Iron Skin", "C63");
        nature.put("Ice Storm", "C64");
        nature.put("Earthquake", "C65");
        nature.put("Gorgons", "C66");
        nature.put("Move Fortress", "C67");
        nature.put("Gaia's Blessing", "C68");
        nature.put("Earth Elemental", "C69");
        nature.put("Regeneration", "C6A");
        nature.put("Behemoth", "C6B");
        nature.put("Entangle", "C6C");
        nature.put("Natures Awareness", "C6D");
        nature.put("Call Lighting", "C6E");
        nature.put("Colossus", "C6F");
        nature.put("Earth Gate", "C70");
        nature.put("Herb Mastery", "C71");
        nature.put("Great Wyrm", "C72");
        nature.put("Nature's Wrath", "C73");
    }

    private void initSorcery() {
        sorcery.put("Resist Magic", "C74");
        sorcery.put("Dispel Magic True", "C75");
        sorcery.put("Floating Island", "C76");
        sorcery.put("Guardian Wind", "C77");
        sorcery.put("Phantom Warriors", "C78");
        sorcery.put("Confusion", "C79");
        sorcery.put("Word of Recall", "C7A");
        sorcery.put("Counter Magic", "C7B");
        sorcery.put("Nagas", "C7C");
        sorcery.put("Psionic Blast", "C7D");
        sorcery.put("Blur", "C7E");
        sorcery.put("Disenchant True", "C7F");
        sorcery.put("Vertigo", "C80");
        sorcery.put("Spell Lock", "C81");
        sorcery.put("Enchant Road", "C82");
        sorcery.put("Flight", "C83");
        sorcery.put("Wind Mastery", "C84");
        sorcery.put("Spell Blast", "C85");
        sorcery.put("Aura of Mastery", "C86");
        sorcery.put("Phantom Beast", "C87");
        sorcery.put("Disjuntion True", "C88");
        sorcery.put("Invisibility", "C89");
        sorcery.put("Wind Walking", "C8A");
        sorcery.put("Banish", "C8B");
        sorcery.put("Storm Giant", "C8C");
        sorcery.put("Air Elemental", "C8D");
        sorcery.put("Mind Storm", "C8E");
        sorcery.put("Stasis", "C8F");
        sorcery.put("Magic Immunity", "C90");
        sorcery.put("Haste", "C91");
        sorcery.put("Djinn", "C92");
        sorcery.put("Spell Ward", "C93");
        sorcery.put("Creature Binding", "C94");
        sorcery.put("Mass Invisibility", "C95");
        sorcery.put("Great UnSummoning", "C96");
        sorcery.put("Spell Binding", "C97");
        sorcery.put("Flying Fortress", "C98");
        sorcery.put("Sky Drake", "C99");
        sorcery.put("Suppress Magic", "C9A");
        sorcery.put("Time Stop", "C9B");
    }

    public Map<String, String> abilities() {
        return abilities;
    }

    public Map<String, String> base() {
        return base;
    }

    public Map<String, String> books() {
        return books;
    }

    public Map<String, String> nature() {
        return nature;
    }

    public Map<String, String> sorcery() {
        return sorcery;
    }
}
