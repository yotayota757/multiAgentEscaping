import java.awt.*;
import java.util.*;

public class Found_object{
  private int NEAR = 200;
  private LinkedList obj;

  public Found_object(){
    obj = new LinkedList();
  }

  //見つけたobjを管理
  public addFoundobj(x,y){
    Point location = new Point(x,y);
    obj.add(location);
  }

  //見つけたobjを取得
  public getFoundObj(){
    return this.obj;
  }

  public isObjNear(Creature crt){
    Iterator itr = obj.iterator();
    while(itr.hasNext()){
      Creature nextCrt = (Creature)itr.next();
      if(getDistance(crt.getX(), crt.getY(), nextCrt.getX(), nextCrt.getY())
                                                                      < NEAR){
        return true;
      }
    }
    return false;
  }

  //二点間の距離を返す
  @static
  protected int getDistance(int x, int y, int x2, int y2) {
    double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    return (int) distance;
  }

  public Point getNearestObj(selfX, selfY){
    Iterator itr = obj.iterator();
    Iterator nearest = false;
    while(itr.hasNext()){
      Point next = itr.next();
      if(getDistance(selfX, selfY, next.getX(), next.getY()) < NEAR){
        nearest = itr;
      }
    }
    return new Point(nearest.getX(), nearest.getY());
  }
}
