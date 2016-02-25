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

package com.tulskiy.musique.audio.formats.ogg;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.audio.ogg.OggFileReader;
import org.jaudiotagger.tag.Tag;

import com.tulskiy.musique.audio.AudioFileReader;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.playlist.TrackData;

/**
 * @Author: Denis Tulskiy
 * @Date: 29.06.2009
 */
public class OGGFileReader extends AudioFileReader {
    private static VorbisDecoder vorbisdecoder = new VorbisDecoder();

    public Track readSingle(Track track) {
    	TrackData trackData = track.getTrackData();
        try {
            OggFileReader reader = new OggFileReader();
            AudioFile af1 = reader.read(trackData.getFile());
            Tag tag = af1.getTag();
            copyCommonTagFields(tag, track);
            copySpecificTagFields(tag, track);
            copyHeaderFields((GenericAudioHeader) af1.getAudioHeader(), track);
        } catch (Exception e) {
            System.out.println("Couldn't read file: " + trackData.getFile());
        }
        return track;
    }

    public boolean isFileSupported(String ext) {
        return ext.equalsIgnoreCase("ogg");
    }
    
//    @Override
//    public void copySpecificTagFields(Tag tag, Track track) {
//    	VorbisCommentTag vcTag = (VorbisCommentTag) tag;
//    }

}
