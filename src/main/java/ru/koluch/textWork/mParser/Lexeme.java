/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ru.koluch.textWork.mParser;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * Lexeme. Lexeme is word with defined morphological properties and which bring to normal form
 *
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 */

public class Lexeme {

    
    /**
     * Homonyms of current wordform
     */
    ArrayList<WordForm> omonims;
    
    /**
     * Base wordform
     */
    WordForm base;
    
    /**
     * Common ancode for lexeme
     */
    String commonAn;
    
    /**
     * Type of lexeme
     */
    LexemeType type;

    /**
     * Number in dictionary
     */
    int num;
    
    /**
     *
     * Link to endings model to build arbitrary wordform.
     *
     */
    private int flex_mod;
    
    public Lexeme() {
        this.omonims = new ArrayList<WordForm>();
        this.type = LexemeType.UNDEF;
        this.flex_mod = -1;
        this.commonAn = "??";
    }

    public Lexeme(ArrayList<WordForm> omonims, WordForm base, String commonAn, LexemeType type, int flex_mod) {
        this.omonims = omonims;
        this.base = base;
        this.commonAn = commonAn;
        this.type = type;
        this.flex_mod = flex_mod;
    }

    public void exceptCurrent(WordForm form) {
        this.omonims.clear();
        this.omonims.add(form);
    }


    public void setOmonims(ArrayList<WordForm> omonims) {
        this.omonims = omonims;
    }

    public ArrayList<WordForm> getOmonims() {
        return omonims;
    }

    public void AddOmonim(WordForm omonim)
    {
        this.omonims.add(omonim);
    }

    public void setBase(WordForm base) {
        this.base = base;
    }

    public WordForm getBase() {
        return base;
    }

    public void setCommonAn(String commonAn) {
        this.commonAn = commonAn;
    }

    public String getCommonAn() {
        return commonAn;
    }


    public static char start_ch = '(';
    public static char finish_ch = ')';
    
    
    /**
     * Get normalized form of lexeme
     * @return normalized form
     */
    public StringBuffer getNorm()
    {
        StringBuffer result = new StringBuffer();
        result.append(start_ch);
        result.append(this.base.getBase());
        result.append('^');
        result.append(this.getCommonAn());
        result.append('^');
        result.append(this.base.getFlexion());
        result.append('^');
        result.append(this.base.getAncode());
        Iterator it = this.omonims.iterator();
        while(it.hasNext())
        {
            WordForm curw = (WordForm) it.next();
            result.append('|');
            result.append(curw.getAncode());
        }
        result.append(finish_ch);
        return result;
    }
    
    public void loadNorm(String norm) throws Exception
    {
        StringBuffer buf;
        char ggg = norm.charAt(0);
        if(norm.charAt(0)!=start_ch || norm.charAt(norm.length()-1)!=finish_ch) 
          throw new Exception("Normal form of lexeme has a wrong format!");
        
        int a,b;
        buf = new StringBuffer(norm.substring(1, norm.length()-1));
        this.base = new WordForm();
        
        a = 0; b = buf.indexOf("^");
        this.base.setBase(buf.substring(a, b));
        
        a = b+1; b = buf.indexOf("^", a);
        this.setCommonAn(buf.substring(a, b));
        
        a = b+1; b = buf.indexOf("^", a);
        this.base.setFlexion(buf.substring(a, b));
        
        this.base.setAncode(buf.substring(b+1,b+3));
        
        b=b+4;
        WordForm fw;
        while(b<buf.length())
        {
            fw = new WordForm();
            fw.setAncode(buf.substring(b, b+2));
            this.omonims.add(fw);
            b=b+3;
        }
        
    }
    
}
