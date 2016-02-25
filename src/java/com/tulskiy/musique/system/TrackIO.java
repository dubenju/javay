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

package com.tulskiy.musique.system;

import com.tulskiy.musique.audio.AudioFileReader;
import com.tulskiy.musique.audio.AudioTagWriter;
import com.tulskiy.musique.audio.formats.mp4.MP4FileReader;
import com.tulskiy.musique.audio.formats.ape.APEFileReader;
import com.tulskiy.musique.audio.formats.ape.APETagWriter;
import com.tulskiy.musique.audio.formats.cue.CUEFileReader;
import com.tulskiy.musique.audio.formats.flac.FLACFileReader;
import com.tulskiy.musique.audio.formats.mp3.MP3FileReader;
import com.tulskiy.musique.audio.formats.mp3.MP3TagWriter;
import com.tulskiy.musique.audio.formats.mp4.MP4TagWriter;
import com.tulskiy.musique.audio.formats.ogg.OGGFileReader;
import com.tulskiy.musique.audio.formats.ogg.VorbisTagWriter;
import com.tulskiy.musique.audio.formats.tta.TTAFileReader;
import com.tulskiy.musique.audio.formats.uncompressed.PCMFileReader;
import com.tulskiy.musique.audio.formats.wavpack.WavPackFileReader;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.util.Util;

import java.util.ArrayList;

/**
 * Author: Denis Tulskiy
 * Date: Jun 22, 2010
 */
public class TrackIO {
    private static ArrayList<AudioFileReader> readers;
    private static ArrayList<AudioTagWriter> writers;

    static {
        readers = new ArrayList<AudioFileReader>();
        readers.add(new MP3FileReader());
        readers.add(new APEFileReader());
        readers.add(new CUEFileReader());
        readers.add(new FLACFileReader());
        readers.add(new OGGFileReader());
        readers.add(new PCMFileReader());
        readers.add(new WavPackFileReader());
        readers.add(new MP4FileReader());
        readers.add(new TTAFileReader());

        writers = new ArrayList<AudioTagWriter>();
        writers.add(new MP3TagWriter());
        writers.add(new APETagWriter());
        writers.add(new VorbisTagWriter());
        writers.add(new MP4TagWriter());
    }

    public static AudioFileReader getAudioFileReader(String fileName) {
        String ext = Util.getFileExt(fileName);
        for (AudioFileReader reader : readers) {
            if (reader.isFileSupported(ext))
                return reader;
        }

        return null;
    }

    public static AudioTagWriter getAudioFileWriter(String fileName) {
        String ext = Util.getFileExt(fileName).toLowerCase();
        for (AudioTagWriter writer : writers) {
            if (writer.isFileSupported(ext))
                return writer;
        }

        return null;
    }

    public static void write(Track track) {
        if (track.getTrackData().isFile()) {
            AudioTagWriter writer = TrackIO.getAudioFileWriter(track.getTrackData().getFile().getName());
            if (writer != null)
                try {
                    writer.write(track);
                } catch (com.tulskiy.musique.audio.TagWriteException e) {
                    e.printStackTrace();
                }
        }
    }
}
