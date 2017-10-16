import java.awt.*;

public class Rein_environment{

  private Found_object objects;
  private Danger_area danger;

  private LinkedList all ;

  public Rein_envirnment(LinkedList all){
    this.all = all;
  }

//1・危険エリアに近い（敵がいそうな場所）
//2・目的に近い
//4・視界の中で一番近いのが敵
//8・視界の中で一番近いのが味方
//16・視界の中で一番近いのが目的

  //状態の判定
  public int calculate_env(Crature crt){
    int result = 0;
    if(crt.danger_areas.isAreaNear()) result += 1;
    if(objects.isObjNear(crt.getX(),crt.getY())) result += 2;
    if(crt.getNearestCrt() instanceof Bloody){
      result += 4;
    }else if(crt.getNearestCrt() instanceof Bug){
      result += 8;
    }else if(crt.getNearestCrt() instanceof Plant){
      result += 16;
    }
    return result;
  }

}
