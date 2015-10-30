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
    public final String basis;
    public final Integer paradigmNum;
    public final String ancode;
    public final Integer accentParadigmNum;
    public final Integer userSessionNum;
    public final String prefix;


    public LexemeRec(String basis, Integer paradigmNum, String ancode, Integer accentParadigmNum, Integer userSessionNum, String prefix) {
        this.basis = basis;
        this.paradigmNum = paradigmNum;
        this.ancode = ancode;
        this.accentParadigmNum = accentParadigmNum;
        this.userSessionNum = userSessionNum;
        this.prefix = prefix;
    }
}
