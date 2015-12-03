/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 30.10.2015 01:11
 */
package ru.koluch.morphDict.prefixTree;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrefixTree<T> {

    public PrefixTree[] branches;

    public List<T> data;

    public PrefixTree() {
    }

    public void add(String wordForm, T data) {
        if(wordForm.length()==0) {
            if(this.data == null) {
                this.data = new ArrayList<>();
            }
            this.data.add(data);
        }
        else {
            char nextBranch = wordForm.charAt(0);
            int index = getIndex(nextBranch);

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

    private int getIndex(char nextBranch) {
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
        return index;
    }


    public Optional<List<T>> get(String wordForm) {
        if(wordForm.length()==0) {
            return Optional.ofNullable(this.data);
        }
        else {
            char nextBranch = wordForm.charAt(0);
            String rest = wordForm.substring(1);

            int index = getIndex(nextBranch);
            if(this.branches==null || this.branches[index]==null) {
                return Optional.empty();
            }
            return this.branches[index].get(rest);
        }
    }
}
