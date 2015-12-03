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

import java.util.*;

public class Dictionary {
    public List<List<ParadigmRule>> paradigms = new ArrayList<>();
    public List<LexemeRec> lexemeRecs = new ArrayList<>();
    public List<String> prefixeParadigms = new ArrayList<>();

    public Dictionary(List<List<ParadigmRule>> paradigms, List<LexemeRec> lexemeRecs, List<String> prefixeParadigms) {
        this.paradigms = paradigms;
        this.lexemeRecs = lexemeRecs;
        this.prefixeParadigms = prefixeParadigms;
    }

    
//    public void iterateLexemes(BiFunction<String, Lexeme, Void> f) {
//        for (LexemeRec lexemeRec : lexemeRecs) {
//            List<ParadigmRule> paradigmRules = paradigms.get(lexemeRec.paradigmNum);
//
//            // Create lexeme
//            Lexeme lex = new Lexeme();
//            ParadigmRule firstEnding = paradigmRules.get(0);
//
//            // Get basic ancodes and endings, create main wordform
//            lex.setBase(new WordForm(
//                    firstEnding.prefix,
//                    lexemeRec.basis,
//                    firstEnding.ending,
//                    firstEnding.ancode
//            ));
//
//            // Write common ancode
//            if(lexemeRec.ancode!=null)
//            {
//                lex.setCommonAn(lexemeRec.ancode);
//            }
//
//            for (ParadigmRule flexMatchinRecord : paradigmRules) {
//                // For each ancode create wordform and register in lexeme as homonym
//                lex.AddOmonim(new WordForm(
//                        flexMatchinRecord.prefix,
//                        lexemeRec.basis,
//                        flexMatchinRecord.ending,
//                        flexMatchinRecord.ancode
//                ));
//            }
//
//            for (WordForm wordForm : lex.getOmonims()) {
//                f.apply(wordForm.getPrefix() + wordForm.getBase() + wordForm.getEnding(), lex);
//            }
//        }
//    }



//    public List<String> allForms() {
//
//        ArrayList<String> result = new ArrayList<>(10000000);
//        for (Map.Entry<String, List<LexemeRec>> entry : baseToLexemes.entrySet()) {
//            String base = entry.getKey();
//            for (LexemeRec lexemeRec : entry.getValue()) {
//                List<ParadigmRule> paradigmRule = paradigms.get(lexemeRec.paradigmNum);
//                for (ParadigmRule ruleRecord : paradigmRule) {
//                    result.add(ruleRecord.prefix + base + ruleRecord.ending);
//                }
//            }
//        }
//        return result;
//    }

}
