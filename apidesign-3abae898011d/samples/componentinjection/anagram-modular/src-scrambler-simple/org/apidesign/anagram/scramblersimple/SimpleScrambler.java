package org.apidesign.anagram.scramblersimple;

import java.util.Random;
import org.apidesign.anagram.api.Scrambler;

public class SimpleScrambler implements Scrambler {
    private static final Random random = new Random();

    public String scramble(String word) {
        for (;;) {
            int index1 = random.nextInt(word.length());
            int index2 = random.nextInt(word.length());

            if (index1 == index2) {
                continue;
            }

            char char1 = word.charAt(index1);
            char char2 = word.charAt(index2);
            
            StringBuilder sb = new StringBuilder(word);
            sb.setCharAt(index1, char2);
            sb.setCharAt(index2, char1);
            return sb.toString();
        }
    }

}
