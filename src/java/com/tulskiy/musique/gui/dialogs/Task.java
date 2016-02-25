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

package com.tulskiy.musique.gui.dialogs;

import com.tulskiy.musique.playlist.Playlist;

import java.io.File;
import java.util.HashMap;

/**
 * Author: Denis Tulskiy
 * Date: Jul 9, 2010
 */
public abstract class Task {
    public static class FileAddingTask extends Task {
        private Playlist playlist;
        private File[] files;
        private int location;

        public FileAddingTask(Playlist playlist, File[] files, int location) {
            this.playlist = playlist;
            this.files = files;
            this.location = location;
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        @Override
        public String getStatus() {
            return "Reading File: " + String.valueOf(map.get("processing.file"));
        }

        @Override
        public void abort() {
            map.put("processing.stop", true);
        }

        @Override
        public void start() {
            for (File file : files) {
                int ret = playlist.insertItem(file.toString(), location, true, map);
                if (location != -1)
                    location += ret;
            }
        }
    }

    public boolean isIndeterminate() {
        return true;
    }

    public float getProgress() {
        return 0;
    }

    public String getTitle() {
        return null;
    }

    public abstract String getStatus();

    public abstract void abort();

    public abstract void start();
}
