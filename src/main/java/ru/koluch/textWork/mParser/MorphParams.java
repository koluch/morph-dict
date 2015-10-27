/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.mParser;

import java.io.*;
import java.util.HashMap;

/**
 * Class for work with lexem parameters. Works with Anoshkin's codes
 *
 * "Nikolay Mavrenkov <koluch@koluch.ru>"
 */
public class MorphParams {

    private HashMap<String,Ancode> ancode_list; // Существительные

    public MorphParams(InputStream inputStream) {
        /**
         *
         * Load list of ancodes with parameters
         *
         */
        try
        {
            BufferedReader fin = new BufferedReader(new InputStreamReader(inputStream));
            ancode_list = new HashMap<>();
            Ancode newan;
            String str, ancode;
            String type_str,params,param;
            int a,b;
            while(fin.ready())
            {
                str = fin.readLine();
                if(str.equals("")) continue;
                ancode = str.substring(0, 2);
                if(ancode.equals("//")) continue;

                newan = new Ancode();

                a = str.indexOf(" ",6);
                if(a>=0)
                {
                    type_str = str.substring(5, str.indexOf(" ",6));
                    params = str.substring(str.indexOf(" ",6)+1);
                }
                else
                {
                    type_str = str.substring(5);
                    params = "";
                }

                if(type_str.equals("С"))
                {
                    newan.getValues()[0] = 'с';
                }
                else if(type_str.equals("П") || type_str.equals("КР_ПРИЛ"))
                {
                    newan.getValues()[0] = 'п';
                }
                else if(type_str.equals("Г"))
                {
                    newan.getValues()[0] = 'г';
                }
                else if(type_str.equals("ПРИЧАСТИЕ") || type_str.equals("КР_ПРИЧАСТИЕ"))
                {
                    newan.getValues()[0] = 'р';
                }
                else if(type_str.equals("ДЕЕПРИЧАСТИЕ"))
                {
                    newan.getValues()[0] = 'д';
                }
                else if(type_str.equals("ИНФИНИТИВ"))
                {
                    newan.getValues()[0] = 'и';
                }
                else if(type_str.equals("МС") || type_str.equals("МС-ПРЕДК"))
                {
                    newan.getValues()[0] = 'м';
                }
                else if(type_str.equals("МС-П"))
                {
                    newan.getValues()[0] = 'л';
                }
                else if(type_str.equals("Н"))
                {
                    newan.getValues()[0] = 'н';
                }
                else if(type_str.equals("ЧИСЛ"))
                {
                    newan.getValues()[0] = 'ч';
                }
                else if(type_str.equals("ПРЕДЛ"))
                {
                    newan.getValues()[0] = 'е';
                }
                else
                {
                    newan.getValues()[0] = '?';
                }


                b = -1;
                do
                {
                    a = b+1;
                    b = params.indexOf(',', a);
                    if(b==-1) param = params.substring(a); else param = params.substring(a, b);
                    param = param.trim();

                    if(param.equals("мр"))     newan.getValues()[1] = 'м';
                    else if(param.equals("жр"))     newan.getValues()[1] = 'ж';
                    else if(param.equals("ср"))     newan.getValues()[1] = 'с';
                    else if(param.equals("мр-жр"))  newan.getValues()[1] = 'о';

                    else if(param.equals("ед"))     newan.getValues()[2] = 'е';
                    else if(param.equals("мн"))     newan.getValues()[2] = 'м';

                    else if(param.equals("им"))     newan.getValues()[3] = 'и';
                    else if(param.equals("рд"))     newan.getValues()[3] = 'р';
                    else if(param.equals("дт"))     newan.getValues()[3] = 'д';
                    else if(param.equals("вн"))     newan.getValues()[3] = 'в';
                    else if(param.equals("тв"))     newan.getValues()[3] = 'т';
                    else if(param.equals("пр"))     newan.getValues()[3] = 'п';
                    else if(param.equals("зв"))     newan.getValues()[3] = 'з';

                    else if(param.equals("2") && newan.getValues()[3]=='р') newan.getValues()[3] = 'Р';
                    else if(param.equals("2") && newan.getValues()[3]=='п') newan.getValues()[3] = 'П';

                    else if(param.equals("од"))     newan.getValues()[4] = 'о';
                    else if(param.equals("но"))     newan.getValues()[4] = 'н';

                    else if(param.equals("св"))     newan.getValues()[5] = 'с'; // Perfect (Совершенный)
                    else if(param.equals("нс"))     newan.getValues()[5] = 'н';

                    else if(param.equals("пе"))     newan.getValues()[6] = 'п'; //  Transitional (Переходный)
                    else if(param.equals("нп"))     newan.getValues()[6] = 'н';

                    else if(param.equals("дст"))    newan.getValues()[7] = 'д'; // Active voice (Действительный залог)
                    else if(param.equals("стр"))    newan.getValues()[7] = 'с'; // Passive voice (Страдательный залог)

                    else if(param.equals("нст"))    newan.getValues()[8] = 'н';
                    else if(param.equals("прш"))    newan.getValues()[8] = 'п';
                    else if(param.equals("буд"))    newan.getValues()[8] = 'б';

                    else if(param.equals("пвл"))    newan.getValues()[9] = 'п'; // Imperative form of the verb (Повелительная форма глагола)

                    else if(param.equals("1л"))     newan.getValues()[10] = '1';
                    else if(param.equals("2л"))     newan.getValues()[10] = '2';
                    else if(param.equals("3л"))     newan.getValues()[10] = '3';

                    else if(param.equals("0"))      newan.getValues()[11] = 'н';  // Invariant (Неизменяемое)

                    else if(param.equals("кр"))     newan.getValues()[12] = 'к';

                    else if(param.equals("сравн"))  newan.getValues()[13] = 'с';  // Comparative form, for adjectives (сравнительная форма, для прилагательных)

                    else if(param.equals("имя"))    newan.getValues()[14] = 'и';
                    else if(param.equals("фам"))    newan.getValues()[14] = 'ф';
                    else if(param.equals("отч"))    newan.getValues()[14] = 'о';

                    else if(param.equals("лок"))    newan.getValues()[15] = 'л';  // Locative (Локативность)
                    else if(param.equals("орг"))    newan.getValues()[15] = 'о';  // Organization (Организация)

                    else if(param.equals("кач"))    newan.getValues()[16] = 'к';  // Qualitative (Качественное)

                    else if(param.equals("вопр"))   newan.getValues()[17] = 'в';  // Interrogation, adverb (Вопросительность, наречие)
                    else if(param.equals("относ"))  newan.getValues()[17] = 'о';  // Relativity (Относительноеть)

                    else if(param.equals("дфст"))   newan.getValues()[18] = 'д';  // Couldn't have plural form (Не имеет множественного числа)

                    else if(param.equals("опч"))    newan.getValues()[18] = 'о';  // Mispring (Опечатка или ошибка)

                    else if(param.equals("жарг"))   newan.getValues()[19] = 'ж';
                    else if(param.equals("арх"))    newan.getValues()[19] = 'а';
                    else if(param.equals("проф"))   newan.getValues()[19] = 'п';
                    else if(param.equals("разг"))   newan.getValues()[19] = 'ж'; // Colloquial (Разговорное) (= ж?)

                    else if(param.equals("аббр"))   newan.getValues()[20] = 'п';

                    else if(param.equals("безл"))   newan.getValues()[21] = 'б';  // Impersonal verb  (Безличный глагол)


                } while(b!=-1);

                ancode_list.put(ancode, newan);

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    public LexemeType getType(String ancode)
    {
        Ancode cur = ancode_list.get(ancode);
        if(cur.getValues()[0]=='с')
        {
            return LexemeType.NOUN;
        }
        else if(cur.getValues()[0]=='п')
        {
            return LexemeType.ADJECTIVE;
        }
        else if(cur.getValues()[0]=='г')
        {
            return LexemeType.NOUN;
        }
        else if(cur.getValues()[0]=='р')
        {
            return LexemeType.PARTICIPLE;
        }
        else if(cur.getValues()[0]=='и')
        {
            return LexemeType.INFINITIVE;
        }
        else if(cur.getValues()[0]=='д')
        {
            return LexemeType.GERUND;
        }
        else if(cur.getValues()[0]=='м')
        {
            return LexemeType.PRONOUN;
        }
        else if(cur.getValues()[0]=='л')
        {
            return LexemeType.PRONOUN_ADJECTIVE;
        }
        else if(cur.getValues()[0]=='н')
        {
            return LexemeType.ADVERB;
        }
        else if(cur.getValues()[0]=='ч')
        {
            return LexemeType.NUMERAL;
        }
        else if(cur.getValues()[0]=='е')
        {
            return LexemeType.PREPOSITION;
        }
        
        return LexemeType.UNDEF;
    }
    
    public char getGender(String ancode)
    {
        Ancode cur = ancode_list.get(ancode);
        return cur.getValues()[1];
    }

    public static enum Plurality {SINGULAR, PLURAL, UNDEF}
    public Plurality getNumber(String ancode)
    {
        Ancode cur = ancode_list.get(ancode);
        if(cur.getValues()[2] == 'е')
        {
            return Plurality.SINGULAR;
        }
        else if (cur.getValues()[2] == 'м')
        {
            return Plurality.PLURAL;
        }
        else
        {
            return Plurality.UNDEF;
        }
    }
    
    public char getCase(String ancode)
    {
        Ancode cur = ancode_list.get(ancode);
        return cur.getValues()[3];
    }
    
    
            
}
