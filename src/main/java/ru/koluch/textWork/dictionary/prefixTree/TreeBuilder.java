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
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.dictionary.prefixTree;

import ru.koluch.textWork.dictionary.Dictionary;
import ru.koluch.textWork.dictionary.Lexeme;
import ru.koluch.textWork.dictionary.LexemeRec;
import ru.koluch.textWork.dictionary.ParadigmRule;

import java.util.List;
import java.util.Optional;

public class TreeBuilder {

    public PrefixTree<TreeData> build(Dictionary dictionary) {
        PrefixTree<TreeData> result = new PrefixTree<>();
        for (LexemeRec lexemeRec : dictionary.lexemeRecs) {
            List<ParadigmRule> paradigmRules = dictionary.paradigms.get(lexemeRec.paradigmNum);
            String superPrefix = lexemeRec.prefixParadigmNum.map(dictionary.prefixes::get).orElse("");

            for (ParadigmRule paradigmRule : paradigmRules) {
                String wordForm = superPrefix + lexemeRec.basis + paradigmRule.ending.orElse("");
                if(!(wordForm.contains("#") || wordForm.contains("-"))) {
                    TreeData treeData = new TreeData(paradigmRule.ancode, lexemeRec);
                    result.add(wordForm, treeData);
                }
            }
        }

        return result;
    }


    public static class TreeData {
        public final String ancode;
        public final LexemeRec lexemeRec;

        public TreeData(String ancode, LexemeRec lexemeRec) {
            this.ancode = ancode;
            this.lexemeRec = lexemeRec;
        }
    }


}
