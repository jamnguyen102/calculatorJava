/*
 * @topic T10173 Desktop Calculator v3
 * @brief class AstNode represents a building block for AST, the abstract syntax tree
*/
package a2_import;

import java.util.HashMap;

public class AstNode {
    //---------------------------
    // constants
    //---------------------------
    public static final int NUMBER = 'N';
    public static final int END = 'Z';
    public static final int ERROR = 'E';
    public static final int IDENTIFIER = 'I';
    /*----------------
    /   Hashmap
    /----------------*/
    static public HashMap<String, Double> variableTable = new HashMap<>();
    //---------------------------
    // data
    //---------------------------
    public int type;
    public String value;
    public AstNode leftNode;
    public AstNode rightNode;

    //---------------------------
    // constructors
    //---------------------------
    public AstNode(int type) {
        this.type = type;
        this.value = "";
        this.leftNode = null;
        this.rightNode = null;
    }//AstNode

    public AstNode(int type, String value) {
        this.type = type;
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }//AstNode

    //---------------------------
    // operations
    //---------------------------
    public void print( int level ) {
        for ( int idx = 0; idx < level; ++idx ) System.out.print( "." );
        System.out.println( value );
        if ( leftNode != null ) leftNode.print( level + 1 );
        if ( rightNode != null ) rightNode.print( level + 1 );
    }//print

    public double nodeEvaluate(){

        switch(type){
            case IDENTIFIER:
                return getObjValue(value.charAt(0)) ;
            case NUMBER:
                return Double.parseDouble(value);
            case '+': // addition
                return leftNode.nodeEvaluate() + rightNode.nodeEvaluate();
            case '-': // minus
                if ( leftNode == null)              // if none left operand
                    return -rightNode.nodeEvaluate();// then return negative right operand
                //else left operand - right operand
                return leftNode.nodeEvaluate() - rightNode.nodeEvaluate();
            case '*':
                return leftNode.nodeEvaluate() * rightNode.nodeEvaluate();
            case '/':
                if (Double.parseDouble(rightNode.value) == 0)
                    throw new ArithmeticException("Error: cannot divide 0");
                return (double)leftNode.nodeEvaluate() / rightNode.nodeEvaluate();
            case '%':
                if (Double.parseDouble(rightNode.value) == 0)
                    throw new ArithmeticException("Error: cannot divide 0");
                return leftNode.nodeEvaluate() % rightNode.nodeEvaluate();
            case '=':
                // x = expression, since x is at leftnode, calculate only rightnode.
                variableTable.put(leftNode.value, rightNode.nodeEvaluate() );
                break;
        }
        return 0.0;
    }//nodeEvaluate

    public boolean lookForKey(char input_key){
        for (HashMap.Entry<String, Double> entry : variableTable.entrySet()) {
            String key = entry.getKey();

            if (input_key == key.charAt(0))
                return true;
        }
        return false;
    }//lookForVariable in hashmap

    public int getType(){
        return type;
    }//getType of node

    public double getObjValue( char input_key){
        String convertedKey = Character.toString(input_key);
        return variableTable.get(convertedKey);
    }//getObjValue in hashmap

    public void deleteHashMap(){
        variableTable.clear();
    }

}//class AstNode
