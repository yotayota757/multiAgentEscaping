import java.awt.*;
import javax.swing.*;

public class Status extends JFrame{
  private StatusMain st;
  public Status(int width){
    setTitle("Status");
    setVisible(true);
    setBounds(width,0,100,200);
    st = new StatusMain();

    Container contentPane = getContentPane();
    contentPane.add(st);
    pack();
  }

  public void send(Creature creat){
    st.display(creat);
  }
}

class StatusMain extends JPanel implements MouseListener{
  private static int WIDTH = 300;
  private static int HEIGHT = 200;

  private Creature selected;

  public StatusMain(){
    setPreferredSize(new Dimension(this.WIDTH,this.HEIGHT));
    setMouseListener(this);
    repaint();
  }

  public void paintComponent(Graphics g){
      super.paintComponent(g);

      g.drawString("spawn:", 160, 40);
      g.drawString("hunger:", 160, 70);
      g.drawString("ate:", 160, 100);
      g.drawString("raised:", 160, 130);
      g.drawString("countsup:", 160, 160);
      //起動時重くなる原因　不明
      if(selected!=null)if(!selected.dead){
        g.drawString(""+selected.spawn, 230, 40);
        g.drawString(""+selected.hunger, 230, 70);
        g.drawString(""+selected.ate, 230, 100);
        g.drawString(""+selected.raised, 230, 130);
        g.drawString(""+selected.countsup, 230, 160);
      }else{
        selected = null;
      }
  }

  public void display(Creature creat){
    selected = creat;
    repaint();
  }
////////////////////////////////////////////////////////////////////////////
  //マウスイベント
  public void mouseClicked(MouseEvent e){
  }
  public void mouseExited(MouseEvent e){
  }
  public void mouseEntered(MouseEvent e){
  }
  public void mousePressed(MouseEvent e){
  }
  public void mouseReleased(MouseEvent e){
  }
}
