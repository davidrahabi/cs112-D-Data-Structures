package warehouse;

public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        int a=StdIn.readInt();
        Warehouse w= new Warehouse();
        for(int i=0;i<a;i++){
            if(StdIn.readString().equals("add")){
                int day= StdIn.readInt();
                int id=StdIn.readInt();
                String name= StdIn.readString();
                int stock=StdIn.readInt();
                int demand=StdIn.readInt();

                w.addProduct(id, name, stock, day, demand);
            }
            else{
                int id=StdIn.readInt();
                int amount=StdIn.readInt();
                w.restockProduct(id,amount);
            }

        }
        StdOut.println(w);
    }
}
