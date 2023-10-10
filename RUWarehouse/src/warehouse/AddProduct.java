package warehouse;

/*
 * Use this class to test to addProduct method.
 */
public class AddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse w=new Warehouse();

        int a=StdIn.readInt();

        for(int i=0;i<a;i++){
            int id=StdIn.readInt(); 
            int stock=StdIn.readInt(); 
            String name=StdIn.readString(); 
            int day=StdIn.readInt(); 
            int demand=StdIn.readInt();

            w.addProduct(id,name,stock,day,demand);
            

        }
        StdOut.println(w);
	
    }
}
