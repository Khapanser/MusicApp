package main.java;


import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniPlayer {

    static JFrame f;
    static MyPanel control;

    public void go(){
        f = new JFrame("Music plus Graphics");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400);
        f.getContentPane().add(control);
        f.setVisible(true);
    }

    public static void main(String[] args)
            go()
    {
        try{

            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();

            int[] eventsIWant = {127};
            control = new MyPanel();
            sequencer.addControllerEventListener(control,eventsIWant);


            Sequence seq = new Sequence(Sequence.PPQ,4);
            Track track = seq.createTrack();
        for (int i = 5; i<61; i+=4)
        {
            track.add(makeEvent(144,1,i,100,i));
            track.add(makeEvent(176,1,127,0,i));
            track.add(makeEvent(128,1,i,100,i+2));
        }
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(220);
        sequencer.start();
        } catch (Exception ex){ex.printStackTrace();}
    }

    public static MidiEvent makeEvent (int comd, int chan, int one, int two, int tick)
    {
        MidiEvent event = null;
                try{
            ShortMessage a = new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event = new MidiEvent(a,tick);
                } catch (Exception e) {}
                return event;
    }

    /*
    static class  Control implements ControllerEventListener {
        public void controlChange(ShortMessage event)
        {
            System.out.println(" LA ");
        }
    }
    */

    static class MyPanel extends JPanel implements ControllerEventListener{
        public void controlChange(ShortMessage event)
        {
            System.out.println(" LA ");
            repaint();
        }

        public void PaintComponent(Graphics g)
        {

        }

    }

}
