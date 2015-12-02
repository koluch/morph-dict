import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.koluch.textWork.dictionary.Attribute;
import ru.koluch.textWork.dictionary.Dictionary;
import ru.koluch.textWork.dictionary.MorphParams;
import ru.koluch.textWork.dictionary.parsing.DictionaryParser;
import ru.koluch.textWork.dictionary.prefixTree.Metrics;
import ru.koluch.textWork.dictionary.prefixTree.PrefixTree;
import ru.koluch.textWork.dictionary.prefixTree.TreeBuilder;
import ru.koluch.textWork.lookup.PrefixTreeLookup;
import ru.koluch.textWork.lookup.LookupResult;
import ru.koluch.textWork.lookup.WordForm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * --------------------------------------------------------------------
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2015 Nikolai Mavrenkov
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * --------------------------------------------------------------------
 * Author:  Nikolai Mavrenkov <koluch@koluch.ru>
 * Created: 02.12.2015 14:34
 */


public class TestLookup {

    private PrefixTreeLookup lookup;

    @Before
    public void init() throws IOException, DictionaryParser.ParseException {
        DictionaryParser dictionaryParser = new DictionaryParser();
        MorphParams params = new MorphParams(new InputStreamReader(TestLookup.class.getResourceAsStream("/rgramtab.tab"), "UTF-8"));

        Dictionary dictionary = dictionaryParser.parse(new InputStreamReader(TestLookup.class.getResourceAsStream("/morphs.mrd"), "UTF-8"));


        TreeBuilder treeBuilder = new TreeBuilder();
        PrefixTree<TreeBuilder.TreeData> tree = treeBuilder.build(dictionary);

        lookup = new PrefixTreeLookup(dictionary, tree);


//
//        JsonFilesBuilder jsonFilesBuilder = new JsonFilesBuilder();
//        Gson gson = new Gson();
//        jsonFilesBuilder.build(new File("/Users/koluch/tmp/json/"), dictionary, tree, (data) -> {
//            JsonArray json = new JsonArray();
//            for (TreeBuilder.TreeData treeData : data) {
//                JsonArray sub = new JsonArray();
//                sub.add(treeData.ancode);
//                sub.add(treeData.lexemeRecNum);
//                json.add(sub);
//            }
//            return json;
//        });

//        testOnData(lookup, params);

    }


    @Test
    public void test1() throws IOException {

        ArrayList<LookupResult> resultList = lookup.find("собакой");

        assertThat(resultList.size(), is(1));

        LookupResult lookupResult = resultList.get(0);

        Set<Attribute> attributes = Attribute.getAttributes(lookupResult.wordForm.getAncode());

        assertTrue(attributes.contains(Attribute.NOUN));
        assertTrue(attributes.contains(Attribute.FEMININE_GENDER));
        assertTrue(attributes.contains(Attribute.INSTRUMENTAL_CASE));
        assertTrue(attributes.contains(Attribute.SINGULAR));

    }
}
