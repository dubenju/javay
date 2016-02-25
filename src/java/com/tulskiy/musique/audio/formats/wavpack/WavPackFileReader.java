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

package com.tulskiy.musique.audio.formats.wavpack;

import com.tulskiy.musique.audio.AudioFileReader;
import com.tulskiy.musique.audio.formats.ape.APETagProcessor;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.playlist.TrackData;
import com.wavpack.decoder.WavPackUtils;
import com.wavpack.decoder.WavpackContext;

import java.io.RandomAccessFile;

/**
 * @Author: Denis Tulskiy
 * @Date: 01.07.2009
 */
public class WavPackFileReader extends AudioFileReader {
    private static APETagProcessor apeTagProcessor = new APETagProcessor();

    public Track readSingle(Track track) {
    	TrackData trackData = track.getTrackData();
        try {
            apeTagProcessor.readAPEv2Tag(track);
            RandomAccessFile raf = new RandomAccessFile(trackData.getFile(), "r");
            WavpackContext wpc = WavPackUtils.WavpackOpenFileInput(raf);
            trackData.setTotalSamples(WavPackUtils.WavpackGetNumSamples(wpc));
            trackData.setSampleRate((int) WavPackUtils.WavpackGetSampleRate(wpc));
            trackData.setChannels(WavPackUtils.WavpackGetReducedChannels(wpc));
            trackData.setBitrate((int) (raf.length() / trackData.getTotalSamples() / 1000 * 8));
            trackData.setCodec("WavPack");
            raf.close();
        } catch (Exception e) {
            System.out.println("Couldn't read file: " + trackData.getFile());
        }
        return track;
    }

    public boolean isFileSupported(String ext) {
        return ext.equalsIgnoreCase("wv");
    }

}
