/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.dictionary;

/**
 * Wordform. Stores ending and ancode
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class WordForm {
    
    private String prefix;
    
    private String base;
    
    private String ending;
    
    private String ancode;
    
    public WordForm() {
    }

    public WordForm(String ancode) {
        this.ancode = ancode;
    }
    
    public WordForm(String prefix, String base, String ending, String ancode) {
        this.prefix = prefix != null ? prefix : "";
        this.base = base;
        this.ending = ending;
        this.ancode = ancode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    
    public void setEnding(String ending) {
        this.ending = ending;
    }

    public String getEnding() {
        return ending;
    }
   
    
    public void setAncode(String ancode) {
        this.ancode = ancode;
    }

    public String getAncode() {
        return ancode;
    }
    
    public String getNormalForm()
    {
        StringBuilder result = new StringBuilder();
        result.append('|');
        result.append(ending);
        result.append('^');
        result.append(ancode);
        return result.toString();
    }

    @Override
    public String toString() {
        return "WordForm{"
                +prefix
                +base
                +ending
        +"}";
    }
}
