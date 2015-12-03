/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 31.10.2015 18:26
 */
package ru.koluch.textWork.morphDict.prefixTree;

/**
 * Auxiliary class to countNodes things inside tree
 */
public class Statistics {

    public static  <T> Integer countNodes(PrefixTree<T> tree) {
        int result = 1;
        if(tree.branches!=null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    result += countNodes(branch);
                }
            }
        }
        return result;
    }

    public static  <T> Integer countLeafs(PrefixTree<T> tree) {
        if(tree.branches!=null) {
            int result = 0;
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    result += countLeafs(branch);
                }
            }
            return result;
        }
        else {
            return 1;
        }
    }

    public static  <T> Integer countMaxDeep(PrefixTree<T> tree) {
        int result = 0;
        if(tree.branches!=null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    int branchDeep = 1 + countMaxDeep(branch);
                    result = Math.max(result, branchDeep);
                }
            }
        }
        return result;
    }

}
