package warehouse;

/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse w=new Warehouse();
        int a=StdIn.readInt();
        for(int i=0; i<a;i++){
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
                w.deleteProduct(id);
            }
        }
        StdOut.println(w);

        
    }
}
