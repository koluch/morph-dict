/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 27.10.2015 23:07
 */
package ru.koluch.textWork.morphDict.dictionary;

import ru.koluch.textWork.morphDict.lookup.Lexeme;
import ru.koluch.textWork.morphDict.lookup.WordForm;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Dictionary {
    public List<List<ParadigmRule>> paradigms = new ArrayList<>();
    public List<LexemeRec> lexemeRecs = new ArrayList<>();
    public List<String> prefixeParadigms = new ArrayList<>();

    public Dictionary(List<List<ParadigmRule>> paradigms, List<LexemeRec> lexemeRecs, List<String> prefixeParadigms) {
        this.paradigms = paradigms;
        this.lexemeRecs = lexemeRecs;
        this.prefixeParadigms = prefixeParadigms;
    }

    
    public void iterateLexemes(Consumer<Lexeme> consumer) {
        for (LexemeRec lexemeRec : lexemeRecs) {
            List<ParadigmRule> paradigmRules = paradigms.get(lexemeRec.paradigmNum);
            ArrayList<WordForm> omonims = new ArrayList<>();

            for (ParadigmRule flexMatchinRecord : paradigmRules) {
                // For each ancode create wordform and register in lexeme as homonym
                omonims.add(new WordForm(
                        flexMatchinRecord.prefix,
                        lexemeRec.basis,
                        flexMatchinRecord.ending,
                        flexMatchinRecord.ancode
                ));
            }

            // Create lexeme and pass it to callback
            consumer.accept(new Lexeme(omonims, lexemeRec.ancode));
        }
    }

}
