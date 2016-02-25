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

package com.tulskiy.musique.audio;

import com.tulskiy.musique.system.configuration.Configuration;

import javax.sound.sampled.AudioFormat;
import java.io.File;
import java.util.logging.Logger;

/**
 * Author: Denis Tulskiy
 * Date: Jul 25, 2010
 */
public interface Encoder {
    final Logger logger = Logger.getLogger(Encoder.class.getName());

    public boolean open(File outputFile, AudioFormat fmt, Configuration options);

    public void encode(byte[] buf, int len);

    public void close();
}
