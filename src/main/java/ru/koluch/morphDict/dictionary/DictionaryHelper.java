/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.morphDict.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.koluch.morphDict.dictionary.data.Dictionary;
import ru.koluch.morphDict.dictionary.data.LexemeRec;
import ru.koluch.morphDict.dictionary.data.ParadigmRule;
import ru.koluch.morphDict.prefixTree.PrefixTree;
import static ru.koluch.morphDict.dictionary.Attribute.*;

public class DictionaryHelper {

    private DictionaryHelper() {

    }

    /*
        Parsing
     */

    /**
     * Parse morphs.mrd file through supplied reader and build dictionary
     *
     * @param reader reader with morphs.mrd file content
     * @return parsed dictionary
     * @throws ParseException thrown when parsing failed
     */
    public static Dictionary parse(Reader reader) throws ParseException {

        try(BufferedReader fin = new BufferedReader(reader)) {

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

    /**
     * Parse dictionary from default resource file
     * @return parsed dictionary
     * @throws ParseException thrown when parsing failed
     */
    public static Dictionary parse() throws ParseException {
        try {
            try (InputStreamReader reader = new InputStreamReader(Dictionary.class.getResourceAsStream("/morphs.mrd"), "UTF-8")) {
                return parse(reader);
            }
        } catch (IOException e) {
            throw new ParseException("Unexpected exception while parsing morphs.mrd file", e);
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
            List<ParadigmRule> paradigmRules = dictionary.paradigmList.get(lexemeRec.paradigmIndex);
            String superPrefix = lexemeRec.prefixParadigmIndex.map(dictionary.prefixeParadigmList::get).orElse("");

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

    /*
        Working with attributes
     */

    /**
     * Map of ancodes to sets of attributes build according to rgramtab.tab aot.ru file
     */
    private final static Map<String, Set<Attribute>> ancodeToAttributes = new HashMap<>();
    static {
        ancodeToAttributes.put("аа", new HashSet<>(Arrays.asList(NOUN, MASCULINE_GENDER, SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("аб", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Эф", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL)));
        ancodeToAttributes.put("ав", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("аг", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ад", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ае", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Эх", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL)));
        ancodeToAttributes.put("ас", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, VOCATIVE_CASE)));
        ancodeToAttributes.put("аж", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("аз", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("аи", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("ай", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ак", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ал", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ам", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,IMMUTABLE)));
        ancodeToAttributes.put("ан", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR,IMMUTABLE)));
        ancodeToAttributes.put("Юо", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Юп", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Юр", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Юс", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Ют", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Юф", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Юх", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, VOCATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яб", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яа", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яв", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яг", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яд", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("Яж", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("го", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гп", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гр", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гс", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гт", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("гу", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("гф", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, NOMINATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гх", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, GENITIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гц", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, DATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гч", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, ACCUSATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("гш", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, INSTRUMENTAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("гщ", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PLURAL, PREPOSITIONAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("ва", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вб", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("вв", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("вг", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вд", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ве", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("вж", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вз", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("ви", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("вй", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вк", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("вл", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("вм", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,IMMUTABLE)));
        ancodeToAttributes.put("вн", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,SINGULAR,IMMUTABLE)));
        ancodeToAttributes.put("во", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вп", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("вр", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("вс", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вт", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ву", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("вф", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вх", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("вц", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("вч", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вш", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("вщ", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,COMMON_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("га", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("гб", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("гв", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("гг", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("гд", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ге", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Эч", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL)));
        ancodeToAttributes.put("Йш", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR, VOCATIVE_CASE)));
        ancodeToAttributes.put("гж", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("гз", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("ги", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("гй", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("гк", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("гл", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("гм", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,IMMUTABLE)));
        ancodeToAttributes.put("гн", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR,IMMUTABLE)));
        ancodeToAttributes.put("Йа", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Йб", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Йв", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Йг", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Йд", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Йе", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Йж", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Йз", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Йи", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Йй", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Йк", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Йл", new HashSet<>(Arrays.asList(NOUN,ARCHAISM,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Йм", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Йн", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Йо", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Йп", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Йр", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Йс", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Йт", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Йу", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Йф", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Йх", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Йц", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Йч", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,FEMININE_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("еа", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("еб", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("ев", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("ег", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ед", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ее", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("еж", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("ез", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("еи", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("ей", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ек", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ел", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ем", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,IMMUTABLE)));
        ancodeToAttributes.put("ен", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR,IMMUTABLE)));
        ancodeToAttributes.put("Эя", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ABBREVIATION)));
        ancodeToAttributes.put("Яз", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Яи", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Як", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Ял", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Ям", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Ян", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Яо", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Яп", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Яр", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Яс", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Ят", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Яу", new HashSet<>(Arrays.asList(NOUN,COLLOQUIAL,NEUTER_GENDER,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("иж", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("из", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("ии", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("ий", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ик", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ил", new HashSet<>(Arrays.asList(NOUN,PLURAL,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("им", new HashSet<>(Arrays.asList(NOUN,PLURAL,IMMUTABLE)));
        ancodeToAttributes.put("ао", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("ап", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("ат", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("ау", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("ац", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("ач", new HashSet<>(Arrays.asList(NOUN,NEUTER_GENDER,SINGULAR,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("аъ", new HashSet<>(Arrays.asList(NOUN,PLURAL,ABBREVIATION,IMMUTABLE)));
        ancodeToAttributes.put("бо", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("бп", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("бр", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("бс", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("бт", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("бу", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("бь", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("бф", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("бх", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("бц", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("бч", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("бш", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("бщ", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("бН", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,GIVEN_NAME,IMMUTABLE)));
        ancodeToAttributes.put("вН", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,IMMUTABLE)));
        ancodeToAttributes.put("вО", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вП", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("вР", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("вС", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вТ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("вУ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("вЬ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("вФ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("вХ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("вЦ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("вЧ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("вШ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("вЩ", new HashSet<>(Arrays.asList(NOUN,COMMON_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("до", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("дп", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("др", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("дс", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("дт", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ду", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("дь", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,SINGULAR, VOCATIVE_CASE,COLLOQUIAL)));
        ancodeToAttributes.put("дф", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("дх", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("дц", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("дч", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("дш", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("дщ", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("дН", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,GIVEN_NAME,IMMUTABLE)));
        ancodeToAttributes.put("Ра", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Рб", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Рв", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Рг", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Рд", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Ре", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Рн", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Ро", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Рп", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Рр", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Рс", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Рт", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Рж", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Рз", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Ри", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Рк", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Рл", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Рм", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Ру", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Рф", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Рх", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Рц", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Рч", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Рш", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Та", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Тб", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Тв", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Тг", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Тд", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Те", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Тн", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("То", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Тп", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Тр", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Тс", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Тт", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Тж", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Тз", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("Ти", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("Тк", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Тл", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Тм", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Ту", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("Тф", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("Тх", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("Тц", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Тч", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("Тш", new HashSet<>(Arrays.asList(NOUN,FEMININE_GENDER,PATRONYMIC,COLLOQUIAL,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("йа", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йб", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йв", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йг", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("Рщ", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("йд", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йе", new HashSet<>(Arrays.asList(ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йж", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йз", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йи", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йй", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йк", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йл", new HashSet<>(Arrays.asList(ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йм", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йн", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йо", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йп", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йр", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йс", new HashSet<>(Arrays.asList(ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йт", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йу", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йф", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йх", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("Рь", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("йц", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йч", new HashSet<>(Arrays.asList(ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йш", new HashSet<>(Arrays.asList(SHORT_ADJECTIVE,MASCULINE_GENDER,SINGULAR,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йщ", new HashSet<>(Arrays.asList(SHORT_ADJECTIVE,FEMININE_GENDER,SINGULAR,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йы", new HashSet<>(Arrays.asList(SHORT_ADJECTIVE,NEUTER_GENDER,SINGULAR,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йэ", new HashSet<>(Arrays.asList(SHORT_ADJECTIVE,PLURAL,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йю", new HashSet<>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йъ", new HashSet<>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,SECOND_GENITIVE_OR_SECOND_PREPOSITIONAL,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("йь", new HashSet<>(Arrays.asList(ADJECTIVE,COMPARATIVE_FORM,ANIMATED,INANIMATED,COLLOQUIAL)));
        ancodeToAttributes.put("йя", new HashSet<>(Arrays.asList(ADJECTIVE,IMMUTABLE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иа", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иб", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ив", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иг", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("ид", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("ие", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гб", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гв", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гг", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гд", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Ге", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гж", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("Гз", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ин", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ио", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ип", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ир", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ис", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ит", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иу", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иф", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("их", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("иц", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("ич", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("иш", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ищ", new HashSet<>(Arrays.asList(ADJECTIVE,SUPERLATIVE_FORM,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("нр", new HashSet<>(Arrays.asList(INFINITIVE,IMPERSONAL)));
        ancodeToAttributes.put("нс", new HashSet<>(Arrays.asList(VERB,IMPERSONAL,FUTURE_TENSE)));
        ancodeToAttributes.put("нт", new HashSet<>(Arrays.asList(VERB,IMPERSONAL,PAST_TENSE)));
        ancodeToAttributes.put("ну", new HashSet<>(Arrays.asList(VERB,IMPERSONAL,PRESENT_TENSE)));
        ancodeToAttributes.put("ка", new HashSet<>(Arrays.asList(INFINITIVE,ACTIVE_VOICE)));
        ancodeToAttributes.put("кб", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR)));
        ancodeToAttributes.put("кв", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL)));
        ancodeToAttributes.put("кг", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR)));
        ancodeToAttributes.put("кд", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL)));
        ancodeToAttributes.put("ке", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR)));
        ancodeToAttributes.put("кж", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL)));
        ancodeToAttributes.put("кз", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,MASCULINE_GENDER,SINGULAR)));
        ancodeToAttributes.put("ки", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,FEMININE_GENDER,SINGULAR)));
        ancodeToAttributes.put("кй", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,NEUTER_GENDER,SINGULAR)));
        ancodeToAttributes.put("кк", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL)));
        ancodeToAttributes.put("кп", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR)));
        ancodeToAttributes.put("кр", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL)));
        ancodeToAttributes.put("кс", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR)));
        ancodeToAttributes.put("кт", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL)));
        ancodeToAttributes.put("ку", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR)));
        ancodeToAttributes.put("кф", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL)));
        ancodeToAttributes.put("Ръ", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("Ры", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Рэ", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("Рю", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Ря", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("кю", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("кя", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("кэ", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("Эа", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Эб", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("Эв", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Эг", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("Эд", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Эе", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эж", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,FIRST_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эз", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эи", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,SECOND_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эй", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эк", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PRESENT_TENSE,THIRD_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эл", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,PAST_TENSE,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эм", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эн", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эо", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эп", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("Эр", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("Эс", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("кн", new HashSet<>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PRESENT_TENSE)));
        ancodeToAttributes.put("ко", new HashSet<>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PAST_TENSE)));
        ancodeToAttributes.put("Эт", new HashSet<>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PRESENT_TENSE,ARCHAISM)));
        ancodeToAttributes.put("Эу", new HashSet<>(Arrays.asList(TRANSGRESSIVE,ACTIVE_VOICE,PAST_TENSE,ARCHAISM)));
        ancodeToAttributes.put("нп", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,FIRST_PERSON,PLURAL)));
        ancodeToAttributes.put("къ", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,FIRST_PERSON,SINGULAR)));
        ancodeToAttributes.put("кл", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR)));
        ancodeToAttributes.put("км", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL)));
        ancodeToAttributes.put("ль", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,COLLOQUIAL)));
        ancodeToAttributes.put("кь", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL,COLLOQUIAL)));
        ancodeToAttributes.put("Эю", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,ABBREVIATION)));
        ancodeToAttributes.put("фъ", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,SINGULAR,ARCHAISM)));
        ancodeToAttributes.put("фю", new HashSet<>(Arrays.asList(VERB,ACTIVE_VOICE,IMPERATIVE_FORM,SECOND_PERSON,PLURAL,ARCHAISM)));
        ancodeToAttributes.put("ла", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("лб", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("лв", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("лг", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Ла", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("лд", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ле", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("лз", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("ли", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("лй", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("лк", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("лл", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("лм", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ло", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("лп", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("лр", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("лс", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("лт", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("лу", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("лх", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("лц", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("лч", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("лш", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Лй", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("лщ", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("лы", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,ACTIVE_VOICE,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ма", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("мб", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("мв", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("мг", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Лб", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("мд", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ме", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("мз", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("ми", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("мй", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("мк", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("мл", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("мм", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("мо", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("мп", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("мр", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("мс", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("мт", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("му", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("мх", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("мц", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("мч", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("мш", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Лк", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("мщ", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("мы", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,ACTIVE_VOICE,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("па", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("пб", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("пв", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("пг", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Лг", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("пд", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("пе", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("пж", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER)));
        ancodeToAttributes.put("пз", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("пи", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("пй", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("пк", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("пл", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("пм", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("пн", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER)));
        ancodeToAttributes.put("по", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("пп", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("пр", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("пс", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("пт", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("пу", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("пф", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER)));
        ancodeToAttributes.put("пх", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("пц", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("пч", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("пш", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Лм", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("пщ", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("пы", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("пэ", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PRESENT_TENSE,PASSIVE_VOICE,PLURAL)));
        ancodeToAttributes.put("са", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("сб", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("св", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("сг", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Ле", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("сд", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("се", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("сж", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,MASCULINE_GENDER)));
        ancodeToAttributes.put("сз", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("си", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("сй", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("ск", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("сл", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("см", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("сн", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,FEMININE_GENDER)));
        ancodeToAttributes.put("со", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("сп", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("ср", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("сс", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ст", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("су", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("сф", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,SINGULAR,NEUTER_GENDER)));
        ancodeToAttributes.put("сх", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("сц", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("сч", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("сш", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("Ло", new HashSet<>(Arrays.asList(PARTICIPLE,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("сщ", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("сы", new HashSet<>(Arrays.asList(PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("сэ", new HashSet<>(Arrays.asList(SHORT_PARTICIPLE,ANIMATED,INANIMATED,PAST_TENSE,PASSIVE_VOICE,PLURAL)));
        ancodeToAttributes.put("ча", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("чб", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("чв", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("чг", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("чд", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("че", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("чж", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("чз", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("чи", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("чй", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("чк", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("чл", new HashSet<>(Arrays.asList(PRONOUN,FIRST_PERSON,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("чм", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("чн", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("чо", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("чп", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("чр", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("чс", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("чт", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("чу", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("чф", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("чх", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("чц", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("чч", new HashSet<>(Arrays.asList(PRONOUN,SECOND_PERSON,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ша", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("шб", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("шв", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("шг", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("шд", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ше", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("шж", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("шз", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("ши", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("шй", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("шк", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("шл", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("шм", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("шн", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("шо", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("шп", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("шр", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("шс", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("шт", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("шу", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("шф", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("шх", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("шц", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("шч", new HashSet<>(Arrays.asList(PRONOUN,THIRD_PERSON,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ща", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("щб", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("щв", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("щг", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("щд", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ще", new HashSet<>(Arrays.asList(PRONOUN,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("щж", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("щз", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("щи", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("щй", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("щк", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("щл", new HashSet<>(Arrays.asList(PRONOUN,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("щм", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE)));
        ancodeToAttributes.put("щн", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("що", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("щп", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("щр", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("щс", new HashSet<>(Arrays.asList(PRONOUN,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("щт", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("щу", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, GENITIVE_CASE)));
        ancodeToAttributes.put("щф", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, DATIVE_CASE)));
        ancodeToAttributes.put("щх", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("щц", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("щч", new HashSet<>(Arrays.asList(PRONOUN,PLURAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("щщ", new HashSet<>(Arrays.asList(PRONOUN, GENITIVE_CASE)));
        ancodeToAttributes.put("щы", new HashSet<>(Arrays.asList(PRONOUN, DATIVE_CASE)));
        ancodeToAttributes.put("щэ", new HashSet<>(Arrays.asList(PRONOUN, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("щю", new HashSet<>(Arrays.asList(PRONOUN, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("щя", new HashSet<>(Arrays.asList(PRONOUN, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ыа", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыб", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыв", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыг", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("Лф", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("ыд", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ые", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыж", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыз", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыи", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ый", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ык", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыл", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ым", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ын", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыо", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ып", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыр", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыс", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыт", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыу", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыф", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ых", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("Лх", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("ыц", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыч", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыш", new HashSet<>(Arrays.asList(PRONOUN_ADJECTIVE,IMMUTABLE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ыщ", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, GENITIVE_CASE)));
        ancodeToAttributes.put("ыы", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, DATIVE_CASE)));
        ancodeToAttributes.put("ыэ", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("ыю", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("ыь", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN,SINGULAR, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("ыя", new HashSet<>(Arrays.asList(PRONOUN_PREDICATE_NOUN)));
        ancodeToAttributes.put("эа", new HashSet<>(Arrays.asList(NUMERAL, NOMINATIVE_CASE)));
        ancodeToAttributes.put("эб", new HashSet<>(Arrays.asList(NUMERAL, GENITIVE_CASE)));
        ancodeToAttributes.put("эв", new HashSet<>(Arrays.asList(NUMERAL, DATIVE_CASE)));
        ancodeToAttributes.put("эг", new HashSet<>(Arrays.asList(NUMERAL, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("эд", new HashSet<>(Arrays.asList(NUMERAL, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("эе", new HashSet<>(Arrays.asList(NUMERAL, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("Ца", new HashSet<>(Arrays.asList(NUMERAL, NOMINATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("Цб", new HashSet<>(Arrays.asList(NUMERAL, GENITIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("Цв", new HashSet<>(Arrays.asList(NUMERAL, DATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("Цг", new HashSet<>(Arrays.asList(NUMERAL, ACCUSATIVE_CASE,ARCHAISM)));
        ancodeToAttributes.put("Цд", new HashSet<>(Arrays.asList(NUMERAL, INSTRUMENTAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("Це", new HashSet<>(Arrays.asList(NUMERAL, PREPOSITIONAL_CASE,ARCHAISM)));
        ancodeToAttributes.put("эж", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("эз", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("эи", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("эй", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("эк", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("эл", new HashSet<>(Arrays.asList(NUMERAL,MASCULINE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("эм", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("эн", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("эо", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("эп", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("эр", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("эс", new HashSet<>(Arrays.asList(NUMERAL,FEMININE_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("эт", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, NOMINATIVE_CASE)));
        ancodeToAttributes.put("эу", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, GENITIVE_CASE)));
        ancodeToAttributes.put("эф", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, DATIVE_CASE)));
        ancodeToAttributes.put("эх", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, ACCUSATIVE_CASE)));
        ancodeToAttributes.put("эц", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, INSTRUMENTAL_CASE)));
        ancodeToAttributes.put("эч", new HashSet<>(Arrays.asList(NUMERAL,NEUTER_GENDER, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("эш", new HashSet<>(Arrays.asList(NUMERAL,COMPARATIVE_FORM)));
        ancodeToAttributes.put("юа", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юб", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юв", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юг", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("Лт", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("юд", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юе", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,MASCULINE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юж", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юз", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юи", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юй", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юк", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юл", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,FEMININE_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юм", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юн", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юо", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юп", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, ACCUSATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юр", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юс", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,NEUTER_GENDER,SINGULAR, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ют", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, NOMINATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юу", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юф", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, DATIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юх", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,INANIMATED)));
        ancodeToAttributes.put("Лу", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, ACCUSATIVE_CASE,ANIMATED)));
        ancodeToAttributes.put("юц", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, INSTRUMENTAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("юч", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE,PLURAL, PREPOSITIONAL_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("ющ", new HashSet<>(Arrays.asList(NUMERAL_ADJECTIVE, GENITIVE_CASE,ANIMATED,INANIMATED)));
        ancodeToAttributes.put("яа", new HashSet<>(Arrays.asList(ADVERB)));
        ancodeToAttributes.put("ян", new HashSet<>(Arrays.asList(ADVERB,INTERROGATIVE)));
        ancodeToAttributes.put("яо", new HashSet<>(Arrays.asList(ADVERB,DEMONSTRATIVE)));
        ancodeToAttributes.put("яп", new HashSet<>(Arrays.asList(ADVERB,COLLOQUIAL)));
        ancodeToAttributes.put("яб", new HashSet<>(Arrays.asList(PREDICATE_NOUN,PRESENT_TENSE)));
        ancodeToAttributes.put("як", new HashSet<>(Arrays.asList(PREDICATE_NOUN,PAST_TENSE)));
        ancodeToAttributes.put("ял", new HashSet<>(Arrays.asList(PREDICATE_NOUN)));
        ancodeToAttributes.put("яр", new HashSet<>(Arrays.asList(PREDICATE_NOUN,COMPARATIVE_FORM,PRESENT_TENSE)));
        ancodeToAttributes.put("ям", new HashSet<>(Arrays.asList(PREDICATE_NOUN,IMMUTABLE)));
        ancodeToAttributes.put("яв", new HashSet<>(Arrays.asList(PREPOSITION)));
        ancodeToAttributes.put("яг", new HashSet<>(Arrays.asList(POSTPOSITION)));
        ancodeToAttributes.put("яд", new HashSet<>(Arrays.asList(CONJUNCTION)));
        ancodeToAttributes.put("яе", new HashSet<>(Arrays.asList(INTERJECTION)));
        ancodeToAttributes.put("яё", new HashSet<>(Arrays.asList(INTERJECTION,COLLOQUIAL)));
        ancodeToAttributes.put("яж", new HashSet<>(Arrays.asList(PARTICLE)));
        ancodeToAttributes.put("яз", new HashSet<>(Arrays.asList(PARENTHESIS)));
        ancodeToAttributes.put("яй", new HashSet<>(Arrays.asList(PHRASEME)));
        ancodeToAttributes.put("Пп", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,SINGULAR)));
        ancodeToAttributes.put("Пр", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,FIRST_PERSON,PLURAL)));
        ancodeToAttributes.put("Пс", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,SINGULAR)));
        ancodeToAttributes.put("Пт", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,SECOND_PERSON,PLURAL)));
        ancodeToAttributes.put("Пу", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,SINGULAR)));
        ancodeToAttributes.put("Пф", new HashSet<>(Arrays.asList(VERB,PASSIVE_VOICE,FUTURE_TENSE,THIRD_PERSON,PLURAL)));
        ancodeToAttributes.put("Уа", new HashSet<>(Arrays.asList(COMMON,TOPONYM)));
        ancodeToAttributes.put("Уе", new HashSet<>(Arrays.asList(COMMON,QUALITATIVE)));
        ancodeToAttributes.put("Уж", new HashSet<>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM)));
        ancodeToAttributes.put("Уз", new HashSet<>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,ORGANIZATION)));
        ancodeToAttributes.put("Уи", new HashSet<>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,TOPONYM)));
        ancodeToAttributes.put("Ул", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE)));
        ancodeToAttributes.put("Ум", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE)));
        ancodeToAttributes.put("Ун", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE)));
        ancodeToAttributes.put("Уо", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE)));
        ancodeToAttributes.put("Уп", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,IMPERFECTIVE_ASPECT,TRANSITIVE)));
        ancodeToAttributes.put("Ур", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,IMPERFECTIVE_ASPECT,INTRANSITIVE)));
        ancodeToAttributes.put("Ус", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT)));
        ancodeToAttributes.put("Ут", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT)));
        ancodeToAttributes.put("Уф", new HashSet<>(Arrays.asList(COMMON,SLANG)));
        ancodeToAttributes.put("Ух", new HashSet<>(Arrays.asList(COMMON,COMMON_TYPO_OR_ERROR)));
        ancodeToAttributes.put("Уч", new HashSet<>(Arrays.asList(COMMON,SLANG,COMMON_TYPO_OR_ERROR)));
        ancodeToAttributes.put("Уц", new HashSet<>(Arrays.asList(COMMON,ORGANIZATION,SLANG)));
        ancodeToAttributes.put("Уш", new HashSet<>(Arrays.asList(COMMON,TOPONYM,SLANG)));
        ancodeToAttributes.put("Ущ", new HashSet<>(Arrays.asList(COMMON,INANIMATED,TOPONYM)));
        ancodeToAttributes.put("Уь", new HashSet<>(Arrays.asList(COMMON,INANIMATED,ORGANIZATION)));
        ancodeToAttributes.put("Уы", new HashSet<>(Arrays.asList(COMMON,ANIMATED,SURNAME)));
        ancodeToAttributes.put("Уъ", new HashSet<>(Arrays.asList(COMMON,INANIMATED,USUALLY_HAS_NO_PLURAL_FORM,TOPONYM)));
        ancodeToAttributes.put("Уэ", new HashSet<>(Arrays.asList(COMMON,INANIMATED,USUALLY_HAS_NO_PLURAL_FORM,ORGANIZATION)));
        ancodeToAttributes.put("Ую", new HashSet<>(Arrays.asList(COMMON,INANIMATED,SLANG)));
        ancodeToAttributes.put("Уя", new HashSet<>(Arrays.asList(COMMON,INANIMATED,COMMON_TYPO_OR_ERROR)));
        ancodeToAttributes.put("Фа", new HashSet<>(Arrays.asList(COMMON,INANIMATED)));
        ancodeToAttributes.put("Фб", new HashSet<>(Arrays.asList(COMMON,ANIMATED)));
        ancodeToAttributes.put("Фв", new HashSet<>(Arrays.asList(COMMON,ORGANIZATION,SLANG,INANIMATED)));
        ancodeToAttributes.put("Фг", new HashSet<>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,INANIMATED)));
        ancodeToAttributes.put("Фд", new HashSet<>(Arrays.asList(COMMON,USUALLY_HAS_NO_PLURAL_FORM,ANIMATED)));
        ancodeToAttributes.put("Фж", new HashSet<>(Arrays.asList(COMMON,ANIMATED,SLANG)));
        ancodeToAttributes.put("Фз", new HashSet<>(Arrays.asList(COMMON,GIVEN_NAME,POSSESSIVE)));
        ancodeToAttributes.put("Фи", new HashSet<>(Arrays.asList(COMMON,POSSESSIVE)));
        ancodeToAttributes.put("Фк", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,COLLOQUIAL)));
        ancodeToAttributes.put("Фл", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,COLLOQUIAL)));
        ancodeToAttributes.put("Фн", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,COLLOQUIAL)));
        ancodeToAttributes.put("Фо", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,COLLOQUIAL)));
        ancodeToAttributes.put("Фп", new HashSet<>(Arrays.asList(COMMON,INANIMATED,COLLOQUIAL)));
        ancodeToAttributes.put("Фр", new HashSet<>(Arrays.asList(COMMON,ANIMATED,COLLOQUIAL)));
        ancodeToAttributes.put("Фс", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,SLANG)));
        ancodeToAttributes.put("Фт", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,SLANG)));
        ancodeToAttributes.put("Фу", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,SLANG)));
        ancodeToAttributes.put("Фф", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,SLANG)));
        ancodeToAttributes.put("Фх", new HashSet<>(Arrays.asList(COMMON,COLLOQUIAL)));
        ancodeToAttributes.put("Фц", new HashSet<>(Arrays.asList(COMMON,ARCHAISM)));
        ancodeToAttributes.put("Фч", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,TRANSITIVE,ARCHAISM)));
        ancodeToAttributes.put("Фш", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,INTRANSITIVE,ARCHAISM)));
        ancodeToAttributes.put("Фщ", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,TRANSITIVE,ARCHAISM)));
        ancodeToAttributes.put("Фь", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,INTRANSITIVE,ARCHAISM)));
        ancodeToAttributes.put("Фы", new HashSet<>(Arrays.asList(COMMON,INANIMATED,ARCHAISM)));
        ancodeToAttributes.put("Фъ", new HashSet<>(Arrays.asList(COMMON,ANIMATED,ARCHAISM)));
        ancodeToAttributes.put("Фэ", new HashSet<>(Arrays.asList(COMMON,IMPERFECTIVE_ASPECT,ARCHAISM)));
        ancodeToAttributes.put("Фю", new HashSet<>(Arrays.asList(COMMON,PERFECTIVE_ASPECT,ARCHAISM)));
        ancodeToAttributes.put("Фя", new HashSet<>(Arrays.asList(COMMON,QUALITATIVE,ARCHAISM)));
        ancodeToAttributes.put("Фё", new HashSet<>(Arrays.asList(COMMON,INANIMATED,ANIMATED)));
        ancodeToAttributes.put("Ха", new HashSet<>(Arrays.asList(COMMON,ANIMATED,COMMON_TYPO_OR_ERROR)));
        ancodeToAttributes.put("Хб", new HashSet<>(Arrays.asList(COMMON,TOPONYM,COMMON_TYPO_OR_ERROR)));
        ancodeToAttributes.put("яю", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,FEMININE_GENDER,NEUTER_GENDER,SINGULAR, NOMINATIVE_CASE, GENITIVE_CASE, DATIVE_CASE, ACCUSATIVE_CASE, INSTRUMENTAL_CASE, PREPOSITIONAL_CASE)));
        ancodeToAttributes.put("яя", new HashSet<>(Arrays.asList(NOUN,MASCULINE_GENDER,FEMININE_GENDER,NEUTER_GENDER,SINGULAR,PLURAL, NOMINATIVE_CASE, GENITIVE_CASE, DATIVE_CASE, ACCUSATIVE_CASE, INSTRUMENTAL_CASE, PREPOSITIONAL_CASE)));
    }



    /**
     * Return attributes associeted with specified ancode. Ancode is a short for "Anoshkin's code", 2-letters string,
     * specified for any wordform and for lexeme. You can lookup more information on aot.ru site, see links section
     * in README file
     *
     * @param ancode Anoshkin's code
     * @return set of attributes
     */
    public static Set<Attribute> getAttributes(String ancode) {
        if(ancodeToAttributes.containsKey(ancode)) {
            return ancodeToAttributes.get(ancode);
        }
        else {
            throw new RuntimeException("Unknown ancode: " + ancode);
        }
    }


}
