package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author David Rahabi
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * 
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        sortedCharFreqList= new ArrayList<CharFreq>();
        int[] at=new int[128];
        int totalChars=0;
        while(StdIn.hasNextChar()){
            char a=StdIn.readChar();
            at[a]=at[a]+1;
            totalChars++;
        }
        char save=' ';
        for(int i=0;i<at.length;i++){
            if(at[i]>0){
                Character n= (char) i;
               double t=(double) totalChars;
               double as= (double) at[i];
                double p=as/t;
                if(p==1.0){
                    int ii=i+1;
                    if(i==127){
                        ii=0;
                    }
                    save=(char) ii;
                    Character f=save;
                    CharFreq d= new CharFreq(f,0.0);
                    sortedCharFreqList.add(d);
                }
                CharFreq b= new CharFreq(n,p);
                sortedCharFreqList.add(b);
            }
        }
        Collections.sort(sortedCharFreqList);
    }
    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {
        Queue<CharFreq> source= new Queue<CharFreq>(); //initialize queues
        Queue<TreeNode> target=new Queue<TreeNode>();
        for(int i=0; i<sortedCharFreqList.size(); i++){
            source.enqueue(sortedCharFreqList.get(i));    
         }
         while(!source.isEmpty()){ //execute until there is nothing left in source
          
            if(target.isEmpty()){        //set up the first parent node on the first iteration of the while loop
            CharFreq r=source.dequeue();
            TreeNode L= new TreeNode(r,null,null);
            
            CharFreq y=source.dequeue();
            TreeNode R= new TreeNode(y,null,null);

            double ar=r.getProbOcc();
            double ay=y.getProbOcc();
            double a=ar+ay; // adding the two probabilities for the parents
            
            CharFreq prnt= new CharFreq(null, a);

            TreeNode parent= new TreeNode(prnt,L,R);

            target.enqueue(parent);
            }
            if(!target.isEmpty() && !source.isEmpty()){  //execute the rest only if target is not empty
                                                        //source not empty was added to account for input 2-
                                                        //where source is empty after the first if statement above
                TreeNode l=null;
                TreeNode r=null;

                TreeNode parent=null; // initialize left and right nodes and parent node before executing for loop

                for(int i=0; i<2;i++){ //for loop runs twice to pick two child nodes
                    if(target.isEmpty()){ //if target is empty, this means that it was chosen in the first iteration of- 
                                          // this for loop, so by default you have to dequeue source to be the larger-
                                          //right node
                        CharFreq k=source.dequeue();
                        r=new TreeNode(k,null,null);
                    }
                    else if(source.isEmpty()){ // if source is empty, then that means that it was chosen in the first 
                                                //iteration of this loop, and this will be the last time this entire
                                                //block of code executes, so now you must dequeue from target as the
                                                //greater node
                        r=target.dequeue();
                    }
                    else{   //normal case where both source and target are not empty
                    
                double s= source.peek().getProbOcc();
                double t= target.peek().getData().getProbOcc(); //store the values of front nodes of source and target

                if(s<=t){ //if the source node is less than or equal to the target node, dequeue source
                    CharFreq n= source.dequeue();
                    if(l==null){      
                        l=new TreeNode(n,null,null); // if l has not been set yet, then the dequeued node must be the left
                                                    //node of the tree
                    }
                    else{
                        r=new TreeNode(n,null,null); //if l has already been set, then the dequeude node must be the right
                                                     // node of the tree
                    }
                }
                else{ //if target is less, dequeue from it
                    TreeNode p=target.dequeue();
                    if(l==null){ //same as above (except now its setting a treenode instead of a CharFreq)
                        l=p;
                    }
                    else{
                        r=p;
                    }
                } 
                }
             }

             //after this for loop is done, you will have your left and right nodes set
             //just make the new charfreq with their added up values, set the parent node, and enqueue it into target
            
                 double ll=l.getData().getProbOcc();
                 double rr= r.getData().getProbOcc();

                 CharFreq pval= new CharFreq(null, ll+rr);
                 parent=new TreeNode(pval, l, r);
                 target.enqueue(parent);
            }
        }
        while(target.size()!=1){   
                TreeNode l=target.dequeue();
                if(target.size()>=1){
                TreeNode r=target.dequeue();
                double val= l.getData().getProbOcc()+r.getData().getProbOcc();
        
                CharFreq pv= new CharFreq(null, val);
                TreeNode parent=new TreeNode(pv, l, r);
                target.enqueue(parent);
                }
        }
       huffmanRoot=target.dequeue();
    }

private Boolean traverse(TreeNode n, String s, int c ){
    
    if(n.getData().getCharacter()!=null && (int)((char)n.getData().getCharacter())==c){
       encodings[c]=s;
       return true;
     }
     if(n.getLeft()!=null){
    
        if(traverse(n.getLeft(),s.concat("0"),c)) return true;
     }
     if(n.getRight()!=null){
        
        if(traverse(n.getRight(),s.concat("1"),c)) return true;
     }
        
     return false;
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
        encodings=new String[128];
        for(int i=0; i<sortedCharFreqList.size(); i++){
            char a=sortedCharFreqList.get(i).getCharacter();
            int b=(int)a;
            traverse(huffmanRoot,"",b);
         }
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String p="";
        while(StdIn.hasNextChar()){
            int gg=(int)StdIn.readChar();
            
            p=p+encodings[gg];

        }
        writeBitString(encodedFile, p);

	/* Your code goes here */
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * 
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

        String s=readBitString(encodedFile);

        String p="";

        TreeNode ptr=huffmanRoot;
        char[] c=s.toCharArray();

        int i=0;
        while (i<c.length){

            if(ptr.getData().getCharacter()!=null){
                char a=ptr.getData().getCharacter();
                p=p+a;
                ptr=huffmanRoot;
            }
            if((c[i]=='0')){
                ptr=ptr.getLeft();
                i++;
                continue;
            }
             if(c[i]=='1'){
                ptr=ptr.getRight();
                i++;   
            }
        }
        char a=ptr.getData().getCharacter();
        p=p+a;
        
        char[] d= p.toCharArray();
        for(int j=0;j<d.length;j++){
            //System.out.print(d[i]);
            StdOut.print(d[j]);
        }
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * 
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
