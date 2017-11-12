import java.awt.*;
import java.util.Iterator;
import java.util.*;

public class Bug extends Creature{

    private MainPanel mp ;
    private int share_size = 60; //情報共有範囲
    private Rein_main rein; //学習データの取得クラス
    public Danger_area danger_areas; //自分が知っている危険エリアのクラス
    public Found_object objects;
    private LinkedList<Creature> crtInSight; //視界内のCreatureのリスト

    public Bug(int x ,int y, int randt, int direction, MainPanel mp){
        this.x = x;
        this.y = y;
        switch(direction){
            case 0: vx =1; vy = 0; break;
            case 1: vx =0; vy = 1; break;
            case 2: vx =-1; vy = 0; break;
            case 3: vx =0; vy = -1; break;
        }
        this.randt = randt;
        this.fullClr = Color.blue;
        this.dyingClr = Color.gray;
        this.clr = fullClr;
        this.full = 450;
        this.hunger = full;
        this.rdy_birth = 2;
        this.spawn = 1;
        this.sight_size = 100;
        this.mp = mp;

        rein = new Rein_main(mp.all);
        danger_areas = new Danger_area();
        crtInSight = new LinkedList<Creature>();
    }

    public void move(){
        super.move();
        super.changeDirRandt();
        this.flame++;

        Point nextMove = rein.getMove(this);

        x += nextMove.getX();
        y += nextMove.getY();
    }


    //視界内のCreatureを取得する
    public void getCrtInSight(){
      Iterator itr = mp.all.iterator();
      while(itr.hasNext()){
        Creature crt = (Creature)itr.next();
        Rectangle firstRect = new Rectangle(x+SIZE/2-sight_size/2,
                                            y+SIZE/2-sight_size/2,
                                            sight_size,sight_size);
        Rectangle creatureRect = new Rectangle(getX(),getY(),
                                        getSize(), getSize());
        if(firstRect.intersects(creatureRect)){
          //視界にいる
          if(crt instanceof Bloody){
            danger_areas.addArea(crt.getX(), crt.getY());
          }
          crtInSight.add(crt);
        }
      }
    }

    //視界内の一番近いCreatureを返す
    public Creature getNearestCrt(){
      Iterator itr = crtInSight.iterator();
      int nearestDis = this.sight_size;
      Creature nearestCrt = null;
      while(itr.hasNext()){
        Creature crt = (Creature)itr.next();
        int newDistance = Danger_area.getDistance(this.x,this.y,crt.x,crt.y);
        if(newDistance < nearestDis){
          nearestDis = newDistance;
          nearestCrt = crt;
        }
      }
      return nearestCrt;
    }

    public void draw(Graphics g){
        super.draw(g);
        g.fillOval(x,y,SIZE,SIZE);
        //視界
        g.drawOval(x+this.SIZE/2-this.sight_size/2,
                   y+this.SIZE/2-this.sight_size/2,
                   this.sight_size,this.sight_size);

        //情報共有範囲
        g.setColor(new Color(0,200,200));
        g.drawOval(x+this.SIZE/2-this.share_size/2,
                  y+this.SIZE/2-this.share_size/2,
                  this.share_size,this.share_size);
    }
}
