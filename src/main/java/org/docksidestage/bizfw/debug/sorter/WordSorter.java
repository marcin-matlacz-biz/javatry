package org.docksidestage.bizfw.debug.sorter;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.docksidestage.bizfw.debug.Word;
import org.docksidestage.bizfw.debug.WordPool;

/**
 * @author zaya
 */
public class WordSorter implements Sorter<Word> {
    public List<Word> words;
    private final List<Sorter<Word>> sorters = Arrays.asList(
            new BubbleSorter(),
            new SelectionSorter(),
            new QuickSorter()
    );

    public WordSorter() {
        words = new WordPool().getWords();
    }

    @Override
    public List<Word> sort() {
        int i = new Random().nextInt(sorters.size());
        return sorters.get(i).sort();
    }

    @Override
    public List<Word> sort(List<Word> list) {
        int i = new Random().nextInt(sorters.size());
        return sorters.get(i).sort(list);
    }
}
