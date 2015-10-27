/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.dictionaryParser;

/**
 * Wordform. Stores ending and ancode
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class WordForm {
    
    private String base;
    
    private String flexion;
    
    private String ancode;
    
    public WordForm() {
    }

    public WordForm(String ancode) {
        this.ancode = ancode;
    }
    
    public WordForm(String base, String flexion, String ancode) {
        this.base = base;
        this.flexion = flexion;
        this.ancode = ancode;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    
    public void setFlexion(String flexion) {
        this.flexion = flexion;
    }

    public String getFlexion() {
        return flexion;
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
        result.append(flexion);
        result.append('^');
        result.append(ancode);
        return result.toString();
    }


    
    
}
