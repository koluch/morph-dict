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
 * Created: 30.10.2015 01:11
 */
package ru.koluch.textWork.dictionary.prefixTree;


public class PrefixTree<T> {

    public PrefixTree[] branches;

    public T data;

    public PrefixTree(T data) {
        this.data = data;
    }

    public PrefixTree() {
    }

    public void add(String wordForm, T data) {
        if(wordForm.length()==0) {
            this.data = data;
        }
        else {
            char nextBranch = wordForm.charAt(0);
            int index;
            if(nextBranch >= 'а' && nextBranch <= 'е') {
                index = nextBranch - 'а';
            }
            else if (nextBranch=='ё') {
                index = 'е' - 'а' + 1;
            }
            else if (nextBranch > 'е' && nextBranch <= 'я') {
                index = nextBranch - 'а' + 1;
            }
            else {
                throw new IllegalArgumentException("Bad branch: '" + nextBranch + "' (allowed only russian letters)");
            }

            String rest = wordForm.substring(1);
            if(this.branches==null) {
                this.branches = new PrefixTree[33];  // 33 letters in russian alphabet
            }

            PrefixTree<T> nextTree;
            if(this.branches[index]==null) {
                nextTree = new PrefixTree<T>();
                this.branches[index] = nextTree;
            }
            else {
                nextTree = this.branches[index];
            }
            nextTree.add(rest, data);
        }
    }
}
