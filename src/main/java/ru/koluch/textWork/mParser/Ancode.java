/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.mParser;

/**
 * Class for describe single Anoshkin's code
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class Ancode {
    
    /**
     * Ancode parameters
     */
    private char values[];

    public Ancode() {
        this.values = new char[22];
        for(int i=0; i<=21; ++i)
        {
            this.values[i] = '-';
        }    
    }
    public char[] getValues() {
        return values;
    }

    public void setValues(char[] values) {
        this.values = values;
    }

}
