public class Monster {
    // declare state
    private String name; 
    private String[] types = new String[2];
    private Move[] moves = new Move[4];

    // set state
    public void setMove (int index, Move move){
        moves[index] = move;
    }

    // get state
    public String getName(){
        return this.name;
    }
    public Boolean hasType(String type) {
        return Arrays.asList(this.types).contains(type);
    }
    public Move getMove (int index) {
        if(this.moves[index] != null){
            return this.moves[index];
        } else{
            return null;
        }
    }

    // override default output
    public String toString(){
        return this.name + " " + Arrays.toString(this.types);
    }

    // constructors
    public Monster (String _name, String _type){
        name = _name;
        types[0] = _type;
    }

    public Monster (String _name, String _type1, String _type2){
        name = _name;
        types = new String[]{_type1, _type2};
    }
}