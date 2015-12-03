/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.morphDict.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.koluch.textWork.morphDict.prefixTree.PrefixTree;

public class DictionaryHelper {

    /*
        Parsing
     */

    public static Dictionary parse(Reader reader) throws ParseException {



        /**
         * Dictionary loading
         */
        try(BufferedReader fin = new BufferedReader(reader)) {


            // Load flexias models
            List<List<ParadigmRule>> allRules = new ArrayList<>();
            int num = Integer.valueOf(fin.readLine());
            Pattern paradigmListEx = Pattern.compile("\\%([^\\%]+)");
            Pattern paradigmEx = Pattern.compile("([^\\*]+)?\\*([^\\*]+)(?:\\*([^\\*]+))?");
            for (int i = 0; i < num; ++i) {
                String nextString = fin.readLine();
                Matcher matcher = paradigmListEx.matcher(nextString);

                List<ParadigmRule> paradigmRules = new ArrayList<>();
                while(matcher.find())
                {
                    String paradigmString = matcher.group(1);
                    Matcher paradigmMatcher = paradigmEx.matcher(paradigmString);
                    if(paradigmMatcher.find())
                    {
                        String ending = paradigmMatcher.group(1);
                        String ancode = paradigmMatcher.group(2);  // Ancode is Anoshkin's code
                        String prefix = paradigmMatcher.group(3);

                        paradigmRules.add(new ParadigmRule(
                                Optional.ofNullable(ending).map(String::toLowerCase),
                                ancode,
                                Optional.ofNullable(prefix).map(String::toLowerCase)
                        ));
                    }
                }

                allRules.add(paradigmRules);
            }

            // Skip: accents, journal... //todo:implement
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                fin.readLine();
            }
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                fin.readLine();
            }

            // Read prefixes
            ArrayList<String> prefixes = new ArrayList<>();
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                prefixes.add(fin.readLine().toLowerCase());
            }

            // Read lexemes
            List<LexemeRec> lexemeRecs = new ArrayList<>();
            num = Integer.decode(fin.readLine());

            for (int i = 0; i < num; ++i) {
                String lem = fin.readLine();

                String[] lemParts = lem.split(" ");

                String basis = lemParts[0];
                Integer paradigmNum = Integer.valueOf(lemParts[1]);
                Integer accentParadigmNum = Integer.valueOf(lemParts[2]);
                Integer userSessionNum = Integer.valueOf(lemParts[3]);
                String anc = lemParts[4].equals("-") ? null : lemParts[4];
                Integer prefixParadigmNum = lemParts[5].equals("-") ? null : Integer.valueOf(lemParts[5]);

                lexemeRecs.add(new LexemeRec(
                        basis.toLowerCase(),
                        paradigmNum,
                        Optional.ofNullable(anc),
                        accentParadigmNum,
                        userSessionNum,
                        Optional.ofNullable(prefixParadigmNum)
                ));

            }

            return new Dictionary(allRules, lexemeRecs, prefixes);

        } catch (IOException ex) {
            throw new ParseException(ex);
        }
    }


    public static class ParseException extends Exception {
        public ParseException() {
        }

        public ParseException(String message) {
            super(message);
        }

        public ParseException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParseException(Throwable cause) {
            super(cause);
        }

        public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

    /*
        Building prefix tree from dictionary
     */

    public static PrefixTree<TreeData> buildPrefixTree(Dictionary dictionary) {
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
