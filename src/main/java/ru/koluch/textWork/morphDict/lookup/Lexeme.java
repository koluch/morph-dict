/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.morphDict.lookup;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * Lexeme. Lexeme is word with defined morphological properties and which bring to normal form
 *
 */
public class Lexeme {

    
    /**
     * Homonyms of current wordform
     */
    ArrayList<WordForm> omonims;
    

    /**
     * Common ancode for lexeme
     */
    Optional<String> commonAncode;
    

    public Lexeme(ArrayList<WordForm> omonims, Optional<String> commonAn) {
        this.omonims = omonims;
        this.commonAncode = commonAn;
    }

    public void setOmonims(ArrayList<WordForm> omonims) {
        this.omonims = omonims;
    }

    public ArrayList<WordForm> getOmonims() {
        return omonims;
    }

    public void setCommonAncode(Optional<String> commonAncode) {
        this.commonAncode = commonAncode;
    }

    public Optional<String> getCommonAncode() {
        return commonAncode;
    }



}
