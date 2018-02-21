package main.java;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static main.java.MiniPlayer.makeEvent;

public class BBox {
    //Создаём ссылки на объекты
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxlist;
    Sequencer sequencer;
    Sequence sequence;
    Track track;
    JFrame theFrame;
    //массив названий инструментов:
    String[] instrumentNames = {"1", "2","3","4","5","6","7","8","9","10","11", "12","13","14","15","16",};
    //наименования инструментов в базе:
    int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};

    public static void main (String[] args)
    {
        new BBox().buildGUI();
    }

    public void buildGUI()
    {
        theFrame = new JFrame("BBox");
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // TODO разобрать следующие три строки
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkboxlist = new ArrayList<JCheckBox>();

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        /*
        Создаем кнопки, делаем их слушателями и добавляем в коробку
         */
        JButton start = new JButton("Start");
        start.addActionListener(new MyStartListener);
        buttonBox.add(start);

        JButton stop = new JButton("Stop");
        stop.addActionListener(new MyStopListener);
        buttonBox.add(stop);

        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(new MyStopListener);
        buttonBox.add(upTempo);

        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(new MyStopListener);
        buttonBox.add(downTempo);

        /*
        Добавляем названия инструментов
         */
        Box nameBox = new Box(BoxLayout.Y_AXIS);
        for (int i = 0; i<16; i++)
        {
            nameBox.add(new Label(instrumentNames[i]));
        }

        background.add(BorderLayout.EAST,buttonBox);
        background.add(BorderLayout.WEST,nameBox);

        theFrame.getContentPane().add(background);

        //TODO изучить
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);

        for (int i = 0; i<256; i++)
        {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxlist.add(c);
            mainPanel.add(c);
        }

        setUpMidi();
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
    }


    public void setUpMidi(){
        try{
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequence = new Sequence(Sequence.PPQ,4);
            track = sequence.createTrack();
            sequencer.setTempoInBPM(120);
        } catch (Exception e) {e.printStackTrace();}
    }

    public void buildTrackAndStart() {
        //массив на 16 элементов, чтобы хранить значения для каждого инструмента на 16 тактов
        int[] trackList = null;
        //удаляем и создаём дорожку заново
        sequence.deleteTrack(track);
        track = sequence.createTrack();

            for(int i = 0;i<16; i++)
            {
                trackList = new int[16];
                int key = instruments[i];

                for (int j = 0; j < 16; j++)
                {
                    JCheckBox jc = (JCheckBox) checkboxList.get(j+(16*i));
                    if (jc.isSelected()){
                        trackList[j] = key;
                    }else {
                        trackList[j] = 0;
                    }
                }

                makeTracks(trackList);
                track.add(makeEvent(176,1,127,0,16));
            }





    }


}
