import java.awt.*;
import java.util.*;

public class Rein_environment{
  private int result;
  private String envBit;
  public Rein_environment(){
  }
//1・危険エリアに近い（敵がいそうな場所）
//2・目的に近い
//4・視界の中で一番近いのが敵
//8・視界の中で一番近いのが味方
//16・視界の中で一番近いのが目的

  //状態の判定
  public int calculate_env(Bug self){
    this.result = 0;
    this.envBit = "";
    if(self.danger_areas.isAreaNear(self)){
      this.envBit += "1";
      this.result += 1;
    }else this.envBit += "0";

    if(self.objects.isObjNear(self)){
      this.envBit += " 1";
      this.result += 2;
    }else this.envBit += " 0";

    Creature nearest = self.getNearestCrt();

    if(nearest instanceof Bloody){
      this.envBit += " 1";
      this.result += 4;
    }else this.envBit += " 0";

    if(nearest instanceof Bug){
      this.envBit += " 1";
      this.result += 8;
    }else this.envBit += " 0";

    if(nearest instanceof Plant){
      this.envBit += " 1";
      this.result += 16;
    }else this.envBit += " 0";

    return this.result;
  }

  public String getEnvBit(){
    return this.envBit;
  }

  public int getResult(){
    return this.result;
  }
}
