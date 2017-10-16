import java.awt.*;

public abstract class Creature{
    public int spawn;
    public static final int SIZE = 10;
    //public Point storage ;
    public int x,y;//getとかの関数を使って参照
    public int vx,vy;//速度
    public int hunger;//
    public int ate = 0;
    public int rdy_birth;
    public int raised = 0; //plant用 改善の余地あり
    public int randt = 0;
    public int countsup=0; //方向転換のためのカウント
    public Color clr;
    public Color fullClr;
    public Color dyingClr;
    public int full;
    public boolean dead = false;
    public int flame; //生存フレーム数
    public int sight_size;

    public void change_direction(){
        int direction = (int)(Math.random()*4);
        switch(direction){
            case 0: vx = 1; vy = 0; break;
            case 1: vx = 0; vy = 1; break;
            case 2: vx = -1; vy = 0; break;
            case 3: vx = 0; vy = -1; break;
        }
    }
    public void collideWall(){
        if(x<0 && x>-20){ vx = -vx; x=0;}
        if(x > MainPanel.WIDTH-SIZE){
            vx = -vx;
            x = MainPanel.WIDTH-SIZE;
        }
        if(y<0 && y>-20){ vy = -vy; y=0;}
        if(y>MainPanel.HEIGHT-SIZE){
            vy = -vy;
            y = MainPanel.HEIGHT-SIZE;
        }
    }

    public boolean isCollision(Creature creature){
        Rectangle firstRect = new Rectangle(x,y,SIZE,SIZE);
        Rectangle creatureRect = new Rectangle(creature.getX(),creature.getY(), creature.getSize(), creature.getSize());
        if(firstRect.intersects(creatureRect)){
            return true;
        }
        return false;
    }


    public void move(){
      //hunger--;
      countsup++;
      collideWall();
    }

    public boolean isDead(){
        if(hunger<=0){
          this.dead = true;
          return true;
        }
        else return false;
    }

    public boolean isDying(){
        if(hunger < full*1/3){
            this.clr = dyingClr;
            return true;
        }
        else return false;
    }

    public void changeDirRandt(){
        if(countsup == randt){
            change_direction();
            countsup = 0;
            randt = (int)(Math.random()*100)+100+1;
        }
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getSize(){
        return this.SIZE;
    }

    public void draw(Graphics g){
        g.setColor(clr);
    }
}
