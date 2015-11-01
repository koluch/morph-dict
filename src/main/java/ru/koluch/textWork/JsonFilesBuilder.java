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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class JsonFilesBuilder {

    private int counter;
    private int linesPerFile = 500;
    private Writer writer;
    private Gson gson = new Gson();
    private File dir;

    public <T> void build(File dir, PrefixTree<T> tree, Function<List<T>,JsonElement> dataSerializer) throws IOException {
        this.dir = dir;
        int root;
        try {
            root = traverse(tree, dataSerializer);
        } finally {
            if(writer!=null) {
                flush();
                writer.close();
            }
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(dir, "index.json")))) {
            JsonObject indexJson = new JsonObject();
            indexJson.add("root", new JsonPrimitive(root));
            indexJson.add("linesPerFile", new JsonPrimitive(linesPerFile));
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder.create();
            writer.write(gson.toJson(indexJson));
        }
    }

    public <T> int traverse(PrefixTree<T> tree, Function<List<T>,JsonElement> dataSerializer) throws IOException {
        JsonArray node = new JsonArray();
        JsonObject branches = new JsonObject();

        if(tree.branches != null) {
            for (int i = 0; i < tree.branches.length; i++) {
                PrefixTree<T> branch = tree.branches[i];
                if(branch!=null) {
                    int index = traverse(branch, dataSerializer);
                    char br;
                    char iCh = (char) ('а' + i);
                    if(iCh >= 'а' && iCh <= 'е') {
                        br = iCh;
                    }
                    else if(iCh == 'е' + 1) {
                        br = 'ё';
                    }
                    else {
                        br = (char) (iCh-1);
                    }

                    branches.add(String.valueOf(br), new JsonPrimitive(index));
                }
            }
        }
        node.add(branches);
        if(tree.data!=null) {
            node.add(dataSerializer.apply(tree.data));
        }
        int index = write(gson.toJson(node));
        return index;
    }


    private List<String> lines = new ArrayList<>();

    private int write(String json) throws IOException {

        if(counter % linesPerFile == 0) {
            if(writer!=null) {
                flush();
                writer.close();
            }
            writer = new BufferedWriter(new FileWriter(new File(dir, (counter / linesPerFile) + ".json")));
//            writer.write('[');
//            writer.write('\n');
        }

//        writer.write(json);
//        if((counter+1) % linesPerFile != 0) {
//            writer.write(",");
//        }
//        writer.write("\n");
        lines.add(json);

        return counter++;
    }

    private void flush() throws IOException {
        writer.write("[\n");
        for (int i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            writer.write("\n");
            if(i!=lines.size()-1) {
                writer.write(",");
            }
        }
        writer.write("]");
        lines.clear();
    }



}
