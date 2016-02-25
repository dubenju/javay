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

package com.tulskiy.musique.audio.formats.cue;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.List;

import org.jaudiotagger.tag.FieldKey;

import jwbroek.cuelib.CueParser;
import jwbroek.cuelib.CueSheet;
import jwbroek.cuelib.FileData;
import jwbroek.cuelib.Index;
import jwbroek.cuelib.TrackData;

import com.tulskiy.musique.audio.AudioFileReader;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.system.TrackIO;

/**
 * @Author: Denis Tulskiy
 * @Date: 29.06.2009
 */
public class CUEParser {
    public void parse(List<Track> list, Track file, LineNumberReader cueStream, boolean embedded) {
        try {
            CueSheet cueSheet = CueParser.parse(cueStream);
            List<FileData> datas = cueSheet.getFileData();
            String cueLocation = file.getTrackData().getFile().getAbsolutePath();
            if (datas.size() > 0) {
                for (FileData fileData : datas) {
                    if (!embedded) {
                        String parent = file.getTrackData().getFile().getParent();
                        File referencedFile = new File(parent, fileData.getFile());
                        if (!referencedFile.exists())
                            continue;
                        AudioFileReader reader = TrackIO.getAudioFileReader(referencedFile.getName());
                        if (reader == null) break;
                        file = reader.read(referencedFile);
                    }

                    int size = fileData.getTrackData().size();
                    for (int i = 0; i < size; i++) {
                        TrackData trackData = fileData.getTrackData().get(i);
                        Track track = file.copy();
                        track.getTrackData().setCueEmbedded(embedded);
                        if (!embedded)
                            track.getTrackData().setCueLocation(cueLocation);

                        String album = trackData.getMetaData(CueSheet.MetaDataField.ALBUMTITLE);
                        if (album.length() > 0)
                            track.getTrackData().setTagFieldValues(FieldKey.ALBUM, album);
                        String artist = trackData.getPerformer();
                        track.getTrackData().setTagFieldValues(FieldKey.ARTIST, artist != null && artist.length() > 0 ? artist : cueSheet.getPerformer());
                        track.getTrackData().setTagFieldValues(FieldKey.ALBUM_ARTIST, cueSheet.getPerformer());
                        track.getTrackData().setTagFieldValues(FieldKey.COMMENT, cueSheet.getComment());
                        track.getTrackData().setTagFieldValues(FieldKey.TITLE, trackData.getTitle());
                        String year = trackData.getMetaData(CueSheet.MetaDataField.YEAR);
                        if (year.length() > 0)
                        	track.getTrackData().setTagFieldValues(FieldKey.YEAR, year);
                        track.getTrackData().setTagFieldValues(FieldKey.TRACK, String.valueOf(trackData.getNumber()));
                        String genre = trackData.getMetaData(CueSheet.MetaDataField.GENRE);
                        if (genre.length() > 0)
                            track.getTrackData().setTagFieldValues(FieldKey.GENRE, genre);
                        int sampleRate = track.getTrackData().getSampleRate();
                        long startPosition = indexToSample(trackData.getIndex(1), sampleRate);
//                        System.out.println(song.getFile().getName() + " " + startPosition);
                        long endPosition;
                        if (i >= size - 1) {
                            endPosition = track.getTrackData().getTotalSamples();
                        } else {
                            TrackData nextTrack = fileData.getTrackData().get(i + 1);
                            endPosition = indexToSample(nextTrack.getIndex(1), sampleRate);
                        }
                        track.getTrackData().setTotalSamples(endPosition - startPosition);
                        track.getTrackData().setSubsongIndex(i + 1);
                        track.getTrackData().setStartPosition(startPosition);
                        list.add(track);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long indexToSample(Index index, int sampleRate) {
//        System.out.println(index.getPosition().getTotalFrames() / 75d * sampleRate);
        return (long) (index.getPosition().getTotalFrames() / 75d * sampleRate);
    }
}
