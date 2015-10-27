package ru.koluch.textWork.dictionaryParser;


import java.io.*;
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
    
    private ArrayList<ParadigmRule> allRules = new ArrayList<>();
    private HashMap<String, LexemeRec[]> baseToLexemes = new HashMap<>();

    public DictionaryParser(InputStream inputStream) {
        /**
         * Dictionary loading
         */
        BufferedReader fin = null;
        try {

            int a;
            fin = new BufferedReader(new InputStreamReader(inputStream));

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


        } catch (IOException ex) {
            Logger.getLogger(DictionaryParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(DictionaryParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    /**
     * Search wordform in dictionary
     *
     * @param tofind arbitrary wordform
     * @return list of lexemes-homonym, containing specified wordform
     */
    public ArrayList<Lexeme> find(String tofind)
    {
        HashMap flex_mod;
        HashSet flex_params;
        WordForm form;
        String base, flex, ancode, buf1, buf2;
        Iterator it;
        
        tofind = tofind.toUpperCase(); // Make it uppercase
        
        ArrayList<Lexeme> result = new ArrayList<Lexeme>();
        
        LexemeRec[] ending_list;
        // Iterate all sub-string of source word from beggining to the i-th symbol
        for(int i=1, len=tofind.length(); i<=len; ++i)
        {
            base = tofind.substring(0, i);
            flex = tofind.substring(i);

            // If current state is not registered yet
            if(baseToLexemes.containsKey(base))
            {
                
                // Get list of models for current ending
                ending_list = baseToLexemes.get(base);
                
                // Go through all models
                for(int n=0; n<10; ++n)
                {
                    // If model with this number it not registered yet - break
                    if(ending_list[n].mod==-1)
                    {
                        break;
                    }
                    ParadigmRule paradigmRule = allRules.get(ending_list[n].mod);

                    // If model contains current ending
                    if(paradigmRule.getEndingsToAncodes().containsKey(flex))
                    {
                        // Create lexeme
                        Lexeme lex = new Lexeme();
                        
                        // Get basic ancodes and endings, create main wordform
                        buf1 = paradigmRule.getFirstAncode();
                        buf2 = paradigmRule.getFirstEnding();
                        form = new WordForm();
                        form.setAncode(buf1);
                        form.setBase(base);
                        form.setFlexion(buf2);
                        lex.setBase(form);
                        
                        // Write common ancode
                        if(ending_list[n].ancode!=null)
                        {
                            lex.setCommonAn(ending_list[n].ancode);
                        }
                        else
                        {
                            lex.setCommonAn(" -");
                        }
                        
                        // Get list of ancods for current ending
                        List<String> ancodes = paradigmRule.getEndingsToAncodes().get(flex);

                        for (String nextAncode : ancodes) {
                            // For each ancode create wordform and register in lexeme as homonym
                            form = new WordForm();

                            form.setAncode(nextAncode);
                            form.setBase(base);
                            form.setFlexion(flex);

                            lex.AddOmonim(form);
                        }

                        result.add(lex);
                    }
                }
            }
        }    
        
        return result;
    }     
    
    
    
    
   
}
