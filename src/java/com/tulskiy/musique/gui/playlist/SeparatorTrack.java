/*
 * Copyright (c) 2008, 2009, 2010 Denis Tulskiy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with this work.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tulskiy.musique.gui.playlist;

import com.tulskiy.musique.gui.components.Separator;
import com.tulskiy.musique.playlist.Track;

/**
 * Author: Denis Tulskiy
 * Date: Jun 30, 2010
 */
public class SeparatorTrack extends Track implements Separator {
    private int size;
    private String name;

    public SeparatorTrack(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getGroupName() {
        return name;
    }

    @Override
    public int getGroupSize() {
        return size;
    }

//    @Override
//    public boolean equals(Object obj) {
//        return obj instanceof Separator;
//    }

    @Override
    public String toString() {
        return null;
    }
}
