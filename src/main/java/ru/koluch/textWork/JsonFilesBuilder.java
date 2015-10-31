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
 * Created: 31.10.2015 21:54
 */
package ru.koluch.textWork;


import com.google.gson.*;
import ru.koluch.textWork.dictionary.Dictionary;
import ru.koluch.textWork.dictionary.prefixTree.PrefixTree;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class JsonFilesBuilder {

    private int counter;

    public <T> int build(Writer writer, PrefixTree<T> tree) throws IOException {
        Gson gson = new Gson();

        JsonArray node = new JsonArray();
        JsonObject branches = new JsonObject();

        if(tree.branches != null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    int index = build(writer, branch);
                    branches.add(String.valueOf(i), new JsonPrimitive(index));
                }
            }
        }

        node.add(branches);
        writer.write(gson.toJson(node));
        writer.write("\n");

        return counter++;
    }




}
