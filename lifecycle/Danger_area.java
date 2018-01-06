import java.awt.*;
import java.util.*;

public class Danger_area{
  private final int NEAR = 200; //”近い”と認識する範囲
  private LinkedList<Area> area; //危険エリアを管理しているリスト

  //creature の isCollisionを使う
  public Danger_area(){
    area = new LinkedList<Area>();
  }

  public void update(){
    Iterator itr = area.iterator();
    while(itr.hasNext()){
      Area nextArea = (Area)itr.next();
      nextArea.duration--;
    }
    //最初を見て消す
    if(area.size() > 0){
      Area temp = area.getFirst();
      if(temp == null) return;
      else if(temp.duration <= 0) area.pop();
    }
  }

  //エリアの追加
  public void addArea(int x,int y){
    area.add(new Area(x,y));
  }

  //エリアの削除
  public void remove(int index){
    this.area.remove(index);
  }

  //エリアの取得
  public LinkedList<Area> get_area(){
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
}
