import java.awt.*;
import java.util.*;

public class Rein_main{
  private Rein_actions act;
  private Rein_environment env;

  private int env_score;

  private Bug self;

  public Rein_main(Bug bug){
    this.self = bug;
    this.env = new Rein_environment();
    this.act = new Rein_actions(this.self);
  }

  //状態を認識、行動関数を呼び、移動方向を返す
  public Point getMove(){
    this.env_score = this.env.calculate_env(self);
    Point result = this.act.learn(this.env_score);
    return result;
  }

  public String getBit(){
    return this.env.getEnvBit();
  }

  public int getInteger(){
    return this.env.getResult();
  }

}
