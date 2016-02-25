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

package com.tulskiy.musique.gui.playlist;

import java.awt.BorderLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.tulskiy.musique.audio.player.Player;
import com.tulskiy.musique.audio.player.PlayerEvent;
import com.tulskiy.musique.audio.player.PlayerListener;
import com.tulskiy.musique.gui.dialogs.OptionsDialog;
import com.tulskiy.musique.gui.dialogs.PlaybackQueueDialog;
import com.tulskiy.musique.gui.dialogs.ProgressDialog;
import com.tulskiy.musique.gui.dialogs.SearchDialog;
import com.tulskiy.musique.gui.dialogs.Task;
import com.tulskiy.musique.gui.dialogs.TreeFileChooser;
import com.tulskiy.musique.images.Images;
import com.tulskiy.musique.playlist.PlaybackOrder;
import com.tulskiy.musique.playlist.Playlist;
import com.tulskiy.musique.playlist.PlaylistManager;
import com.tulskiy.musique.playlist.Track;
import com.tulskiy.musique.system.Application;
import com.tulskiy.musique.system.configuration.Configuration;
import com.tulskiy.musique.system.configuration.PlaylistConfiguration;
import com.tulskiy.musique.util.Util;

/**
 * @Author: Denis Tulskiy
 * @Date: Feb 6, 2010
 */
public class PlaylistPanel extends JPanel {

    private Application app = Application.getInstance();
    private Configuration config = app.getConfiguration();
    private PlaylistTabs tabs;
    private List<TableColumnModel> columnModels = new LinkedList<TableColumnModel>();

