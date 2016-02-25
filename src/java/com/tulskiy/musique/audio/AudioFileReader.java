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

package com.tulskiy.musique.audio;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jaudiotagger.audio.generic.GenericAudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.mp4.field.Mp4DiscNoField;
import org.jaudiotagger.tag.mp4.field.Mp4TrackField;

import com.tulskiy.musique.audio.formats.cue.CUEParser;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.playlist.TrackData;

/**
 * Author: Denis Tulskiy
 * Date: 25.06.2009
 */
public abstract class AudioFileReader {
    private static CUEParser cueParser;
    protected static Charset defaultCharset = Charset.forName("iso8859-1");
    protected final Logger logger = Logger.getLogger(getClass().getName());

    public void read(File file, List<Track> list) {
        logger.log(Level.FINEST, "Reading file : {0}", file);
        Track track = read(file);
        String cueSheet = track.getTrackData().getCueSheet();
        if (cueSheet != null && cueSheet.length() > 0) {
            if (cueParser == null)
                cueParser = new CUEParser();
            LineNumberReader reader = new LineNumberReader(new StringReader(cueSheet));
            cueParser.parse(list, track, reader, true);
        } else {
            list.add(track);
        }
    }

    protected abstract Track readSingle(Track track);

    public Track reload(Track track) {
        Track res = readSingle(track);
        if (res.getTrackData().isFile())
            res.getTrackData().setLastModified(res.getTrackData().getFile().lastModified());
        return res;
    }

    public Track read(File file) {
        Track track = new Track();
        track.getTrackData().setLocation(file.toURI().toString());
        return reload(track);
    }

    public abstract boolean isFileSupported(String ext);

    // in case of logic change, review MP3TagReader and APETagProcessor
    protected void copyCommonTagFields(Tag tag, Track track) throws IOException {
    	TrackData trackData = track.getTrackData();
        if (tag != null && track != null) {
    		for (FieldKey key : FieldKey.values()) {
    			List<TagField> fields;
    			try {
    				fields = tag.getFields(key);
    			}
    			catch (KeyNotFoundException knfe) {
    				// TODO review
    				continue;
    			}
    			catch (NullPointerException npe) {
    				// TODO review workaround for mp4tag (throws nullpointer if no mapping found for generic key)
    				continue;
    			}
    			for (TagField field : fields) {
    				// TODO think how to minimize custom check impact
    				if (field instanceof Mp4TrackField || field instanceof Mp4DiscNoField) {
    					break;
    				}
    				track.getTrackData().addTagFieldValues(key, field.toString());
    			}
    		}

    		// TODO think about the way
        	trackData.setCueSheet(tag.getFirst("CUESHEET"));
        }
    }

    protected void copySpecificTagFields(Tag tag, Track track) {
    	// Empty implementation, to be overridden
    }

    protected void copyHeaderFields(GenericAudioHeader header, Track track) {
        if (header != null && track != null) {
            TrackData trackData = track.getTrackData();
        	trackData.setChannels(header.getChannelNumber());
        	trackData.setTotalSamples(header.getTotalSamples());

//            trackData.setTotalSamples((long) (header.getPreciseLength() * header.getSampleRateAsNumber()));
        	trackData.setSampleRate(header.getSampleRateAsNumber());
        	trackData.setStartPosition(0);
        	trackData.setCodec(header.getFormat());
            trackData.setBitrate((int) header.getBitRateAsNumber());
        }
    }

    public static void setDefaultCharset(Charset charset) {
        defaultCharset = charset;
    }

    public static Charset getDefaultCharset() {
        return defaultCharset;
    }
}
