package ru.koluch.textWork;

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import ru.koluch.textWork.dictionaryParser.DictionaryParser;
import ru.koluch.textWork.dictionaryParser.Lexeme;
import ru.koluch.textWork.dictionaryParser.MorphParams;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

/*
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        DictionaryParser dictionaryParser = initParser();
        MorphParams params = initParams();

        testOnData(dictionaryParser,params);

        System.out.println("Finished!");

/*        try {
            Thread.sleep(130000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    private static DictionaryParser initParser() {
        DictionaryParser dictionaryParser = new DictionaryParser(Main.class.getResourceAsStream("/morphs.mrd"));
        MorphParams params = new MorphParams(Main.class.getResourceAsStream("/rgramtab.tab"));
        return dictionaryParser;
    }

    private static MorphParams initParams() {
        MorphParams params = new MorphParams(Main.class.getResourceAsStream("/rgramtab.tab"));
        return params;
    }

    private static void testOnData(DictionaryParser dictionaryParser, MorphParams params) throws IOException {
        InputStream testText = Main.class.getResourceAsStream("/test.txt");
        BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(testText));

        {
            ArrayList<Lexeme> lex_list = dictionaryParser.find("КРАСИВЕЕ");
            Lexeme first = lex_list.get(0);

            System.out.println(first.getBase().getBase() + first.getBase().getFlexion());

            System.out.println("Gender: " + params.getGender(first.getBase().getAncode()));
            System.out.println("Plurality: " + params.getNumber(first.getBase().getAncode()));
            System.out.println("Type:" + params.getType(first.getBase().getAncode()));
        }


        String testString = IOUtils.toString(testText)
                .replaceAll("\\r?\\n", " ")
                .replaceAll("[\\u00AB\\u00BB,.\\(\\)\\?\\\\!—\\;\\:(\\d*)\\№\\-]", "")
                .replaceAll("\\s\\s+", " ");


        String[] words = testString.split(" ");
        System.out.println("Total word count: " + words.length);
        for (String word : words) {
		/* Get main wordform by arbitrary */
            ArrayList<Lexeme> lex_list = dictionaryParser.find(word.toUpperCase());
            if(lex_list.size()<1)
            {
//                System.out.println("WARN: " + word);
            }
            else
            {
                Lexeme first = lex_list.get(0);
                System.out.println(first.getBase().getBase() + first.getBase().getFlexion());
            }
/*
//            ArrayList<Lexeme> lex_list = morphParser.find("собаками");

            System.out.println();

            System.out.println("Gender: " + params.getGender(first.getBase().getAncode()));
            System.out.println("Plurality: " + params.getNumber(first.getBase().getAncode()));
            System.out.println("Type:" + params.getType(first.getBase().getAncode()));
*/

        }
    }


}
