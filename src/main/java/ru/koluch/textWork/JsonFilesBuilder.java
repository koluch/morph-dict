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
import ru.koluch.textWork.dictionary.prefixTree.PrefixTree;

import java.io.*;
import java.util.List;
import java.util.function.Function;

public class JsonFilesBuilder {

    private int counter;
    private Writer writer;
    private Gson gson = new Gson();

    public <T> void build(File dir, PrefixTree<T> tree, Function<List<T>,String> dataSerializer) throws IOException {
        try {
            counter = 0;
            writer = new BufferedWriter(new FileWriter(new File(dir, "1.json")));
            traverse(tree, dataSerializer);
        } finally {
            if(writer!=null) {
                writer.close();
            }
        }
    }

    public <T> int traverse(PrefixTree<T> tree, Function<List<T>,String> dataSerializer) throws IOException {
        JsonArray node = new JsonArray();
        JsonObject branches = new JsonObject();

        if(tree.branches != null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree branch = tree.branches[i];
                if(branch!=null) {
                    int index = traverse(branch, dataSerializer);
                    branches.add(String.valueOf(i), new JsonPrimitive(index));
                }
            }
        }
        node.add(branches);
        if(tree.data!=null) {
            node.add(new JsonPrimitive(dataSerializer.apply(tree.data)));
        }
        int index = write(gson.toJson(node));
        return index;
    }

    private int write(String json) throws IOException {
        writer.write(json);
        writer.write("\n");

        return counter++;
    }



}
