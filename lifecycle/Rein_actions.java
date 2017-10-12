import java.awt.*;

public class Rein_actions{

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
    //一番近くの味方に近く
    return 0;
  }

  private Point run_away(){
    //孤立する形で逃げる
    return 0;
  }

  private Point move_to_object(){
    //目的を食いに行く
    return 0;
  }

  private Point explore(){
    //ランダムに移動する、偵察するという意味で
    return 0;
  }

}
