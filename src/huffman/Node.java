/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author user
 */
class Node {
    Character ch;
    Integer freq;
    Node left = null, right = null;
 
    Node(Character ch, Integer freq) {
        this.ch = ch;
        this.freq = freq;
    }
 
    public Node(Character ch, Integer freq, Node left, Node right) {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
    
}
