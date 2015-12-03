/**
 * --------------------------------------------------------------------
 * Copyright 2015 Nikolay Mavrenkov
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------
 * <p/>
 * Author:  Nikolay Mavrenkov <koluch@koluch.ru>
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
