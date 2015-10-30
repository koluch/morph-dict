/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.dictionary;

/**
 * Record in lexemes table
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class LexemeRec {
    public int paradigmNumber;
    public String ancode;

    public LexemeRec() {
    }

    public LexemeRec(int paradigmNumber, String ancode) {
        this.paradigmNumber = paradigmNumber;
        this.ancode = ancode;
    }

    
}
