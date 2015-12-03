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

import org.junit.Before;
import org.junit.Test;
import ru.koluch.morphDict.dictionary.Attribute;
import ru.koluch.morphDict.dictionary.data.Dictionary;
import ru.koluch.morphDict.lookup.data.Lexeme;
import ru.koluch.morphDict.lookup.data.WordForm;
import ru.koluch.morphDict.prefixTree.PrefixTree;
import ru.koluch.morphDict.dictionary.DictionaryHelper;
import ru.koluch.morphDict.lookup.PrefixTreeLookupService;
import ru.koluch.morphDict.lookup.data.LookupResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static ru.koluch.morphDict.dictionary.Attribute.*;




public class TestLookup {

    private PrefixTreeLookupService lookup;

    @Before
    public void init() throws IOException, DictionaryHelper.ParseException {
        Dictionary dictionary = DictionaryHelper.parse(new InputStreamReader(Dictionary.class.getResourceAsStream("/morphs.mrd"), "UTF-8"));
        PrefixTree<DictionaryHelper.TreeData> tree = DictionaryHelper.buildPrefixTree(dictionary);
        lookup = new PrefixTreeLookupService(dictionary, tree);
    }


    @Test
    public void testNoun() throws IOException {
        ArrayList<LookupResult> resultList = lookup.lookup("собакой");

        assertThat(resultList.size(), is(1));

        LookupResult lookupResult = resultList.get(0);
        Set<Attribute> attributes = DictionaryHelper.getAttributes(lookupResult.wordForm.ancode);

        assertThat(attributes, hasItem(NOUN));
        assertThat(attributes, hasItem(FEMININE_GENDER));
        assertThat(attributes, hasItem(INSTRUMENTAL_CASE));
        assertThat(attributes, hasItem(SINGULAR));

        Lexeme lexeme = resultList.get(0).lexeme;
        WordForm wordForm = lexeme.homonyms.get(0);
        assertThat(wordForm.makeWord(), is("собака"));

    }

    @Test
    public void testVerb() throws IOException {
        ArrayList<LookupResult> resultList = lookup.lookup("побегут");

        assertThat(resultList.size(), is(1));

        LookupResult lookupResult = resultList.get(0);
        Set<Attribute> attributes = DictionaryHelper.getAttributes(lookupResult.wordForm.ancode);

        assertThat(attributes, hasItem(VERB));
        assertThat(attributes, hasItem(FUTURE_TENSE));
        assertThat(attributes, hasItem(PLURAL));
        assertThat(attributes, hasItem(ACTIVE_VOICE));
        assertThat(attributes, hasItem(PLURAL));

        Lexeme lexeme = resultList.get(0).lexeme;
        assertFalse(lexeme.homonyms.isEmpty());

        WordForm wordForm = lexeme.homonyms.get(0);
        assertThat(wordForm.makeWord(), is("побежать"));

    }

}
