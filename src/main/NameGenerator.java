package main;

import java.util.Calendar;
import java.util.Objects;
import java.util.Random;

public class NameGenerator {

    protected String genName;
    private static final int diffBetweenAtoZ = 25;
    private static final int charValueOfa = 97;
    private String lastGeneratedName = "";
    private Random rand = new Random();

    char[] vowels = {
            'a', 'e', 'i', 'o', 'u'
    };

    protected static String[] nouns = {
            "Wolves", "Ancients", "Killers", "Merchants", "Nobles", "Horde", "Hand", "Builders", "Farmers", "Soldiers",
            "Seekers", "Sun", "Moon", "Scripture", "Dragon", "Historians", "Teachers", "Thieves", "Idiots",
            "Spies", "Tribesmen", "Eagles", "Crows", "Chosen", "Remnants", "Revenants", "Servants", "Holy Order",
            "Achievers", "Winners", "Losers", "Knights", "Criminals", "Evils", "Commanders", "Hunters", "Conquerors",
            "Exiles", "Outcasts", "Founders", "Good", "Ideals", "Empire", "Imperial", "Travellers", "Huntsmen",
            "Guardians", "Wall", "Force", "Students", "Devotees", "Firepower", "Escalation", "Elite", "Kingdom",
            "Fiefdom", "Dukes", "Lords", "Age", "Papacy", "Temple", "Church", "Order", "Cabal", "Cult", "Lore",
            "Legend", "Myth", "Crusade", "Crusaders", "Tower", "Fortress"
    };

    protected static String[] adjectives = {
            "Radiant", "Shining", "Destined", "Divine", "Screaming", "Prophesized", "Pure", "Golden", "Tarnished", "Gleaming", "Final", "Dark",
            "Last", "Precious", "Imploding", "Powerful", "Ancient", "Leaping", "Fleeing", "First", "Doomed", "Blessed", "Cursed", "Liquid",
            "Flowing", "Blind", "Mythical", "Guiding", "Stout", "Wandering", "Lost", "Apocryphal", "Stunning", "Powerful", "Guilty", "Burning",
            "Rusted", "Forsaken", "Frozen", "Derelict", "New", "Old", "Abandoned", "Deadly", "Shiny", "Dull", "Scheming", "Dead", "Living",
            "Armored", "Frail", "Elder", "Primeval", "Lucid", "Impure", "Drunken", "Tortured", "Nascent", "Incomprehensible", "Electric",
            "Gaping", "Thinking", "Furious", "Lucky", "Unlucky", "Simple", "Complex", "Hydraulic", "Pneumatic", "Stifled", "Caged", "Weak",
            "Wealthy", "Enchanted", "Freed", "Failed", "Impenetrable", "Piercing", "Metal", "Sharp", "Destroying", "Bright", "Hopeful",
            "True", "False", "Beginning", "Ending", "Wounded", "Hidden", "Still", "Silent", "Fighting", "Toppled", "Sickened", "Reborn",
            "Thieving", "Insightful", "Healing", "Collapsed", "Inconvenient", "Alluring", "Evolved", "Scorching", "Ashen", "Deaf", "Genius",
            "Fractured", "Indestructible", "Brutal", "Forgiven", "Made in", "Dying", "Fallen", "Unstoppable", "Open", "Progenitor", "Floating",
            "Obsolete", "Cut", "Limping", "Forbidden", "Legendary", "Multi", "Carrier", "Second", "Timeless", "Classic", "Painted", "Toxic",
            "Repulsive", "Stolen", "Waiting", "Cowering", "Charging", "Collapsing", "Evil", "Hardened", "Rabid", "Double", "Long", "Berserk",
            "Barren", "Brazen", "Hated", "Hollow", "Branded", "Whole", "Holy", "Unholy", "Bought", "Sold", "Captured", "Deep", "Sleeping",
            "Shallow", "Honest", "Young", "Drowning", "Bizarre", "Heroic", "Forced", "Voluntary", "Violent", "Peaceful", "Weeping",
            "Sons of the", "The Greater", "The Lesser"
    };

    private boolean positionIsOdd(int i) {
        return i % 2 == 0;
    }

    public String getNoun() {
        // Generate a random positive adjective
        Random rand = new Random();
        return nouns[rand.nextInt(nouns.length)];
    }

    public String getAdj() {
        // Generate a random positive adjective
        Random rand = new Random();
        return adjectives[rand.nextInt(adjectives.length)];
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public String newName() {
        Random rand = new Random();
        String message;
        String word1 = "null";
        String word2 = "null";

        if(rand.nextBoolean())
        {
            while(Objects.equals(word1, word2))
            {
                word1 = getNoun();
                word2 = getNoun();
            }

            message = word1 + "'s " + word2;

            return message;
        }else{

            while(Objects.equals(word1, word2))
            {
                word1 = getAdj();
                word2 = getNoun();
            }


            message = word1 + " " + word2;

            return message;
        }
    }

    private char getConsonant(Random randomNumberGenerator) {
        for (;;) {
            char currentCharacter = (char) (randomNumberGenerator
                    .nextInt(diffBetweenAtoZ) + charValueOfa);
            if (currentCharacter == 'a' || currentCharacter == 'e'
                    || currentCharacter == 'i' || currentCharacter == 'o'
                    || currentCharacter == 'u')
                continue;
            else
                return currentCharacter;
        }
    }

    private char getVowel(Random randomNumberGenerator) {
        return vowels[randomNumberGenerator.nextInt(vowels.length)];
    }

    public String newCivName() {
        int length = rand.nextInt(6,11);

        for (;;) {
            Random randomNumberGenerator = new Random(Calendar.getInstance()
                    .getTimeInMillis());

            char[] nameInCharArray = new char[length];

            for (int i = 0; i < length; i++) {
                if (positionIsOdd(i)) {
                    nameInCharArray[i] = getVowel(randomNumberGenerator);
                } else {
                    nameInCharArray[i] = getConsonant(randomNumberGenerator);
                }
            }
            nameInCharArray[0] = Character
                    .toUpperCase(nameInCharArray[0]);

            String currentGeneratedName = new String(nameInCharArray);

            if (!currentGeneratedName.equals(lastGeneratedName)) {
                lastGeneratedName = currentGeneratedName;
                return currentGeneratedName;
            }

        }
    }

    public static void main(String[] args)
    {
        NameGenerator testGen = new NameGenerator();
        while(true){
            System.out.println(testGen.newName());
            wait(850);
        }
    }

}
