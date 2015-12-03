/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 28.10.2015 00:42
 */
package ru.koluch.textWork.morphDict.lookup;

import java.util.Optional;

/**
 * Wordform. Stores ending and ancode
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class WordForm {
    
    private Optional<String> prefix;
    
    private String base;
    
    private Optional<String> ending;
    
    private String ancode;
    

    public WordForm(Optional<String> prefix, String base, Optional<String> ending, String ancode) {
        this.prefix = prefix;
        this.base = base;
        this.ending = ending;
        this.ancode = ancode;
    }

    public Optional<String> getPrefix() {
        return prefix;
    }

    public void setPrefix(Optional<String> prefix) {
        this.prefix = prefix;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    
    public void setEnding(Optional<String> ending) {
        this.ending = ending;
    }

    public Optional<String> getEnding() {
        return ending;
    }
   
    
    public void setAncode(String ancode) {
        this.ancode = ancode;
    }

    public String getAncode() {
        return ancode;
    }

    @Override
    public String toString() {
        return "WordForm{" +
                "prefix=" + prefix +
                ", base='" + base + '\'' +
                ", ending=" + ending +
                ", ancode='" + ancode + '\'' +
                '}';
    }

    public String makeWord() {
        return prefix.orElse("") +base + ending.orElse("");
    }
}
