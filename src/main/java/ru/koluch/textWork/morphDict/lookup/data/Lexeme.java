/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.morphDict.lookup.data;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Lexeme is word, containing all word forms and common attributes (through common ancode)
 * <p>
 * Immutable data-class
 */
public class Lexeme {

    
    /**
     * Homonyms of current wordform
     */
    public final ArrayList<WordForm> omonims;
    

    /**
     * Common ancode for lexeme
     */
    public final Optional<String> commonAncode;
    
    public Lexeme(ArrayList<WordForm> omonims, Optional<String> commonAn) {
        this.omonims = omonims;
        this.commonAncode = commonAn;
    }

}
