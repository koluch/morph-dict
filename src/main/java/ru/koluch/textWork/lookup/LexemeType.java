/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.lookup;

/**
 *
 * Lexical type of lexeme
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public enum LexemeType {
    NOUN(1),
    VERB(2),
    ADJECTIVE(3),
    ADVERB(4),
    PRONOUN(5), 
    UNDEF(6), 
    PUNCTUATION(7), 
    PREPOSITION(8), 
    GERUND(9),
    NUMERAL(10),
    PARTICIPLE(11),
    INFINITIVE(12),
    PRONOUN_ADJECTIVE(13)
    ; 

    private int id;

    LexemeType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    
    
}
