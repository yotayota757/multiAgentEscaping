import java.awt.*;
import java.util.Iterator;

public class Bloody extends Creature{
    private int target_x;
    private int target_y;
    private MainPanel context;
    public Bloody(int x ,int y, int randt, int direction, MainPanel context){
        this.context = context;
        this.x = x;
        this.y = y;
        switch(direction){
            case 0: vx =1; vy = 0; break;
            case 1: vx =0; vy = 1; break;
            case 2: vx =-1; vy = 0; break;
            case 3: vx =0; vy = -1; break;
        }
        this.randt = randt;
        this.fullClr = Color.red;
        this.dyingClr = Color.pink;
        this.clr = fullClr;
        this.full = 600;
        this.hunger = full;
        this.spawn = 8;
        this.rdy_birth = 3;
        this.sight_size = 90;
    }

    public void move(){
        super.move();
        changeDirRandt();

        x += vx;
        y += vy;
    }

    private void trace(){
        int temp_vx = vx;
        int temp_vy = vy;
        target_x = -1;
        target_y = -1;
        int nearest = Integer.MAX_VALUE;
        Iterator ite = context.bugs.iterator();

        while(ite.hasNext()){
            Creature creat = (Creature)ite.next();
            int distance = (int)(Math.sqrt((creat.getX()-this.x)*(creat.getX()-this.x)+
                                           (creat.getY()-this.y)*(creat.getY()-this.y)));
            if(nearest>distance && distance < 100){
                target_x = creat.getX();
                target_y = creat.getY();
                double angle = Math.atan2(this.y-target_y,this.x-target_x);
                temp_vx = -(int)(2*Math.cos(angle));
                if(temp_vx>1) temp_vx = 1;
                temp_vy = -(int)(2*Math.sin(angle));
                if(temp_vy>1) temp_vy = 1;
                nearest = distance;
            }
        }
        x += temp_vx;
        y += temp_vy;
    }

    public void draw(Graphics g){
      super.draw(g);
      g.fillRect(x,y,SIZE,SIZE);
      g.drawOval(x+this.SIZE/2-this.sight_size/2,
                 y+this.SIZE/2-this.sight_size/2,
                 this.sight_size,this.sight_size);
    }
}
