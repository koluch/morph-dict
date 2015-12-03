/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 28.10.2015 00:42
 */
package ru.koluch.morphDict.lookup.data;

import java.util.Optional;

/**
 * Beside of word form itself, this class makes possible to get word form attributes using ancode
 */
public class WordForm {
    
    public final Optional<String> prefix;
    public final String base;
    public final Optional<String> ending;
    public final String ancode;
    

    public WordForm(Optional<String> prefix, String base, Optional<String> ending, String ancode) {
        this.prefix = prefix;
        this.base = base;
        this.ending = ending;
        this.ancode = ancode;
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
