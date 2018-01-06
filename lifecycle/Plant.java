import java.awt.*;

public class Plant extends Creature{
    public Plant(int x,int y){
        this.x = x;
        this.y = y;
        this.spawn = 1;
        this.rdy_birth = 300;
        this.clr = Color.green;
        this.sight_size = 0;
    }
    public void move(){
      super.move();
      this.countsup++;
    }
    public boolean isDead(){return false;}
    public boolean isDying(){return false;}

    public void draw(Graphics g){
      super.draw(g);
      g.fillPolygon(new int[] {x+SIZE/2,x+SIZE,x},
                      new int[] {y,y+SIZE,y+SIZE},3);
    }
}
