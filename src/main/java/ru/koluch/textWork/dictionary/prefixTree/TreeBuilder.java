/**
 * --------------------------------------------------------------------
 * Copyright 2015 Nikolay Mavrenkov
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * --------------------------------------------------------------------
 * <p/>
 * Author:  Nikolay Mavrenkov <koluch@koluch.ru>
 * Created: 30.10.2015 01:20
 */
package ru.koluch.textWork.dictionary.prefixTree;

import ru.koluch.textWork.dictionary.Dictionary;

public class TreeBuilder {

    public PrefixTree<Object> build(Dictionary dictionary) {
        PrefixTree<Object> result = new PrefixTree<>();
        for (String wordForm : dictionary.allForms()) {
            if(!wordForm.contains("#") && !wordForm.contains("-") ) {
                add(wordForm.toLowerCase(), result, "some data");
            }
        }
        return result;
    }

    private <T> void add(String wordForm, PrefixTree<T> tree, T data) {
        if(wordForm.length()==0) {
            tree.data = data;
        }
        else {
            char nextBranch = wordForm.charAt(0);

            int index;
            if(nextBranch=='ё') {
                index = 32;
            }
            else {
                if(nextBranch<'а' || nextBranch>'я') {
                    throw new IllegalArgumentException("Bad branch: '" + nextBranch + "' (allowed only russian letters)");
                }
                index = nextBranch - 'а';
            }
            String rest = wordForm.substring(1);
            if(tree.branches==null) {
                tree.branches = new PrefixTree[33];  // 33 letters in russian alphabet
            }

            PrefixTree<T> nextTree;
            if(tree.branches[index]==null) {
                nextTree = new PrefixTree<T>();
                tree.branches[index] = nextTree;
            }
            else {
                nextTree = tree.branches[index];
            }
            add(rest, nextTree, data);
        }
    }

}
