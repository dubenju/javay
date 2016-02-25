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

package com.tulskiy.musique.playlist.formatting.tokens;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.tulskiy.musique.gui.model.FieldValues;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.playlist.TrackData;

/**
 * @Author: Denis Tulskiy
 * @Date: Feb 6, 2010
 */
public class ParameterExpression implements Expression {
    private Method method;

    public ParameterExpression(String name) {
        try {
            method = TrackData.class.getDeclaredMethod("get" + name);
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
            try {
                method = TrackData.class.getDeclaredMethod("is" + name);
            } catch (NoSuchMethodException e1) {
//                e.printStackTrace();
            }
        }
    }

    public Object eval(Track track) {
        if (method == null)
            return null;
        try {
            Object o = method.invoke(track.getTrackData());
            if (o == null)
                return null;
            // hack for multi-valued tag fields
            return o instanceof FieldValues ? o : o.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String toString() {
        return "ParameterExpression{" +
               "method=" + method +
               '}';
    }
}
