package ru.koluch.textWork.morphDict;

import java.io.*;

import ru.koluch.textWork.morphDict.dictionary.DictionaryHelper;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, DictionaryHelper.ParseException {

//        DictionaryParser dictionaryParser = new DictionaryParser();
//        MorphParams params = new MorphParams(new InputStreamReader(Main.class.getResourceAsStream("/rgramtab.tab"), "UTF-8"));
//
//        Dictionary dictionary = dictionaryParser.parse(new InputStreamReader(Main.class.getResourceAsStream("/morphs.mrd"), "UTF-8"));
//        Lookup lookup = new Lookup(dictionary);
//
//
//        TreeBuilder treeBuilder = new TreeBuilder();
//        PrefixTree<TreeBuilder.TreeData> tree = treeBuilder.buildPrefixTree(dictionary);
//
//        System.out.println("Nodes: " + Metrics.countNodes(tree));
//        System.out.println("Leafs: " + Metrics.countLeafs(tree));
//        System.out.println("Max deep: " + Metrics.countMaxDeep(tree));
//
//
//        JsonFilesBuilder jsonFilesBuilder = new JsonFilesBuilder();
//        Gson gson = new Gson();
//        jsonFilesBuilder.buildPrefixTree(new File("/Users/koluch/tmp/json/"), dictionary, tree, (data) -> {
//            JsonArray json = new JsonArray();
//            for (TreeBuilder.TreeData treeData : data) {
//                JsonArray sub = new JsonArray();
//                sub.add(treeData.ancode);
//                sub.add(treeData.lexemeRecNum);
//                json.add(sub);
//            }
//            return json;
//        });
//
//        testOnData(lookup, params);
//
//        System.out.println("Finished!");


    }

}
