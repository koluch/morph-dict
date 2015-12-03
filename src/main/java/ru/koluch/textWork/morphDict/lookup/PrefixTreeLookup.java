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
package ru.koluch.textWork.morphDict.lookup;


import ru.koluch.textWork.morphDict.dictionary.*;
import ru.koluch.textWork.morphDict.dictionary.Dictionary;
import ru.koluch.textWork.morphDict.prefixTree.PrefixTree;
import ru.koluch.textWork.morphDict.dictionary.TreeBuilder;

import java.util.*;

public class PrefixTreeLookup implements Lookup{


    private final Dictionary dictionary;
    private final PrefixTree<TreeBuilder.TreeData> prefixTree;

    public PrefixTreeLookup(Dictionary dictionary, PrefixTree<TreeBuilder.TreeData> prefixTree) {
        this.dictionary = dictionary;
        this.prefixTree = prefixTree;
    }

    /**
     * Search wordform in dictionary
     *
     * @param tofind arbitrary wordform
     * @return list of lexemes-homonym, containing specified wordform
     */
    @Override
    public ArrayList<LookupResult> find(String tofind)
    {
        ArrayList<LookupResult> lookupResultList = new ArrayList<>();

        List<TreeBuilder.TreeData> treeDataList = prefixTree.get(tofind).get();//todo: check for null
        for (TreeBuilder.TreeData treeData : treeDataList) {
            LexemeRec lexemeRec = dictionary.lexemeRecs.get(treeData.lexemeRecNum);
            Optional<String> commonAncode = lexemeRec.ancode;
            List<ParadigmRule> paradigmRules = dictionary.paradigms.get(lexemeRec.paradigmNum);
            Optional<String> globalPrefix = lexemeRec.prefixParadigmNum.map(dictionary.prefixeParadigms::get);

            // Build found wordform
            ParadigmRule foundParadigmRule = paradigmRules.get(treeData.paradigmNum);
            WordForm foundWordForm = new WordForm(
                Optional.of(globalPrefix.orElse("") + foundParadigmRule.prefix.orElse("")),
                lexemeRec.basis,
                foundParadigmRule.ending,
                foundParadigmRule.ancode
            );

            // Build lexeme
            ArrayList<WordForm> omonims = new ArrayList<>();
            for (ParadigmRule paradigmRule : paradigmRules) {
                omonims.add(new WordForm(
                        Optional.of(globalPrefix.orElse("") + paradigmRule.prefix.orElse("")),
                        lexemeRec.basis,
                        paradigmRule.ending,
                        paradigmRule.ancode
                ));
            }
            Lexeme lexeme = new Lexeme(omonims, commonAncode);

            lookupResultList.add(new LookupResult(foundWordForm, lexeme));
        }

        return lookupResultList;
    }


}
