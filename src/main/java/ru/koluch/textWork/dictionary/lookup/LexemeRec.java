/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.dictionary.lookup;

/**
 * Record in lexemes table
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class LexemeRec {
    public int mod;
    public String ancode;

    public LexemeRec() {
    }

    public LexemeRec(int mod, String ancode) {
        this.mod = mod;
        this.ancode = ancode;
    }

    
}
