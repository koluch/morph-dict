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
package ru.koluch.textWork.morphDict.dictionary;

import java.util.Iterator;
import java.util.List;

import ru.koluch.textWork.morphDict.prefixTree.PrefixTree;

public class TreeBuilder {

    public PrefixTree<TreeData> build(Dictionary dictionary) {
        PrefixTree<TreeData> result = new PrefixTree<>();

        Iterator<LexemeRec> lexemeRecIterator = dictionary.lexemeRecs.iterator();
        int lexemeRecNum = 0;
        while (lexemeRecIterator.hasNext()) {
            LexemeRec lexemeRec = lexemeRecIterator.next();
            List<ParadigmRule> paradigmRules = dictionary.paradigms.get(lexemeRec.paradigmNum);
            String superPrefix = lexemeRec.prefixParadigmNum.map(dictionary.prefixeParadigms::get).orElse("");

            Iterator<ParadigmRule> paragirmRuleIterator = paradigmRules.iterator();
            int paradigmNum = 0;
            while (paragirmRuleIterator.hasNext()) {
                ParadigmRule paradigmRule = paragirmRuleIterator.next();
                String wordForm = superPrefix + lexemeRec.basis + paradigmRule.ending.orElse("");
                if(!(wordForm.contains("#") || wordForm.contains("-"))) { //todo: fix
                    TreeData treeData = new TreeData(paradigmNum, lexemeRecNum);
                    result.add(wordForm, treeData);
                }
                paradigmNum++;
            }
            lexemeRecNum++;
        }

        return result;
    }


    public static class TreeData {
        public final Integer lexemeRecNum;
        public final Integer paradigmNum;

        public TreeData(Integer paradigmNum, Integer lexemeRecNum) {
            this.paradigmNum = paradigmNum;
            this.lexemeRecNum = lexemeRecNum;
        }
    }


}
