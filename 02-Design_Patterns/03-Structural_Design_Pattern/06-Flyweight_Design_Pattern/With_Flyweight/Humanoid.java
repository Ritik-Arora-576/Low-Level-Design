package With_Flyweight;

public class Humanoid implements Robot{
    // mentioning intrinsic properties here
    // we have to make these feilds immutable
    private final String type;
    private final Sprites body;

    public Humanoid(String type, Sprites body){
        this.type = type;
        this.body = body;
    }

    public void display(int row, int col){
        System.out.println("Robot "+type+" is placed at "+row+","+col);
    }
}
