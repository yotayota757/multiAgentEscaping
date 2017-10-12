import java.awt.*;
import java.util.*;

public class Danger_area{
  private final int NEAR = 200;　//”近い”と認識する範囲
  private LinkedList area; //危険エリアを管理しているリスト

  //creature の isCollisionを使う
  public Danger_area(){
    area = new LinkedList()<Area>;
  }

  //エリアの追加
  public void addArea(x,y){
    area.add(new Area(x,y))
  }

  //エリアの取得
  public LinkedList get_area(){
    return this.area;
  }

  //いずれかのエリアが近いかの判定
  public boolean isAreaNear(Creature crt){
    Iterator itr = area.iterator();
    while(itr.hasNext()){
      Creature nextCrt = (Creature)itr.next();
      if(getDistance(crt.getX(), crt.getY(), nextCrt.getX(), nextCrt.getY())
                                                          < NEAR+Area.raidus){
        return true;
      }
    }
    return false;
  }

  @static
  protected int getDistance(int x, int y, int x2, int y2) {
    double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    return (int) distance;
  }

  //危険エリアのクラス
  private class Area{
    public int x;
    public int y;
    public int radius = 200;
    public Color clr = new Color(RED);
    public int duration = 300;

    public Area(x,y){
      this.x = x;
      this.y = y;
    }
  }
}
