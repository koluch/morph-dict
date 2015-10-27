package ru.koluch.textWork.dictionaryParser;


import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class DictionaryParser {


    public DictionaryParser() {
    }

    public Dictionary parse(InputStream inputStream) throws ParseException {


        ArrayList<ParadigmRule> allRules = new ArrayList<>();
        HashMap<String, LexemeRec[]> baseToLexemes = new HashMap<>();

        /**
         * Dictionary loading
         */
        try(BufferedReader fin = new BufferedReader(new InputStreamReader(inputStream))) {


            // Load endings models
            int num = Integer.valueOf(fin.readLine());
            Pattern paradigmListEx = Pattern.compile("\\%([^\\%]+)");
            Pattern paradigmEx = Pattern.compile("([^\\*]*)\\*([^\\*]*)(:?\\*([^\\*]*))?");
            for (int i = 0; i < num; ++i) {
                String nextString = fin.readLine();
                Matcher matcher = paradigmListEx.matcher(nextString);
                ParadigmRule paradigmRule = new ParadigmRule();
                int k = 0;
                while(matcher.find())
                {
                    String paradigmString = matcher.group(1);
                    Matcher paradigmMatcher = paradigmEx.matcher(paradigmString);
                    if(paradigmMatcher.find())
                    {
                        String ending = paradigmMatcher.group(1);
                        String ancode = paradigmMatcher.group(2);  // Ancode is Anoshkin's code
                        String prefix = paradigmMatcher.group(4);
                        if(prefix!=null)
                        {
//                            System.out.println("!");
                        }
                        paradigmRule.addAncode(ending, ancode);
                        if(k++==0)
                        {
                            paradigmRule.setFirstAncode(ancode);
                            paradigmRule.setFirstEnding(ending);
                        }
                    }
                }
                allRules.add(paradigmRule);
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
                // String accentParadigmNum = matcher.group(2);
                // String userSessionNum = matcher.group(3);
                String anc = lemParts[4];
                // String prefix = matcher.group(5);

                LexemeRec[] modelList;
                if (baseToLexemes.containsKey(basis)) {
                    modelList = baseToLexemes.get(basis);
                } else {
                    modelList = new LexemeRec[10];
                    for (int n = 0; n < 10; ++n) {
                        modelList[n] = new LexemeRec();
                        modelList[n].mod = -1;
                    }
                    baseToLexemes.put(basis, modelList);
                }


                for (int n = 0; n < 10; ++n) {
                    if (modelList[n].mod == -1) {
                        modelList[n].mod = paradigmNum;
                        if(!modelList.equals(" -"))
                            modelList[n].ancode = anc;
                        break;
                    }
                }
            }

            return new Dictionary(allRules, baseToLexemes);

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
