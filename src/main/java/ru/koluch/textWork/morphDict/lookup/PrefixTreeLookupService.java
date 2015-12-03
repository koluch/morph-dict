/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 28.10.2015 00:42
 */
package ru.koluch.textWork.morphDict.lookup;


import ru.koluch.textWork.morphDict.dictionary.*;
import ru.koluch.textWork.morphDict.dictionary.Dictionary;
import ru.koluch.textWork.morphDict.prefixTree.PrefixTree;
import ru.koluch.textWork.morphDict.dictionary.DictionaryHelper;

import java.util.*;

public class PrefixTreeLookupService implements LookupService {


    private final Dictionary dictionary;
    private final PrefixTree<DictionaryHelper.TreeData> prefixTree;

    public PrefixTreeLookupService(Dictionary dictionary, PrefixTree<DictionaryHelper.TreeData> prefixTree) {
        this.dictionary = dictionary;
        this.prefixTree = prefixTree;
    }

    /**
     * Search wordform in dictionary
     *
     * @param toFind arbitrary wordform
     * @return list of lexemes-homonym, containing specified wordform
     */
    @Override
    public ArrayList<LookupResult> find(String toFind)
    {
        ArrayList<LookupResult> lookupResultList = new ArrayList<>();

        List<DictionaryHelper.TreeData> treeDataList = prefixTree.get(toFind).get();//todo: check for null
        for (DictionaryHelper.TreeData treeData : treeDataList) {
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
