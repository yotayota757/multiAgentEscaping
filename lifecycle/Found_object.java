import java.awt.*;
import java.util.*;

public class Found_object{
  private int NEAR = 200;
  private LinkedList<Point> obj;

  public Found_object(){
    obj = new LinkedList<Point>();
  }

  //見つけたobjを管理
  public void addFoundobj(int x,int y){
    Iterator itr = obj.iterator();
    while(itr.hasNext()){
      Point nextObj = (Point)itr.next();
      if(x == nextObj.getX() && y == nextObj.getY()){
        return;
      }
    }
    obj.add(new Point(x,y));
  }

  //見つけたobjを取得
  public LinkedList getFoundObj(){
    return this.obj;
  }

  public void remove(int index){
    this.obj.remove(index);
  }
  public boolean isObjNear(Bug bug){
    Iterator itr = obj.iterator();
    while(itr.hasNext()){
      Point nextCrt = (Point)itr.next();
      if(getDistance(bug.getX(), bug.getY(), (int)nextCrt.getX(), (int)nextCrt.getY())
                                                                      < NEAR){
        return true;
      }
    }
    return false;
  }

  //二点間の距離を返す
  protected static int getDistance(int x, int y, int x2, int y2) {
    double distance = Math.sqrt((x2 - x) * (x2 - x) + (y2 - y) * (y2 - y));
    return (int) distance;
  }

  public Point getNearestObj(int selfX, int selfY){
    Iterator itr = obj.iterator();
    Point nearest = null;
    while(itr.hasNext()){
      Point next = (Point)itr.next();
      if(getDistance(selfX, selfY, (int)next.getX(), (int)next.getY()) < NEAR){
        nearest = next;
      }
    }
    return new Point((int)nearest.getX(), (int)nearest.getY());
  }

  public int getSize(){
    return this.obj.size();
  }
}
