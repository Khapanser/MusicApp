package main.java;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.util.ArrayList;


public class BBox {
    //Создаём ссылки на объекты
    JPanel mainPanel;
    ArrayList<JCheckBox> checkboxList;
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
        // TODO BorderLayout -> разбор
        BorderLayout layout = new BorderLayout();
        JPanel background = new JPanel(layout);
        background.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        checkboxList = new ArrayList<JCheckBox>();

        Box buttonBox = new Box(BoxLayout.Y_AXIS);

        /*
        Создаем кнопки, делаем их слушателями и добавляем в коробку
         */
        JButton start = new JButton("Start");
        /**--First version:*/
        //start.addActionListener(new MyStartListener());
        /** --version with lambda-function: */
        start.addActionListener(event -> buildTrackAndStart());
        buttonBox.add(start);
/** --lambda-f*/
        JButton stop = new JButton("Stop");
        stop.addActionListener(event -> sequencer.stop());
        buttonBox.add(stop);
/** --lambda-f*/
        JButton upTempo = new JButton("Tempo Up");
        upTempo.addActionListener(event -> {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*1.03));
        });
        buttonBox.add(upTempo);
/** --lambda-f*/
        JButton downTempo = new JButton("Tempo Down");
        downTempo.addActionListener(event -> {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*.97));
        });
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

        //TODO изучить использование grid
        GridLayout grid = new GridLayout(16,16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        background.add(BorderLayout.CENTER,mainPanel);

        for (int i = 0; i<256; i++)
        {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkboxList.add(c);
            mainPanel.add(c);
        }

        setUpMidi();
        theFrame.setBounds(50,50,300,300);
        theFrame.pack();
        theFrame.setVisible(true);
        theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    JCheckBox jc = (JCheckBox)checkboxList.get(j+(16*i));
                    if (jc.isSelected()){
                        trackList[j] = key;
                    }else {
                        trackList[j] = 0;
                    }
                }

                makeTracks(trackList);
                track.add(makeEvent(176,1,127,0,16));
            }
        track.add(makeEvent(192,9,1,0,15));
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        } catch(Exception e) {e.printStackTrace();}
    }
/** --block disabled due to the lambda-f using
    public class MyStartListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*1.03));
        }
    }

    public class MyDownTempoListener implements  ActionListener{
        public void actionPerformed(ActionEvent a){
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float)(tempoFactor*.97));
        }
    }*/

    public void makeTracks(int[] list){

        for(int i=0;i<16;i++){
            int key = list[i];

            if (key != 0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key,100,i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event = null;
        try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a,tick);
        }catch(Exception e) {e.printStackTrace();}
        return event;
    }

}

