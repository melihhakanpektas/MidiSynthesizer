package com.melihhakanpektas.midisynthesizersample;

import static android.view.MotionEvent.ACTION_DOWN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.melihhakanpektas.midisynthesizer.midi.InvalidMidiDataException;
import com.melihhakanpektas.midisynthesizer.midi.MidiUnavailableException;
import com.melihhakanpektas.midisynthesizer.midi.ShortMessage;
import com.melihhakanpektas.midisynthesizer.sound.SF2Soundbank;

import com.melihhakanpektas.midisynthesizer.sound.SoftSynthesizer;

import com.melihhakanpektas.midisynthesizer.midi.Receiver;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Receiver recv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SF2Soundbank sf = new SF2Soundbank(getAssets().open("SmallTimGM6mb.sf2"));
            SoftSynthesizer synth = new SoftSynthesizer();
            synth.open();
            synth.loadAllInstruments(sf);
            synth.getChannels()[0].programChange(0);
            synth.getChannels()[1].programChange(1);
            recv = synth.getReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            throw new RuntimeException(e);
        }

    this.findViewById(R.id.piano).setOnTouchListener((v, event) -> {
        int action = event.getAction();
        if (action == ACTION_DOWN) {
            try {
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 0, 60, 127);
                if (recv == null) {

                } else {
                    recv.send(msg, -1);
                }
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
            v.performClick();
        }
        if (action == MotionEvent.ACTION_UP) {
            try {
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_OFF, 0, 60, 0);
                if (recv == null) {
                    throw new RuntimeException("recv is null");
                } else {
                    recv.send(msg, -1);
                }
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    });

    this.findViewById(R.id.woodblock).setOnTouchListener((v, event) -> {
        int action = event.getAction();
        if (action == ACTION_DOWN) {
            try {
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_ON, 1, 60, 127);
                if (recv == null) {
                    throw new RuntimeException("recv is null");
                } else {
                    recv.send(msg, -1);
                }
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
            v.performClick();
        }
        if (action == MotionEvent.ACTION_UP) {
            try {
                ShortMessage msg = new ShortMessage();
                msg.setMessage(ShortMessage.NOTE_OFF, 1, 60, 0);
                if (recv == null) {
                    throw new RuntimeException("recv is null");
                }else {
                    recv.send(msg, -1);
                }
            } catch (InvalidMidiDataException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    });
    }
}