import java.awt.*;

public class Rein_main{
  private Rein_actions act;
  private Rein_envirnment env;

  public Rein_main(LinkedList all){
    this.env = new Rein_envirnment(all);
    this.act = new Rein_actions();
  }

  //状態を認識、行動関数を呼び、移動方向を返す
  public Point getMove(Creature crt){
    int env_score = this.env.calculate_env(crt);
    Point result = this.act.learn(env_score);
    return result;
  }
}
