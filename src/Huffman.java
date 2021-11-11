

import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

class HuffmanNode implements Comparable<HuffmanNode> {
    int freq;
    char c;
    HuffmanNode left,right;
    HuffmanNode(){
        freq=0;

    }
   HuffmanNode(char c,int freq,HuffmanNode left,HuffmanNode right){
       this.freq=freq;
       this.c=c;
       this.left=left;
       this.right=right;

   }

    public boolean isLeaf() {
        return right == null && left == null;
    }

    @Override
    public int compareTo(HuffmanNode that){
       return this.freq-that.freq;
    }
}
public class Huffman {
    Reader R;
    PrintWriter W;
    HashMap<Character,Integer> map = new HashMap<>();
    PriorityQueue<HuffmanNode> pque= new PriorityQueue<>();
    HashMap<Character,String> dict = new HashMap<>();
    public Huffman(){
    }

    public void freq(String In) throws IOException{ //Making a frequency Map
        R = new FileReader(In);
        int nextChar;
        while ((nextChar=R.read())!=-1){
            map.merge((char) nextChar, 1, Integer::sum);
        }

    }
    public void printCode(HuffmanNode root, String s)//building the binary for the dictionary
    {
        if(root.isLeaf()){
            dict.put(root.c,s);
            return;
        }
        printCode(root.left,s+"0");
        printCode(root.right,s+"1");
    }
    public void buildTree(HashMap<Character,Integer> map){ //Making the dictionary
        for(char c:map.keySet()){
            HuffmanNode node= new HuffmanNode(c,map.get(c),null,null);
            System.out.println("character: "+node.c+", frequency:"+node.freq);
            pque.add(node);
        }
        HuffmanNode root =new HuffmanNode();
        while (pque.size()>1){
            HuffmanNode left = pque.peek();
            pque.poll();
            HuffmanNode right = pque.peek();
            pque.poll();

            HuffmanNode f = new HuffmanNode();
            f.freq = left.freq+right.freq;
            f.c='-';
            f.left=left;
            f.right=right;
            root=f;
            pque.add(f);
        }
        printCode(root,"");
    }

    public void writeTree(String In)throws IOException{ //Write Dictionary to compress
        W= new PrintWriter(In+"_Dictionary");
        for(Character c: dict.keySet()){
            W.println(c+","+dict.get(c));
        }
        W.flush();
        W.close();
    }


    public void compress(String In) throws IOException{
        freq(In);
        buildTree(map);
        writeTree(In);
        R= new FileReader(In);
        W= new PrintWriter(In+"Compressed");
        int next;
        while ((next=R.read())!=-1){
            W.print(dict.get((char)next));
        }

        R.close();
        W.flush();
        W.close();
    }

    public void readTree(String In) throws IOException{ //Read Dictionary To decompress
        //Scanner R = new Scanner(In+"_Dictionary");
        //R = new FileReader(In+"_Dictionary");   TODO choose one
    }
    public void readCompressed(String In)throws IOException{
        //Scanner R = new Scanner(In+"Compressed");
        //R = new FileReader(In+"Compressed");   TODO choose one
    }
    public void decompress(String In) throws IOException{

    }


    public static void main(String[] args) {
        System.out.println("1.compress" +
                "\n2.decompress");
        Scanner in = new Scanner(System.in);
        int input=in.nextInt();
        Huffman huff = new Huffman();
        if(input==1) {



            try {
                huff.compress("input");
            } catch (FileNotFoundException e) {
                System.out.println("File Not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(input==2){
            try {
                huff.decompress("input");
            } catch (FileNotFoundException e) {
                System.out.println("File Not found");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
