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
package ru.koluch.textWork.dictionary;

import ru.koluch.textWork.dictionary.lookup.Lexeme;
import ru.koluch.textWork.dictionary.lookup.LexemeRec;

import java.util.*;

public class Dictionary {
    public ArrayList<ParadigmRule> allRules = new ArrayList<>();
    public HashMap<String, List<LexemeRec>> baseToLexemes = new HashMap<>();

    public Dictionary(ArrayList<ParadigmRule> allRules, HashMap<String,  List<LexemeRec>> baseToLexemes) {
        this.allRules = allRules;
        this.baseToLexemes = baseToLexemes;
    }

    public void allForms() {
        int counter = 0;
        for (Map.Entry<String, List<LexemeRec>> entry : baseToLexemes.entrySet()) {
            String base = entry.getKey();
            for (LexemeRec lexemeRec : entry.getValue()) {
                ParadigmRule paradigmRule = allRules.get(lexemeRec.mod);
                for (Map.Entry<String,List<String>> endingToAncodes : paradigmRule.getEndingsToAncodes().entrySet()) {
                    String ending = endingToAncodes.getKey();
                    System.out.println(base + ending);
                    counter++;
                }
            }
        }
        System.out.println(counter);
    }

}
