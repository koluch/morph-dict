/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 28.10.2015 00:42
 */
package ru.koluch.morphDict.lookup;


import ru.koluch.morphDict.dictionary.data.Dictionary;
import ru.koluch.morphDict.dictionary.data.LexemeRec;
import ru.koluch.morphDict.dictionary.data.ParadigmRule;
import ru.koluch.morphDict.lookup.data.Lexeme;
import ru.koluch.morphDict.lookup.data.LookupResult;
import ru.koluch.morphDict.lookup.data.WordForm;
import ru.koluch.morphDict.prefixTree.PrefixTree;
import ru.koluch.morphDict.dictionary.DictionaryHelper;

import java.util.*;

/**
 * Implementation of lookup service using prefix-tree represenation
 */
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
    public ArrayList<LookupResult> lookup(String toFind)
    {
        ArrayList<LookupResult> lookupResultList = new ArrayList<>();

        Optional<List<DictionaryHelper.TreeData>> treeDataListOpt = prefixTree.get(toFind);
        if(treeDataListOpt.isPresent()) {
            List<DictionaryHelper.TreeData> treeDataList = treeDataListOpt.get();
            for (DictionaryHelper.TreeData treeData : treeDataList) {
                LexemeRec lexemeRec = dictionary.lexemeRecs.get(treeData.lexemeRecIndex);
                Optional<String> commonAncode = lexemeRec.ancode;
                List<ParadigmRule> paradigmRules = dictionary.paradigmList.get(lexemeRec.paradigmIndex);
                Optional<String> globalPrefix = lexemeRec.prefixParadigmIndex.map(dictionary.prefixeParadigmList::get);

                // Build found word form
                ParadigmRule foundParadigmRule = paradigmRules.get(treeData.paradigmRuleIndex);
                WordForm foundWordForm = new WordForm(
                        Optional.of(globalPrefix.orElse("") + foundParadigmRule.prefix.orElse("")),
                        lexemeRec.basis,
                        foundParadigmRule.ending,
                        foundParadigmRule.ancode
                );

                // Build lexeme
                ArrayList<WordForm> homonyms = new ArrayList<>();
                for (ParadigmRule paradigmRule : paradigmRules) {
                    homonyms.add(new WordForm(
                            Optional.of(globalPrefix.orElse("") + paradigmRule.prefix.orElse("")),
                            lexemeRec.basis,
                            paradigmRule.ending,
                            paradigmRule.ancode
                    ));
                }
                Lexeme lexeme = new Lexeme(homonyms, commonAncode);

                lookupResultList.add(new LookupResult(foundWordForm, lexeme));
            }
        }

        return lookupResultList;
    }


}
