/**
 * Copyright (c) 2015 Nikolai Mavrenkov <koluch@koluch.ru>
 * <p>
 * Distributed under the MIT License (See accompanying file LICENSE or copy at http://opensource.org/licenses/MIT).
 * <p>
 * Created: 02.12.2015 15:24
 */
package ru.koluch.textWork.morphDict.lookup;


import java.util.ArrayList;

public interface LookupService {
    ArrayList<LookupResult> find(String toFind);
}
