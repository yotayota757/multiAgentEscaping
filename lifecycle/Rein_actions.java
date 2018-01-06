import java.awt.*;
import java.util.*;

public class Rein_actions{

  //2^5
  private double[][] q = new double[32][5];
  //減衰率
  private final double ganma = 0.6;
  //学習率
  private final double a = 0.1;
  //即時報酬
  private int r = 0;
  //最終スコア
  private double score = 0;

  private Random rnd = new Random();

  private Bug self;

  //前フレームでの報酬判定
  public int prev_reward = 0;

  //Statusクラス告知用
  public int chosen_act = 0;

  public Rein_actions(Bug bug){
    this.self = bug;
    init();
  }

  private void init(){
    //それ以外の値をランダムに生成
    for(int i=0;i<32;i++){
      for(int j=0;j<5;j++){
        q[i][j] = rnd.nextFloat();
      }
    }
  }

  public int getChosenAct(){
    return this.chosen_act;
  }

  //状態値のQ値から最善の行動に沿った関数を呼び、移動方向
  public Point learn(int env){
    Point result = null;

    switch(getBestAct(q[env])){
      case 0: result = do_nothing();break;
      case 1: result = run_away(); break;
      case 2: result = move_to_ally(); break;
      case 3: result = move_to_object(); break;
      case 4: result = explore(); break;
    }

    return result;
  }

  //Q値から最善の行動を返す
  public int getBestAct(double actions[]){
    int bestAct = 0;
    for(int i=0;i<5;i++){
      //前の行動が選ばれやすい
      if(actions[bestAct] < actions[i]){
        bestAct = i;
      }
    }
    this.chosen_act = bestAct;
    return bestAct;
  }

  public void setReward(int x){
    this.prev_reward = x;
  }

  //即時報酬
  public int getReward(){
    //いらない？
    int reward = prev_reward;
    prev_reward = 0;
    return reward;
  }

////////////////////////////////////////////////////////////////
//行動群

  private int share_information(){
    //常時
    //視界の中の味方に自分の情報を与える（仮）
    //Creature それぞれが Danger_areaとobjectの位置を把握するマッピングが必要
    //情報を持った奴が教えるか、情報を持っていることを察知してもらうかはまだ決めていない
    return 0;
  }

  private Point do_nothing(){
    //その場に止まる
    this.setReward(-1);
    self.exploring = false;
    return new Point(0,0);
  }

  private Point run_away(){
    //孤立する形で逃げる
    //なんでもいいから視界内のCreatureから逃げる
    Point movement;
    Creature any = self.getNearestCrt();
    if(any == null){
      //nothing is in sight
      movement = null;
      this.setReward(-1);
    }else{
      //それに向けた移動方向を取得
      movement = self.getMovementTo(any);
      //-1倍する
      movement = new Point((int)movement.getX()*-1,(int)movement.getY()*-1);
      this.setReward(-1);
    }
    self.exploring = false;
    return movement;
  }

  private Point move_to_ally(){
    //視界内の一番近くの味方を取得
    Point movement;
    Creature ally = self.getNearestAlly();
    if(ally == null){
      //no ally founded
      movement = null;
      this.setReward(-5);
    }else{
      //それに向けた移動方向を取得
      movement = self.getMovementTo(ally);
      this.setReward(-1);
    }
    self.exploring = false;
    return movement;
  }

  private Point move_to_object(){
    //目的を食いに行く
    Point movement;
    Creature obj = self.getNearestObj();
    if(obj == null){
      //no obj is founded
      movement = null;
      this.setReward(-5);
    }else{
      //それに向けた移動方向を取得
      movement = self.getMovementTo(obj);
      this.setReward(-1);
    }
    self.exploring = false;
    return movement;
  }

  private Point explore(){
    //ランダムに移動する、偵察するという意味で
    if(self.exploring == false ||
        self.getDistance(self.x,self.y,self.explore_x,self.explore_y<10))
          setExplorePoint();
    self.exploring = true;
    Point movement =
      self.getMovementTo(self.explore_x,self.explore_y);
    this.setReward(-1);
    return movement;
  }

  private void setExplorePoint(){
    self.explore_x = rnd.nextInt(MainPanel.WIDTH);
    self.explore_y = rnd.nextInt(MainPanel.HEIGHT);
  }

}
