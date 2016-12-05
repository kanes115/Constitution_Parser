package zad.one;

/**
 * Created by Kanes on 05.12.2016.
 */
public class Set {
    private int x, y;

    public Set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void decX(){x--;}
    public void decY(){y--;}

    public boolean isBetween(int z){
        return z>=x && z<=y;
    }
}
