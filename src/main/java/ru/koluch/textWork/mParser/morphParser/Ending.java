package ru.koluch.textWork.mParser.morphParser;

import org.apache.log4j.Logger;

/**
 * @author Nikolay Mavrenkov <koluch@koluch.ru>
 *         Date: 9/5/13
 *         Time: 11:39 PM
 */

public class Ending {
    public final String value;

    public Ending(String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ending)) return false;

        Ending ending = (Ending) o;

        if (!value.equals(ending.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "Ending{" +
                "value='" + value + '\'' +
                '}';
    }

    public static Ending ENDING(String value)
    {
        return new Ending(value); // todo: implement cache
    }

}