    public PlaylistPanel() {
        PlaylistManager playlistManager = app.getPlaylistManager();
        ArrayList<Playlist> playlists = playlistManager.getPlaylists();

        setLayout(new BorderLayout());

        tabs = new PlaylistTabs();
        add(tabs, BorderLayout.CENTER);

        List<String> bounds = PlaylistConfiguration.getTabBounds();

        for (int i = 0; i < playlists.size(); i++) {
            Playlist pl = playlists.get(i);
            PlaylistTable newTable = new PlaylistTable(pl, pl.getColumns());
            newTable.setUpDndCCP();
            columnModels.add(newTable.getColumnModel());

            //try to set last position
            try {
                String s = bounds.get(i);
                Integer y = Integer.valueOf(s);
                newTable.scrollRectToVisible(new Rectangle(0, y, 0, 0));
            } catch (Exception ignored) {
            }

            tabs.addTab(pl.getName(), newTable.getScrollPane());
        }

        final Playlist playlist = playlistManager.getActivePlaylist();

        tabs.setSelectedIndex(-1);
        tabs.setSelectedIndex(playlists.indexOf(playlist));

        PlaybackOrder order = app.getPlayer().getPlaybackOrder();
        Track lastPlayed = order.getLastPlayed();

        if (lastPlayed != null) {
            PlaylistTable table = tabs.getSelectedTable();
            if (table != null) {
                int index = table.getPlaylist().indexOf(lastPlayed);
                if (index != -1)
                    table.setRowSelectionInterval(index, index);
            }
        }

        final Player player = app.getPlayer();

        final Timer update = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.isPlaying()) {
                    PlaylistTable table = tabs.getSelectedTable();
                    if (table != null) {
                        int index = table.getPlaylist().indexOf(player.getTrack());
                        ((AbstractTableModel) table.getModel()).fireTableRowsUpdated(index, index);
                    }
                }
            }
        });

        player.addListener(new PlayerListener() {
            @Override
            public void onEvent(PlayerEvent e) {
                Track track = player.getTrack();
                if (track != null && track.getTrackData().isStream()) {
                    update.start();
                } else {
                    update.stop();
                }
            }
        });
    }

    public void saveSettings() {
        PlaylistManager playlistManager = app.getPlaylistManager();
        ArrayList<Playlist> playlists = playlistManager.getPlaylists();

        for (int n = 0; n < columnModels.size(); n++) {
            List<PlaylistColumn> columns = playlists.get(n).getColumns();
            TableColumnModel columnModel = columnModels.get(n);
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn tc = columnModel.getColumn(i);
                PlaylistColumn pc = columns.get(tc.getModelIndex());
                pc.setPosition(i);
                pc.setSize(tc.getWidth());
            }
    
            Collections.sort(columns);
            config.put("playlist.selectedTrack", null);
    
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < tabs.getTabCount(); i++) {
                PlaylistTable t = tabs.getTableAt(i);
                list.add(t.getVisibleRect().y);
            }
            // TODO check that bounds stuff works as expected
            PlaylistConfiguration.setTabBounds(list);
        }
    }

    private JMenuItem newItem(String name, String hotkey, ActionListener al) {
        JMenuItem item = new JMenuItem(name);
        item.setAccelerator(KeyStroke.getKeyStroke(hotkey));
        item.addActionListener(al);

        return item;
    }

    private Action tableAction(final String actionName, String name) {
        return new AbstractAction(name) {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table != null)
                    table.runAction(actionName);
            }
        };
    }

    public void addMenu(JMenuBar menuBar) {
        Icon emptyIcon = Images.getEmptyIcon();

        final JComponent comp = getRootPane();
        JMenu fileMenu = new JMenu("File ");
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        final JMenu playbackMenu = new JMenu("Playback");
        menuBar.add(playbackMenu);

        ActionMap tMap = tabs.getActions();
        fileMenu.add(tMap.get("newPlaylist")).setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
        fileMenu.add(tMap.get("removePlaylist")).setIcon(emptyIcon);
        fileMenu.add(tMap.get("loadPlaylist"));
        fileMenu.add(tMap.get("savePlaylist")).setAccelerator(KeyStroke.getKeyStroke("ctrl S"));

        fileMenu.addSeparator();
        fileMenu.add("Add Files").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItems(JFileChooser.FILES_ONLY);
            }
        });
        fileMenu.add("Add Folder").addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItems(JFileChooser.DIRECTORIES_ONLY);
            }
        });
        fileMenu.add("Add Location").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ret = JOptionPane.showInputDialog(comp, "Enter URL", "Add Location", JOptionPane.QUESTION_MESSAGE);
                if (!Util.isEmpty(ret)) {
                    PlaylistTable table = tabs.getSelectedTable();
                    if (table == null)
                        return;
                    table.getPlaylist().insertItem(ret, -1, false, null);
                }
            }
        });
        fileMenu.addSeparator();
        fileMenu.add(newItem("Close", "ctrl W", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (config.getBoolean("tray.enabled", false) &&
                        config.getBoolean("tray.minimizeOnClose", true)) {
                    SwingUtilities.windowForComponent(comp).setVisible(false);
                } else {
                    app.exit();
                }
            }
        }));
        fileMenu.add(newItem("Quit", "ctrl Q", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.exit();
            }
        }));

        TransferActionListener transferListener = new TransferActionListener();

        JMenuItem menuItem = new JMenuItem("Cut");
        menuItem.setActionCommand((String) TransferHandler.getCutAction().
                getValue(Action.NAME));
        menuItem.addActionListener(transferListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        menuItem.setMnemonic(KeyEvent.VK_T);
        editMenu.add(menuItem);

        menuItem = new JMenuItem("Copy");
        menuItem.setActionCommand((String) TransferHandler.getCopyAction().
                getValue(Action.NAME));
        menuItem.addActionListener(transferListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        menuItem.setMnemonic(KeyEvent.VK_C);
        editMenu.add(menuItem);

        menuItem = new JMenuItem("Paste");
        menuItem.setActionCommand((String) TransferHandler.getPasteAction().
                getValue(Action.NAME));
        menuItem.addActionListener(transferListener);
        menuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl V"));
        menuItem.setMnemonic(KeyEvent.VK_P);
        editMenu.add(menuItem);
        editMenu.addSeparator();
        editMenu.add("Clear").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table == null)
                    return;
                table.getPlaylist().clear();
                table.update();
            }
        });
        editMenu.add(tableAction("removeSelected", "Remove Tracks"));
        final String[] groupItems = {"None", "Artist", "Album Artist", "Artist/Album",
                "Artist/Album/Date", null, "Custom"};
        final String[] groupValues = {null, "%artist%", "%albumArtist%", "%albumArtist%[ - %album%]",
                "%albumArtist%[ - %album%][ '['%year%']']"
        };
        JMenu groups = new JMenu("Group playlist");
        ActionListener groupListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table == null)
                    return;

                Playlist playlist = table.getPlaylist();
                int row = table.rowAtPoint(table.getVisibleRect().getLocation());
                Track firstVisibleTrack;
                do {
                    firstVisibleTrack = playlist.get(row++);
                } while (firstVisibleTrack.getTrackData().getLocation() == null);

                JMenuItem src = (JMenuItem) e.getSource();
                Integer index = (Integer) src.getClientProperty("index");
                if (index < groupItems.length - 1) {
                    playlist.setGroupBy(groupValues[index]);
                } else {
                    Object ret = JOptionPane.showInputDialog(comp,
                            "Select formatting",
                            config.getString("playlists.groupBy", playlist.getGroupBy()));
                    if (ret != null) {
                        playlist.setGroupBy(ret.toString());
                        config.setString("playlists.groupBy", ret.toString());
                    }
                }

                int firstVisibleIndex = playlist.indexOf(firstVisibleTrack);
                if (firstVisibleIndex != -1) {
                    Rectangle cellRect = table.getCellRect(firstVisibleIndex, 0, true);
                    Rectangle visibleRect = table.getVisibleRect();
                    cellRect.setSize(visibleRect.width, visibleRect.height);
                    table.scrollRectToVisible(cellRect);
                }

                table.update();
            }
        };

        for (int i = 0; i < groupItems.length; i++) {
            String groupValue = groupItems[i];
            if (groupValue == null) {
                groups.addSeparator();
                continue;
            }

            AbstractButton item = groups.add(groupValue);
            item.setIcon(emptyIcon);
            item.addActionListener(groupListener);
            item.putClientProperty("index", i);
        }

        editMenu.add(groups);

        JMenu sort = new JMenu("Sort");
        String[] sortItems = {
                "Sort by...", "Randomize", "Reverse",
                "Sort by Artist", "Sort by Album",
                "Sort by File Path", "Sort by Title",
                "Sort by Track Number", "Sort by Album Artist/Year/Album/Disc/Track/File Name"
        };

        final String[] sortValues = {
                null, null, null, "%artist%", "%album%",
                "%file%", "%title%", "%trackNumber%",
                "%albumArtist% - %year% - %album% - %discNumber% - %trackNumber% - %fileName%"
        };

        ActionListener sortListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem src = (JMenuItem) e.getSource();
                Integer index = (Integer) src.getClientProperty("index");
                PlaylistTable table = tabs.getSelectedTable();
                if (table == null)
                    return;

                Playlist playlist = table.getPlaylist();
                switch (index) {
                    case 0:
                        Object ret = JOptionPane.showInputDialog(comp,
                                "Sort By...",
                                config.getString("playlist.sortString", ""));
                        if (ret != null) {
                            playlist.sort(ret.toString(), false);
                            config.setString("playlist.sortString", ret.toString());
                        }

                        break;
                    case 1:
                        Collections.shuffle(playlist);
                        playlist.firePlaylistChanged();
                        break;
                    case 2:
                        Collections.reverse(playlist);
                        playlist.firePlaylistChanged();
                        break;
                    default:
                        playlist.sort(sortValues[index], false);
                }
            }
        };

        for (int i = 0; i < sortItems.length; i++) {
            String sortValue = sortItems[i];
            if (sortValue == null) {
                sort.addSeparator();
                continue;
            }

            AbstractButton item = sort.add(sortValue);
            item.setIcon(emptyIcon);
            item.addActionListener(sortListener);
            item.putClientProperty("index", i);
        }

        editMenu.add(sort);
        editMenu.addSeparator();
        editMenu.add(tableAction("clearQueue", "Clear Playback Queue"));
        editMenu.add(new JMenuItem("View Playback Queue")).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PlaybackQueueDialog(comp).setVisible(true);
            }
        });
        editMenu.add(newItem("Search", "ctrl F", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table == null)
                    return;
                new SearchDialog(table).setVisible(true);
            }
        }));
        editMenu.add("Remove Dead Items").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table != null) {
                    table.getPlaylist().removeDeadItems();
                }
            }
        });
        editMenu.add("Remove Duplicates").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlaylistTable table = tabs.getSelectedTable();
                if (table != null) {
                    table.getPlaylist().removeDuplicates();
                }
            }
        });
        editMenu.addSeparator();
        JMenuItem propsItem = editMenu.add("Properties");
        propsItem.setIcon(emptyIcon);
        propsItem.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        propsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OptionsDialog(comp).setVisible(true);
            }
        });

        JMenu orderMenu = new JMenu("Order");
        playbackMenu.add(orderMenu);
        ActionListener orderListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem item = (JMenuItem) e.getSource();
                int index = (Integer) item.getClientProperty("order");
                config.setInt("player.playbackOrder", index);
            }
        };

        final ButtonGroup gr = new ButtonGroup();
        for (PlaybackOrder.Order o : PlaybackOrder.Order.values()) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(o.toString());
            item.addActionListener(orderListener);
            item.putClientProperty("order", o.ordinal());
            gr.add(item);
            orderMenu.add(item);
        }

        config.addPropertyChangeListener("player.playbackOrder", true, new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                int value = config.getInt(evt.getPropertyName(), 0);
                Enumeration<AbstractButton> items = gr.getElements();
                while (items.hasMoreElements()) {
                    AbstractButton item = items.nextElement();
                    if (item.getClientProperty("order").equals(value)) {
                        item.setSelected(true);
                    }
                }
            }
        });

        playbackMenu.addSeparator();

        playbackMenu.add(tableAction("showNowPlaying", "Scroll to Now Playing"));
        boolean selected = config.getBoolean("playlists.cursorFollowsPlayback", true);
        playbackMenu.add(new JCheckBoxMenuItem("Cursor Follows Playback", selected)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
                config.setBoolean("playlists.cursorFollowsPlayback", item.isSelected());
            }
        });

        selected = config.getBoolean("playlists.playbackFollowsCursor", false);
        playbackMenu.add(new JCheckBoxMenuItem("Playback Follows Cursor", selected)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
                config.setBoolean("playlists.playbackFollowsCursor", item.isSelected());
            }
        });

        final JCheckBoxMenuItem stopAfterCurrent = new JCheckBoxMenuItem("Stop After Current");
        playbackMenu.add(stopAfterCurrent).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
                app.getPlayer().setStopAfterCurrent(item.isSelected());
            }
        });

        app.getPlayer().addListener(new PlayerListener() {
            @Override
            public void onEvent(PlayerEvent e) {
                if (e.getEventCode() == PlayerEvent.PlayerEventCode.STOPPED) {
                    stopAfterCurrent.setSelected(false);
                }
            }
        });
    }

    private void addItems(int selectionMode) {
        boolean allowFiles = selectionMode != JFileChooser.DIRECTORIES_ONLY;
        TreeFileChooser fc = new TreeFileChooser(this,
                allowFiles ? "Open file" : "Open folder",
                allowFiles);
        File[] files = fc.showOpenDialog();

        if (files != null) {
            final PlaylistTable table = tabs.getSelectedTable();
            if (table == null)
                return;
            ProgressDialog dialog = new ProgressDialog(table.getParentFrame(), "Adding Files");
            dialog.show(new Task.FileAddingTask(table.getPlaylist(), files, -1));
        }
    }

    public class TransferActionListener implements ActionListener,
            PropertyChangeListener {
        private JComponent focusOwner = null;

        public TransferActionListener() {
            KeyboardFocusManager manager = KeyboardFocusManager.
                    getCurrentKeyboardFocusManager();
            manager.addPropertyChangeListener("permanentFocusOwner", this);
        }

        public void propertyChange(PropertyChangeEvent e) {
            Object o = e.getNewValue();
            if (o instanceof JComponent) {
                focusOwner = (JComponent) o;
            } else {
                focusOwner = null;
            }
        }

        public void actionPerformed(ActionEvent e) {
            if (focusOwner == null)
                return;
            String action = e.getActionCommand();
            Action a = focusOwner.getActionMap().get(action);
            if (a != null) {
                a.actionPerformed(new ActionEvent(focusOwner,
                        ActionEvent.ACTION_PERFORMED,
                        null));
            }
        }
    }
}
