package ru.koluch.textWork.dictionary.parsing;


import ru.koluch.textWork.dictionary.LexemeRec;
import ru.koluch.textWork.dictionary.ParadigmRule;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class DictionaryParser {


    public DictionaryParser() {
    }

    public ru.koluch.textWork.dictionary.Dictionary parse(Reader reader) throws ParseException {


        List<List<ParadigmRule>> allRules = new ArrayList<>();
        HashMap<String, List<LexemeRec>> baseToLexemes = new HashMap<>();

        /**
         * Dictionary loading
         */
        try(BufferedReader fin = new BufferedReader(reader)) {


            // Load flexias models
            int num = Integer.valueOf(fin.readLine());
            Pattern paradigmListEx = Pattern.compile("\\%([^\\%]+)");
            Pattern paradigmEx = Pattern.compile("([^\\*]*)\\*([^\\*]*)(:?\\*([^\\*]*))?");
            for (int i = 0; i < num; ++i) {
                String nextString = fin.readLine();
                Matcher matcher = paradigmListEx.matcher(nextString);
                int k = 0;

                List<ParadigmRule> paradigmRules = new ArrayList<>();
                while(matcher.find())
                {
                    String paradigmString = matcher.group(1);
                    Matcher paradigmMatcher = paradigmEx.matcher(paradigmString);
                    if(paradigmMatcher.find())
                    {
                        String ending = paradigmMatcher.group(1);
                        String ancode = paradigmMatcher.group(2);  // Ancode is Anoshkin's code
                        String prefix = paradigmMatcher.group(4);

                        paradigmRules.add(new ParadigmRule(ending, ancode, prefix));
                    }
                }

                allRules.add(paradigmRules);
            }

            // Skip: accents, journal, prefixes... //todo:implement
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                fin.readLine();
            }
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                fin.readLine();
            }
            num = Integer.decode(fin.readLine());
            for (int i = 0; i < num; ++i) {
                fin.readLine();
            }

            // Read lexemes
            num = Integer.decode(fin.readLine());

            Pattern pseudoBasisEx = Pattern.compile("^(.+)\\s(.+)\\s(.+)\\s(.+)\\s(.+)\\s(.+)$");
            for (int i = 0; i < num; ++i) {
                String lem = fin.readLine();

                String[] lemParts = lem.split(" ");

                String basis = lemParts[0];
                Integer paradigmNum = Integer.valueOf(lemParts[1]);
                Integer accentParadigmNum = Integer.valueOf(lemParts[2]);
                Integer userSessionNum = Integer.valueOf(lemParts[3]);
                String anc = lemParts[4];
                String prefix = lemParts[5];

                List<LexemeRec> modelList;
                if (baseToLexemes.containsKey(basis)) {
                    modelList = baseToLexemes.get(basis);
                } else {
                    modelList = new ArrayList<>();
                    baseToLexemes.put(basis, modelList);
                }

                modelList.add(new LexemeRec(
                    paradigmNum,
                    anc,
                    accentParadigmNum,
                    userSessionNum,
                    prefix
                ));

            }

            return new ru.koluch.textWork.dictionary.Dictionary(allRules, baseToLexemes);

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




    
    
    
   
}
