package ru.koluch.textWork.dictionaryParser;

import org.apache.log4j.Logger;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:40 PM
 */

public class Ancode {
    private static final Logger log = Logger.getLogger(Ancode.class);

    public final String value;

    public Ancode(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ancode)) return false;

        Ancode ancode = (Ancode) o;

        return value.equals(ancode.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Ancode{" +
                "value='" + value + '\'' +
                '}';
    }

    public static Ancode ANCODE(String value)
    {
        return new Ancode(value); //todo:implement cache
    }
}
