# Master of Magic savegame editor

Yes, this is yet another [MoM](https://de.wikipedia.org/wiki/Master_of_Magic) savegame editor, but since I'm on a linux-based notebook and the other editors are all very old like the game itself, here we go.

## Why?
It's easy: Because I can.  
In 1994 when MoM was first released, it took me around a year to get it into my hands and I even have one copy on floppy disks laying around here (aside fom the GoG one and the new release).  
After re-discovering it a few weeks back and the need to (re)learn some Java skills, fiddling with JavaFX etc., I decided to build this editor.

Basically this is my hommage to this great old game, I so dearly love.

## Editor
I'll fill this section with a few infos on what you can edit and where it's limitations are.  
But, to be honest, [there are enough resources](https://masterofmagic.fandom.com/wiki/Save_Game_Format) on the game itself and [how to cheat](http://pcgamescreens.blogspot.com/2017/12/master-of-magic-hex-editor-codes-and.html) :-)

## Notes on savegame architecture (technical stuff)

### items/artifacts
Items in general are stored within a specific space of 250 units max in the savegame.  
Every unit has 50 bytes.

| Offset bytes | What             | Data type | value range |
|--------------|------------------|-----------|-------------|
| 0-29         | item name        | string    | -           |
| 30           | type             | int       | 0-6         |
| 31           | -                | -         | -           |
| 32           | image            | int       | 0-?         |
| 33           | -                | -         | -           |
| 34-35        | price            | int       |             |
| 36           | attack bonus     | int       | 0-6         |
| 37           | hit bonus        | int       | 0-3         |
| 38           | defense bonus    | int       | 0-6         |
| 39           | movement bonus   | int       | 0-4         |
| 40           | resistance bonus | int       | 0-6         |
| 41           | spell skill      | int       | 0-20        |
| 42           | spell save       | int       | 0-4         |
| 43           | spell            | int       | 0-198       |
| 44           | spell charges    | int       | 0-4         |
| 45           | -                | -         | -           |
| 46           | enchantments1    | int       | 0-255       |
| 47           | enchantments2    | int       | 0-255       |
| 48           | enchantments3    | int       | 0-255       |
| 49           | enchantments4    | int       | 0-255       |


Enchantments are fun. They range from offset 46-49, each byte one enchantment or a combination thereof.

| int | hex | type                                    |
|-----|-----|-----------------------------------------|
| 1   | 1   | Vampiric, life draining                 |
| 2   | 2   | Guardian Wind, as guardian wind spell   |
| 4   | 4   | Lighting, armor piercing                |
| 8   | 8   | Cloak of Fear, as cloak of fear spell   |
| 16  | 10  | Destruction, resist or be disintegrated |
| 32  | 20  | Wraithform, as wraithform spell         |
| 64  | 40  | Regeneration, as regeneration spell     |
| 128 | 80  | Pathfinding, as pathfinding spell       |

The funny thing is, if you've noticed the gaps between the progression 1,2,4,8,16...  
In between you'll get a combination of all former enchantments.  
As an example: int 7 would be 4 + 2 + 1, meaning you'd get all the three previous enchantments. If you're above 128, you'll iterate through all possible combinations between the eight enchantments for this offset byte (46).  
In total there are 4 slots of 8 enchantments, each.  
To get all the spells and enchantments, I did a lot of "change savegame, load, check" to get grip of the progression and a list of all the spells. It was tedious >.<

## Contributions
Are always welcome. If it's just to point out missing tests (there aren't any, yet), add improvements or fix bugs: Feel free.   
And it's [MIT License](LICENSE.txt).