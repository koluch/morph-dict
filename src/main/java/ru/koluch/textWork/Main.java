package ru.koluch.textWork;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ru.koluch.textWork.dictionary.Dictionary;
import ru.koluch.textWork.dictionary.parsing.DictionaryParser;
import ru.koluch.textWork.dictionary.Lexeme;
import ru.koluch.textWork.dictionary.MorphParams;
import ru.koluch.textWork.dictionary.lookup.Lookup;
import ru.koluch.textWork.dictionary.prefixTree.PrefixTree;
import ru.koluch.textWork.dictionary.prefixTree.TreeBuilder;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, DictionaryParser.ParseException {

        DictionaryParser dictionaryParser = new DictionaryParser();
        MorphParams params = new MorphParams(new InputStreamReader(Main.class.getResourceAsStream("/rgramtab.tab"), "UTF-8"));

        Dictionary dictionary = dictionaryParser.parse(new InputStreamReader(Main.class.getResourceAsStream("/morphs.mrd"), "UTF-8"));
        Lookup lookup = new Lookup(dictionary);


        TreeBuilder treeBuilder = new TreeBuilder();
        PrefixTree<TreeBuilder.TreeData> tree = treeBuilder.build(dictionary);

        Optional<List<TreeBuilder.TreeData>> data = tree.get("красивый");



        testOnData(lookup, params);

        System.out.println("Finished!");


    }

    private static void testOnData(Lookup lookup, MorphParams params) throws IOException {
        InputStream testText = Main.class.getResourceAsStream("/test.txt");

        {
            ArrayList<Lexeme> lex_list = lookup.find("КРАСИВЕЕ");
            Lexeme first = lex_list.get(0);

            System.out.println(first.getBase().getBase() + first.getBase().getEnding());

            System.out.println("Gender: " + params.getGender(first.getBase().getAncode()));
            System.out.println("Plurality: " + params.getNumber(first.getBase().getAncode()));
            System.out.println("Type:" + params.getType(first.getBase().getAncode()));
        }

//
//        String testString = IOUtils.toString(testText)
//                .replaceAll("\\r?\\n", " ")
//                .replaceAll("[\\u00AB\\u00BB,.\\(\\)\\?\\\\!—\\;\\:(\\d*)\\№\\-]", "")
//                .replaceAll("\\s\\s+", " ");
//
//
//        String[] words = testString.split(" ");
//        System.out.println("Total word count: " + words.length);
//        for (String word : words) {
//		/* Get main wordform by arbitrary */
//            ArrayList<Lexeme> lex_list = dictionaryParser.find(word.toUpperCase());
//            if(lex_list.size()<1)
//            {
////                System.out.println("WARN: " + word);
//            }
//            else
//            {
//                Lexeme first = lex_list.get(0);
//                System.out.println(first.getBase().getBase() + first.getBase().getEnding());
//            }
///*
////            ArrayList<Lexeme> lex_list = morphParser.find("собаками");
//
//            System.out.println();
//
//            System.out.println("Gender: " + params.getGender(first.getBase().getAncode()));
//            System.out.println("Plurality: " + params.getNumber(first.getBase().getAncode()));
//            System.out.println("Type:" + params.getType(first.getBase().getAncode()));
//*/
//
//        }
    }


}
