import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Status extends JFrame{
  private StatusMain st;
  public Status(int width, MainPanel main){
    setTitle("Controller & Status");
    setVisible(true);
    setBounds(width,0,100,200);
    st = new StatusMain(main);

    Container contentPane = getContentPane();
    contentPane.add(st);
    pack();
  }

  public void send(Creature creat){
    st.display(creat);
  }
}

class StatusMain extends JPanel implements ActionListener{
  private static int WIDTH = 300;
  private static int HEIGHT = 200;

  //controller
  private MainPanel main;
  private JButton pause = new JButton("pause");
  private JButton info = new JButton("info");


  private Creature selected;

  public StatusMain(MainPanel main){
    this.main = main;
    setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
    setLayout(null);
    buttonInit();
    repaint();
  }

  private void buttonInit(){
    pause.addActionListener(this);
    info.addActionListener(this);
    pause.setFocusPainted(false);
    info.setFocusPainted(false);
    pause.setBounds(60,150,80,30);
    info.setBounds(160,150,80,30);
    add(pause);
    add(info);

  }

  public void actionPerformed(ActionEvent e){
    if(e.getSource() == pause){
      if(main.paused == true) main.paused = false;
      else main.paused = true;
    }
    else if(e.getSource() == info){
      if(main.display_info == true) main.display_info = false;
      else main.display_info = true;
    }
  }

  public void paintComponent(Graphics g){
      super.paintComponent(g);
      //Found_object数, rein_env のbit

      if(selected instanceof Bug){
        g.drawString("Reinforce Environment",100,40);
        g.drawString("1|2|4|8|16",110,60);
        g.drawString(Integer.toBinaryString(selected.rein.getResult())+
                                    " = "+selected.rein.getResult(),110,70);

        g.drawString("Found Object :", 100, 100);
        g.drawString(""+selected.objects.getSize(), 100, 110);
      }
      else{
        g.drawString("spawn:", 100, 40);
        g.drawString("hunger:", 100, 70);
        g.drawString("ate:", 100, 100);
        g.drawString("raised:", 100, 130);
        //起動時重くなる原因　不明
        if(selected!=null)if(!selected.dead){
          g.drawString(""+selected.spawn, 200, 40);
          g.drawString(""+selected.hunger, 200, 70);
          g.drawString(""+selected.ate, 200, 100);
          g.drawString(""+selected.raised, 200, 130);
          g.drawString(""+selected.countsup, 200, 160);
        }else{
          selected = null;
        }
      }


      g.drawString("spawn:", 100, 40);
      g.drawString("hunger:", 100, 70);
      g.drawString("ate:", 100, 100);
      g.drawString("raised:", 100, 130);
      //起動時重くなる原因　不明
      if(selected!=null)if(!selected.dead){
        g.drawString(""+selected.spawn, 200, 40);
        g.drawString(""+selected.hunger, 200, 70);
        g.drawString(""+selected.ate, 200, 100);
        g.drawString(""+selected.raised, 200, 130);
        g.drawString(""+selected.countsup, 200, 160);
      }else{
        selected = null;
      }
  }

  public void display(Creature crt){
    this.selected = crt;
    repaint();
  }
}
