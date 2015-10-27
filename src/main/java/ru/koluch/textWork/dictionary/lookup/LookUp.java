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
 * Created: 28.10.2015 00:42
 */
package ru.koluch.textWork.dictionary.lookup;


import ru.koluch.textWork.dictionary.*;
import ru.koluch.textWork.dictionary.Dictionary;

import java.util.*;

public class Lookup {


    private final Dictionary dictionary;

    public Lookup(ru.koluch.textWork.dictionary.Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Search wordform in dictionary
     *
     * @param tofind arbitrary wordform
     * @return list of lexemes-homonym, containing specified wordform
     */
    public ArrayList<Lexeme> find(String tofind)
    {
        HashMap flex_mod;
        HashSet flex_params;
        WordForm form;
        String base, flex, ancode, buf1, buf2;
        Iterator it;

        tofind = tofind.toUpperCase(); // Make it uppercase

        ArrayList<Lexeme> result = new ArrayList<Lexeme>();

        List<LexemeRec> ending_list;
        // Iterate all sub-string of source word from beggining to the i-th symbol
        for(int i=1, len=tofind.length(); i<=len; ++i)
        {
            base = tofind.substring(0, i);
            flex = tofind.substring(i);

            // If current state is not registered yet
            if(dictionary.baseToLexemes.containsKey(base))
            {

                // Get list of models for current ending
                ending_list = dictionary.baseToLexemes.get(base);

                for (LexemeRec lexemeRec : ending_list) {
//                    // If model with this number it not registered yet - break
//                    if(lexemeRec.mod==-1)
//                    {
//                        break;
//                    }
                    ParadigmRule paradigmRule = dictionary.allRules.get(lexemeRec.mod);

                    // If model contains current ending
                    if(paradigmRule.getEndingsToAncodes().containsKey(flex))
                    {
                        // Create lexeme
                        Lexeme lex = new Lexeme();

                        // Get basic ancodes and endings, create main wordform
                        buf1 = paradigmRule.getFirstAncode();
                        buf2 = paradigmRule.getFirstEnding();
                        form = new WordForm();
                        form.setAncode(buf1);
                        form.setBase(base);
                        form.setFlexion(buf2);
                        lex.setBase(form);

                        // Write common ancode
                        if(lexemeRec.ancode!=null)
                        {
                            lex.setCommonAn(lexemeRec.ancode);
                        }
                        else
                        {
                            lex.setCommonAn(" -");
                        }

                        // Get list of ancods for current ending
                        List<String> ancodes = paradigmRule.getEndingsToAncodes().get(flex);

                        for (String nextAncode : ancodes) {
                            // For each ancode create wordform and register in lexeme as homonym
                            form = new WordForm();

                            form.setAncode(nextAncode);
                            form.setBase(base);
                            form.setFlexion(flex);

                            lex.AddOmonim(form);
                        }

                        result.add(lex);
                    }
                }
            }
        }

        return result;
    }


}