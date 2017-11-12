import java.awt.*;
import java.util.*;

public class Danger_area{
  private final int NEAR = 200; //”近い”と認識する範囲
  private LinkedList<Area> area; //危険エリアを管理しているリスト

  //creature の isCollisionを使う
  public Danger_area(){
    area = new LinkedList<Area>();
  }

  //エリアの追加
  public void addArea(int x,int y){
    area.add(new Area(x,y));
  }

  //エリアの取得
  public LinkedList get_area(){
    return this.area;
  }

  //いずれかのエリアが近いかの判定
  public boolean isAreaNear(Bug self){
    Iterator itr = area.iterator();
    while(itr.hasNext()){
      Area nextArea = (Area)itr.next();
      if(getDistance(self.getX(), self.getY(), nextArea.x, nextArea.y)
                                                      < NEAR+nextArea.radius){
        return true;
      }
    }
    return false;
  }

  protected static int getDistance(int x, int y, int x2, int y2) {
    double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    return (int) distance;
  }

  //危険エリアのクラス
  private class Area{
    public int x;
    public int y;
    public int radius = 200;
    public Color clr = new Color(255,0,0);
    public int duration = 300;

    public Area(int x,int y){
      this.x = x;
      this.y = y;
    }
  }
}
