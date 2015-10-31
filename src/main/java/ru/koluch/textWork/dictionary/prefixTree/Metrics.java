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
 * Created: 31.10.2015 18:26
 */
package ru.koluch.textWork.dictionary.prefixTree;

/**
 * Auxiliary class to count things inside tree
 */
public class Metrics {

    public static  <T> Integer count(PrefixTree<T> tree) {
        int result = 1;
        if(tree.branches!=null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    result += count(branch);
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
