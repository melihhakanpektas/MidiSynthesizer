/*
 * Copyright 2007 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.melihhakanpektas.midisynthesizer.sound;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.melihhakanpektas.midisynthesizer.midi.InvalidMidiDataException;
import com.melihhakanpektas.midisynthesizer.midi.Soundbank;
import com.melihhakanpektas.midisynthesizer.midi.spi.SoundbankReader;

/**
 * This class is used to connect the SF2SoundBank class
 * to the SoundbankReader SPI interface.
 *
 * @author Karl Helgason
 */
public class SF2SoundbankReader extends SoundbankReader {

    public Soundbank getSoundbank(URL url)
            throws InvalidMidiDataException, IOException {
        try {
            return new SF2Soundbank(url);
        } catch (RIFFInvalidFormatException e) {
            return null;
        } catch(IOException ioe) {
            return null;
        }
    }

    public Soundbank getSoundbank(InputStream stream)
            throws InvalidMidiDataException, IOException {
        try {
            stream.mark(512);
            return new SF2Soundbank(stream);
        } catch (RIFFInvalidFormatException e) {
            stream.reset();
            return null;
        }
    }

    public Soundbank getSoundbank(File file)
            throws InvalidMidiDataException, IOException {
        try {
            return new SF2Soundbank(file);
        } catch (RIFFInvalidFormatException e) {
            return null;
        }
    }
}
