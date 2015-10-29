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
                result.add(wordForm.toLowerCase(), "some data");
            }
        }
        return result;
    }



}
