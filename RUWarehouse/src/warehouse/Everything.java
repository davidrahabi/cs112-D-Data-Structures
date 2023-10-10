package warehouse;

/*
 * Use this class to put it all together.
 */ 
public class Everything {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse w=new Warehouse();
        int a=StdIn.readInt();
        for(int i=0; i<a;i++){
            String s=StdIn.readString();
            if(s.equals("add")){
                int day= StdIn.readInt();
                int id=StdIn.readInt();
                String name= StdIn.readString();
                int stock=StdIn.readInt();
                int demand=StdIn.readInt();
                w.addProduct(id, name, stock, day, demand);
                
                
            }
            else if(s.equals("delete")){
                int id=StdIn.readInt();
                w.deleteProduct(id);
                
            }
            else if(s.equals("purchase")){
                int day=StdIn.readInt();
                int id=StdIn.readInt();
                int amount=StdIn.readInt();

                w.purchaseProduct(id, day, amount);
                
            }
            else if(s.equals("restock")){
            int id=StdIn.readInt();
            int amount=StdIn.readInt();
            w.restockProduct(id,amount);
            
        }
    }

    StdOut.println(w);
}
}
