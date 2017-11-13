import java.awt.*;
import javax.swing.*;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.ListIterator;
import java.awt.event.*;

public class main extends JFrame {
    public main() {
        // タイトル決定
        setTitle("Life Cycle");
        // ウィンドウの可変変更
        setResizable(true);
        // MainPanelをここで呼び出し
        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);
        // パネルをフレームにはめる
        pack();
    }

    public static void main(String[] args) {
        main frame = new main();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MainPanel extends JPanel implements Runnable,MouseListener,
                                          KeyListener{
    public static final int WIDTH = 450;
    public static final int HEIGHT = 450;

    public static final int PLANT_NUM = 4;
    public static final int BUG_NUM = 2;
    public static final int BLOODY_NUM = 1;


    public static Bug bug;
    public static Bloody bloody;
    public static Plant plant;

    public Status st;
    public Creature selected = null;
    public boolean paused = true;
    public boolean display_info = false;

    private int counter = 0;

    public LinkedList<Creature> all;
    public LinkedList<Plant> plants;
    public LinkedList<Bug> bugs;
    public LinkedList<Bloody> bloodies;

    private Thread gameLoop;

    private int alive_duration = 0;
    private int alive_num = 0;

    public MainPanel(){
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        init();
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    //初期化
    public void init(){
        all = new LinkedList<Creature>();
        bugs = new LinkedList<Bug>();
        bloodies = new LinkedList<Bloody>();
        plants = new LinkedList<Plant>();
        st = new Status(WIDTH,this);

        for(int i=0;i<PLANT_NUM;i++){
            int rnd_x = (int)(Math.random()*WIDTH);
            int rnd_y = (int)(Math.random()*HEIGHT);
            plant = new Plant(rnd_x,rnd_y);
            all.add(plant);
            plants.add(plant);
        }
        for(int i=0;i<BUG_NUM;i++){
            int rnd_x = (int)(Math.random()*WIDTH);
            int rnd_y = (int)(Math.random()*HEIGHT);
            int rnd_t = (int)(Math.random()*100)+100+1;
            int rnd_d = (int)(Math.random()*4);
            bug = new Bug(rnd_x,rnd_y,rnd_t,rnd_d,this);
            all.add(bug);
            bugs.add(bug);
        }
        for(int i=0;i<BLOODY_NUM;i++){
            int rnd_x = (int)(Math.random()*WIDTH);
            int rnd_y = (int)(Math.random()*HEIGHT);
            int rnd_t = (int)(Math.random()*100)+100+1;
            int rnd_d = (int)(Math.random()*4);
            bloody = new Bloody(rnd_x,rnd_y,rnd_t,rnd_d,this);
            all.add(bloody);
            bloodies.add(bloody);
        }
    }

    //描画
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Iterator itera = all.iterator();
        while(itera.hasNext()){
            Creature creat = (Creature)itera.next();
            creat.draw(g);
        }
    }
//////////////////////////////////////////////////////////
//Loop thread

    public void run(){
      while(true){
        if(alive_num!=0){
          int alive = alive_duration/alive_num;
          System.out.println(alive);
        }
        //フレームカウンター
        counter++;
        //オブジェクト管理
        ListIterator<Creature> itera = all.listIterator();
        while(itera.hasNext() && !paused){
          Creature creat = (Creature)itera.next();

          //死んでいる時
          if(creat.isDead()){
            itera.remove();
            if(creat instanceof Bug){
              alive_duration += creat.flame;
              alive_num += 1;
              bugs.remove(creat);
              //prdcPlant(creat,itera);
            }else if(creat instanceof Bloody){
              bloodies.remove(creat);
              //prdcPlant(creat,itera);
            }
          }else{
            //死んでない時動く
            creat.move();
          }


          //緑が青に食べられた時の処理
          if(creat instanceof Plant){
            Creature eater = isEatenBy(creat,bugs);
            if(eater!=null){
              itera.remove();
              plants.remove(creat);
              if(eater.ate>=eater.rdy_birth){
                eater.ate = 0;
                int rnd_t = (int)(Math.random()*100)+100+1;
                int rnd_d = (int)(Math.random()*4);
                bug = new Bug(eater.x,eater.y,rnd_t,rnd_d,this);
                itera.add(bug);
                bugs.add(bug);
              }
            }
            if(creat.countsup>=creat.rdy_birth && creat.raised < 1){
              creat.countsup = 0;
              //prdcPlant(creat,itera);
              creat.raised++;
            }
          }
          //青が赤に食べられた時の処理
          else if(creat instanceof Bug){
            Creature eater = isEatenBy(creat,bloodies);
            if(eater!=null){
              itera.remove();
              bugs.remove(creat);
              if(eater.ate>=eater.rdy_birth){
                eater.ate = 0;
                int rnd_t = (int)(Math.random()*100)+100+1;
                int rnd_d = (int)(Math.random()*4);
                bloody = new Bloody(eater.x,eater.y,rnd_t,rnd_d,this);
                itera.add(bloody);
                bloodies.add(bloody);
              }
            }
          }
        }
        //ステータスの更新
        if(selected != null){
          st.send(selected);
        }
        repaint();
        try{
          Thread.sleep(20);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }

    public Creature isEatenBy (Creature eaten , LinkedList eater){
      Iterator ite = eater.iterator();
      while(ite.hasNext()){
        Creature sec = (Creature)ite.next();
        if(eaten.isCollision(sec)){
          sec.hunger = sec.full;
          sec.clr = sec.fullClr;
          sec.ate++;
          eaten.dead = true;
          return sec;
        }
      }
      return null;
    }
///////////////////////////////////////////////////////////////////
//マウスイベント
    @Override
    public void mouseClicked(MouseEvent e){
      int x = e.getX();
      int y = e.getY();
      boolean founded = false;
      ListIterator itera = all.listIterator();
      while(itera.hasNext() && !founded){
        Creature creat = (Creature)itera.next();
        if(x>=creat.x && x<=creat.x+creat.SIZE){
          if(y>=creat.y && y<=creat.y+creat.SIZE){
            selected = creat;
            founded = true;
          }
        }
      }
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }
///////////////////////////////////////////////////////////////////
//キーイベント
    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_SPACE){
        if(!this.paused){
          this.paused = true;
        }else{
          this.paused = false;
        }
      }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
