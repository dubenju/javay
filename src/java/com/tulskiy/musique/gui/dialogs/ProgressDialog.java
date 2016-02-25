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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Denis Tulskiy
 * Date: Jun 10, 2010
 */
public class ProgressDialog extends JDialog {
    private JProgressBar progress;
    private JTextArea status;
    private Task task;
    private Thread thread;
    private boolean aborted;

    public ProgressDialog(Component owner, String message) {
        super(SwingUtilities.windowForComponent(owner), message);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        progress = new JProgressBar(0, 100);
        status = new JTextArea(2, 10);
        status.setEditable(false);
        status.setOpaque(false);
        status.setBorder(BorderFactory.createEmptyBorder());
        status.setFont(status.getFont().deriveFont(11f));
        status.setEnabled(false);
        status.setDisabledTextColor(Color.black);
        JButton cancel = new JButton("Abort");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!aborted) {
                    task.abort();
                    aborted = true;
                } else {
                    /*
                    I assume that a user will click this button
                    twice only if the task does not respond,
                    most probably waiting on IO,
                    in this case, we interrupt the thread
                    */
                    thread.interrupt();
                }
            }
        });

        Box box = Box.createVerticalBox();
        box.add(status);
        box.add(Box.createVerticalStrut(20));
        box.add(progress);
        Box buttonBox = Box.createHorizontalBox();
        buttonBox.add(Box.createHorizontalGlue());
        buttonBox.add(cancel);
        box.add(Box.createVerticalStrut(10));
        box.add(buttonBox);
        box.add(Box.createVerticalStrut(5));

        box.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        add(box, BorderLayout.NORTH);
    }

    public void show(final Task task) {
        this.task = task;
        progress.setIndeterminate(task.isIndeterminate());
        pack();
        setSize(600, getHeight());
        setLocationRelativeTo(getParent());
        aborted = false;
        setVisible(true);

        final Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progress.setValue((int) (progress.getMaximum() * task.getProgress()));
                status.setText(task.getStatus());
                String title = task.getTitle();
                if (title != null)
                    setTitle(title);
                progress.setIndeterminate(task.isIndeterminate());
            }
        });
        timer.start();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    task.start();
                    timer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    setVisible(false);
                    dispose();
                }
            }
        });
        thread.start();
    }
}

