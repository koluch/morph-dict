/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.morphDict.lookup;

import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * Lexeme. Lexeme is word with defined morphological properties and which bring to normal form
 *
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
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
