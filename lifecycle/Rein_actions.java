import java.awt.*;

public class Rein_actions{

  private int test = 1000;

  //2^5
  private double[][][] q = new double[32][4];;
  //減衰率
  private final double ganma = 0.6;
  //学習率
  private final double a = 0.1;
  //即時報酬
  private int r = 0;
  //最終スコア
  private double score = 0;

  public Rein_actions(){
    init();
  }

  private init(){
    //それ以外の値をランダムに生成
    for(int i=0;i<32;i++){
      for(int j=0;j<4;j++){
        q[i][j][k] = rnd.nextFloat();
      }
    }
  }

  //状態値のQ値から最善の行動に沿った関数を呼び、移動方向
  public Point learn(int env){
    Point result;

    switch(getBestAct(q[env])){
      case 0: result = search_enemies();break;
      case 1: result = move_to_ally(); break;
      case 2: result = move_to_object(); break;
      case 3: result = run_away(); break;
      case 4: result = explore(); break;
    }

    return result;
  }

  //Q値から最善の行動を返す
  public int getBestAct(double actions[]){
    int bestAct = 0;
    for(int i=0;i<4;i++){
      if(actions[bestAct] < actions[i]){
        bestAct = i;
      }
    }
    return bestAct;
  }

  //即時報酬
  public int reward(){

    return 0;
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

  private Point search_enemies(){
    //危険エリアに行く or なかったらexplore or なかったら
    return 0;
  }

  private Point move_to_ally(){
    //一番近くの味方を取得
    Creature ally = getNearestAlly();
    if(!ally){
      //no ally founded

    }else{
      //それに向けた移動方向を取得
      Point movement = getMovementTo(ally);
    }
    return movement;
  }

  private Point run_away(){
    //孤立する形で逃げる
    //なんでもいいから視界内のCreatureから逃げる
    Creature any = getNearestCrt();
    if(!any){
      //nothing is in sight

    }else{
      //それに向けた移動方向を取得
      Point movement = getMovementTo(any);
      //-1倍する
      movement = new Point(movement.getX()*-1,movement.getY()*-1);
    }
    return movement;
  }

  private Point move_to_object(){
    //目的を食いに行く
    //なんでもいいから視界内のCreatureから逃げる
    Creature obj = getNearestObj();
    if(!obj){
      //no obj is founded

    }else{
      //それに向けた移動方向を取得
      Point movement = getMovementTo(obj);
    }
    return movement;
  }

  private Point explore(){
    //ランダムに移動する、偵察するという意味で
    Point movement = getMovementTo(x,y);
    return movement;
  }

}
