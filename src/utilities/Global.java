package utilities;

public class Global {
    public static final double ORIGINAL_TILESIZE = 32;
    public static final double SCALE = 3;
    public static final int TILESIZE = (int)(ORIGINAL_TILESIZE*SCALE);

    //variáveis pra facilitar a orientação (ordem dos spritesheets)
    public static final int DOWN = 0;
    public static final int RIGHT_DOWN = 1;
    public static final int RIGHT = 2;
    public static final int RIGHT_UP = 3;
    public static final int UP = 4;
    public static final int LEFT_UP = 5;
    public static final int LEFT = 6;
    public static final int LEFT_DOWN = 7;
}
