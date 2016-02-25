/*
 * Copyright (c) 2008, 2009, 2010, 2011 Denis Tulskiy
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

package com.tulskiy.musique.gui.menu;

import com.tulskiy.musique.playlist.Playlist;
import com.tulskiy.musique.playlist.Track;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Author: Denis Tulskiy
 * Date: 2/27/11
 */
public abstract class Menu {
    protected static final ArrayList<MenuCallback> menus = new ArrayList<MenuCallback>();

    public static void addMenu(MenuCallback menu) {
        menus.add(menu);
    }

    public interface MenuCallback {
        public JMenu create(ArrayList<Track> tracks, Playlist playlist);
    }
}
