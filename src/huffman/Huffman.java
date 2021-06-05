/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
public class Huffman {


     static String Sentence="";
     static String Sentencecode="";
    static StringBuilder decode = new StringBuilder();
    static StringBuilder decomp = new StringBuilder();
     static  PriorityQueue<Node> pQueue   = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq)); 
   static Map<Character, Integer> freq = new HashMap<>();
   static Map<Character, Character> ascii = new HashMap<>();
static Map<Character, String> code = new HashMap<>();
static int num;
static String huffmantree="";
    /**
     * @param args the command line arguments
     */
 

   static void treeTraverse(Node root,String str)
   {
        if (root == null) {
            return;
        }
 
        // Found a leaf node
        // insert 1 if there is only one node in the tree
        if (root.right==null&&root.left==null) {
            code.put(root.ch, str.length() > 0 ? str : "1");
        }

        treeTraverse(root.left, str + '1');
        treeTraverse(root.right, str + '0');
   }
   public static void ascii(){
        Iterator codeIterator = code.entrySet().iterator(); 
         
        
        while (codeIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)codeIterator.next(); 
            char marks = (char) mapElement.getKey(); 
            String bin=(String) mapElement.getValue();
            String asciicode="";
            int m=0;
            
            if(bin.length()<8){
                m=8-bin.length();
                for(int i=0;i<m;i++){
                    asciicode=asciicode+"0";
                }
                asciicode=asciicode+bin;
                
            }
            ascii.put(marks, (char)Integer.parseInt(asciicode, 2));
            
            
        } 
       
   }
   public static void huffmantree(){
       
         Iterator codeIterator = code.entrySet().iterator(); 
       while (codeIterator.hasNext()) { 
         Map.Entry mapElement = (Map.Entry)codeIterator.next();
           huffmantree+=mapElement.getKey();
           huffmantree+=mapElement.getValue();
       
       
       }
   }
   
   
   
   
   
   
   static void HuffmanTree(String fileName)
   {
       int numzero=0;
      
       freq.entrySet().stream().forEach((entry) -> {
           pQueue.add(new Node(entry.getKey(), entry.getValue()));
       });
       while (pQueue.size() != 1)
        {
            // Remove the two nodes of the highest priority
            // (the lowest frequency) from the queue
 
            Node left = pQueue.poll();
            Node right = pQueue.poll();
 
            // Create a new internal node with these two nodes as children
            // and with a frequency equal to the sum of the two nodes'
            // frequencies. Add the new node to the priority queue.
 
            int sum = left.freq + right.freq;
            pQueue.add(new Node(null, sum, left, right));
        }
       treeTraverse(pQueue.peek(),"");
       System.out.println("Huffman Codes are: " + code);
        System.out.println("Original string is: " +Sentence);
       StringBuilder sb = new StringBuilder();
        for (char c: Sentence.toCharArray()) {
            sb.append(code.get(c));
        }
        while(sb.length()%8!=0)
        {
            sb.append('0');
            numzero++;
        }
        System.out.println("Encoded string is: " +sb);
        int i=1;
        String con="";
        StringBuilder compressed=new StringBuilder();
        compressed.append(Integer.toString(numzero));
        
         try {
      File myObj = new File(getFileName(fileName));
      if (myObj.createNewFile()) {
        System.out.println("File created: " + myObj.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        
        while(i<=sb.length())
        {
            
            if(i%8==0)
            {   
                  con+=sb.charAt(i-1);
                int decimal=Integer.parseInt(con,2);
                    
                 compressed.append( Character.toString((char)decimal));
                 con="";
                 
            }
            else
                con+=sb.charAt(i-1);
            i++;
        }
        System.out.println(compressed);
        huffmantree();
           try {
      FileWriter myWriter = new FileWriter(getFileName(fileName));
      
      myWriter.write(huffmantree+"\n");
      myWriter.write(compressed.toString());
      myWriter.close();
    // System.out.println("file is compressed successfully.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
               
               
        
       
       // int decimal=Integer.parseInt(sb.toString());
        
       
   }
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
    static void CountFreq(String text)
    {
        for (char c: text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
    }
    
   static void ReadFile(Scanner input,String fileName)
    {
        try {
            

            File file = new File(fileName);

            input = new Scanner(file);
            

            while (input.hasNextLine()) {
                String line = input.nextLine();
                CountFreq(line);
               // System.out.println(line);
                Sentence=Sentence+line;
                
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    static void deReadFile(Scanner input,String fileName){
         boolean flag = true;
        try {
            

            File file = new File(fileName);

            input = new Scanner(file);
       
           

            while (input.hasNextLine()) {
                String line = input.nextLine();
               
                     if(flag){
                huffmantree =huffmantree+ line; 
                       flag=false;
                     }else if(!flag){
               // System.out.println(line);
                Sentencecode=Sentencecode+line;
                     }
            }
            input.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
       
       
       
   }
   static String getFileName(String fileName)
   {
       String str=fileName.substring(0, fileName.length()-4);
       str=str+"-compressed.txt";
       return str;
   }
         static String getFileNamedecomp(String fileName)
   {
       String str=fileName.substring(0, fileName.length());
      str= str.replaceAll("-compressed.txt",".txt");
       return str;
   }
      public static void decomp(String fileName){
      num=Integer.parseInt(String.valueOf(Sentencecode.charAt(0)));
      int i;
     int n,m;
    
      String zero="";
      for(i=1;i<Sentencecode.length();i++){
          
      
         n=(int)Sentencecode.charAt(i);
         
         zero=Integer.toBinaryString(n);
         if(zero.length()<8){
             m=8-zero.length();
             for(int j=0 ;j<m;j++){
                 decode.append('0');
             }
             decode.append(Integer.toBinaryString(n));
         }else{
             decode.append(Integer.toBinaryString(n));
         }
         
         
         zero=""; 
      }
           
        //  System.out.println(decode);
          Node root = pQueue.peek();
           if (isLeaf(root)) {
            // Special case: For input like a, aa, aaa, etc.
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        }
        else {
           
            int index = -1;
            while (index < decode.length() - num-1) {
                index = decode(root, index,decode);
            }
  }
           
            try {
      FileWriter myWriter = new FileWriter(getFileName(fileName));
      
      
      myWriter.write(decomp.toString());
      myWriter.close();
     System.out.println("file is compressed successfully.");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
           
              
      }
    //11000001001111101000101101011110110000001001010010101100
   // 11000001001111101000101101011110110000001001010010101100
      
      
      
         public static boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }
      
       public static int decode(Node root, int index, StringBuilder sb)
    {
        if (root == null) {
            return index;
        }
 
        // Found a leaf node
        if (isLeaf(root)) {
            //System.out.print(root.ch);
            decomp.append(root.ch);
            return index;
        }
 
        index++;
 
        root = (sb.charAt(index) == '1') ? root.left : root.right;
        index = decode(root, index, sb);
        return index;
    }
    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);
           System.out.println("Enter the file name with extension : ");
             
             
             String fileName=input.nextLine();
             
        System.out.print("1-comp : \n");
        System.out.print("2-decomp : \n");
         
         
            //String fileName=input.nextLine();
         
        // System.out.println(input.nextLine());
           
            
            
            char ch =input.next().charAt(0);
            switch(ch){
                case '1':
          
             
         
              ReadFile(input,fileName);
               HuffmanTree(fileName);
                
              
                     main(args);
               break;
                case '2':
              
                   String str= getFileNamedecomp(fileName);
                    deReadFile(input,fileName);
                    decomp(str);
                    System.out.println("huffman.Huffman.main()"+huffmantree);
                    main(args);
               
                break;
            }
    }
    
}
