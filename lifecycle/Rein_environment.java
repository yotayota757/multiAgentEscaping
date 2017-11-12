import java.awt.*;
import java.util.*;

public class Rein_environment{
  public Rein_environment(){
  }
//1・危険エリアに近い（敵がいそうな場所）
//2・目的に近い
//4・視界の中で一番近いのが敵
//8・視界の中で一番近いのが味方
//16・視界の中で一番近いのが目的

  //状態の判定
  public int calculate_env(Bug bug){
    int result = 0;
    if(bug.danger_areas.isAreaNear(bug)) result += 1;
    if(bug.objects.isObjNear(bug)) result += 2;
    if(bug.getNearestCrt() instanceof Bloody){
      result += 4;
    }else if(bug.getNearestCrt() instanceof Bug){
      result += 8;
    }else if(bug.getNearestCrt() instanceof Plant){
      result += 16;
    }
    return result;
  }

}
