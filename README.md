# morph-dict

Morphological dictionary for Java, based on [AOT project](http://aot.ru) technologies.

## Example
```java
import ru.koluch.textWork.morphDict.dictionary.Attribute;
import ru.koluch.textWork.morphDict.dictionary.DictionaryHelper;
import ru.koluch.textWork.morphDict.dictionary.data.Dictionary;
import ru.koluch.textWork.morphDict.lookup.PrefixTreeLookupService;
import ru.koluch.textWork.morphDict.lookup.data.LookupResult;
import ru.koluch.textWork.morphDict.lookup.data.WordForm;
import ru.koluch.textWork.morphDict.prefixTree.PrefixTree;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static ru.koluch.textWork.morphDict.dictionary.DictionaryHelper.buildPrefixTree;

public class Example {

    public static void main(String[] args) throws Throwable {
        // Load dictionary from resource file
        Dictionary dictionary;
        try(InputStreamReader reader = new InputStreamReader(Dictionary.class.getResourceAsStream("/morphs.mrd"), "UTF-8")) {
            dictionary = DictionaryHelper.parse(reader);
        }
       
        // Build prefix tree for dictionary
        PrefixTree<DictionaryHelper.TreeData> tree = buildPrefixTree(dictionary);

        // Make lookup service for searching words in dictionary
        PrefixTreeLookupService lookupService = new PrefixTreeLookupService(dictionary, tree);

        // Search for word form ("машинами" means "cars" in instrumental case)
        // Every word form could be a part of several lexemes, or it could be found several times in one lexeme,
        // so we have list of found results instead of one result
        ArrayList<LookupResult> resultList = lookupService.lookup("машинами");

        // For every result (for "машинами" we have only one result) ...
        for (LookupResult result : resultList) {
            // Print attributes for found word form
            System.out.println("Attributes:");
            for (Attribute attribute : DictionaryHelper.getAttributes(result.wordForm.ancode)) {
                System.out.println(attribute);
            }

            // Print all homonyms
            System.out.println("\nHomonyms:");
            for (WordForm homonym : result.lexeme.homonyms) {
                System.out.println(homonym.makeWord());
            }
        }
    }

}
```

Output:

```
Attributes:
NOUN
INSTRUMENTAL_CASE
FEMININE_GENDER
PLURAL

Homonyms:
машина
машины
машине
машину
машиной
машиною
машине
машины
машин
машинам
машины
машинами
машинах
```

## Links

Source dictionary files format description (in russian):
[https://code.google.com/p/opencorpora/wiki/MRDFileFormat](https://code.google.com/p/opencorpora/wiki/MRDFileFormat)

Attributes description (in russian):
[http://www.aot.ru/docs/rusmorph.html](http://www.aot.ru/docs/rusmorph.html) 
