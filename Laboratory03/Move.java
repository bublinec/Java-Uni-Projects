public class Move{
    // declare state
    private String name; 
    private String type; 
    private int power;

    // get state
    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getPower(){
        return Integer.toString(this.power);
    }

    // override default output
    public String toString(){
        return this.name + " (" + this.type +  "): " + Integer.toString(this.power);
    }

    // constructor
    public Move (String _name, String _type, int _power){
        name = _name;
        type = _type;
        power = _power;
    }
}