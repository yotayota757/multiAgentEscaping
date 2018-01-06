import java.awt.*;
import java.util.Iterator;
import java.util.*;

public class Bug extends Creature{

    private MainPanel mp ;
    private int share_size = 60; //情報共有範囲

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

        this.rein = new Rein_main(this);
        this.objects = new Found_object();
        this.danger_areas = new Danger_area();
        this.crtInSight = new LinkedList<Creature>();
    }

    public void move(){
      super.move();
      //super.changeDirRandt(); // no need to call ?
      this.init();
      this.flame++;
      this.getCrtInSight();

      Point nextMove = rein.getMove();
      if(nextMove == null){
        x += 0;
        y += 0;
      }
      else{
        x += nextMove.getX();
        y += nextMove.getY();
      }
    }

    private void init(){
      //視界内のリストを初期化
      this.crtInSight.clear();
      //危険エリアの更新
      this.danger_areas.update();
    }

    //自分を除く、視界内のCreatureを取得する
    public void getCrtInSight(){
      Iterator itr = mp.all.iterator();
      //自分の視界の範囲
      Rectangle firstRect = new Rectangle(x+this.SIZE/2-sight_size/2,
                                          y+this.SIZE/2-sight_size/2,
                                          sight_size,sight_size);
      while(itr.hasNext()){
        Creature crt = (Creature)itr.next();
        Rectangle creatureRect = new Rectangle(crt.getX(),crt.getY(),
                                        crt.getSize(), crt.getSize());
        if(firstRect.intersects(creatureRect)){
          //視界にいる
          //己、問題あり
          if(crt.x == x && crt.y == y){
            continue;
          }
          else if(crt instanceof Bloody){
            danger_areas.addArea(crt.getX(), crt.getY());
          }
          else if(crt instanceof Plant){
            this.objects.addFoundobj(crt.getX(), crt.getY());
          }
          crtInSight.add(crt);
        }
      }
    }

    public int howManyCrtInSight(){
      return this.crtInSight.size();
    }

    protected static int getDistance(int x, int y, int x2, int y2) {
      double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
      return (int) distance;
    }

    public Creature getNearestAlly(){
      Iterator itr = crtInSight.iterator();
      int nearestDis = this.sight_size;
      Creature nearestCrt = null;
      while(itr.hasNext()){
        Creature crt = (Creature)itr.next();
        if(crt instanceof Bug){
          int newDistance = Danger_area.getDistance(this.x,this.y,crt.x,crt.y);
          if(newDistance < nearestDis){
            nearestDis = newDistance;
            nearestCrt = crt;
          }
        }
      }
      return nearestCrt;
    }

    public Creature getNearestObj(){
      Iterator itr = crtInSight.iterator();
      int nearestDis = this.sight_size;
      Creature nearestCrt = null;
      while(itr.hasNext()){
        Creature crt = (Creature)itr.next();
        if(crt instanceof Plant){
          int newDistance = Danger_area.getDistance(this.x,this.y,crt.x,crt.y);
          if(newDistance < nearestDis){
            nearestDis = newDistance;
            nearestCrt = crt;
          }
        }
      }
      return nearestCrt;
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
