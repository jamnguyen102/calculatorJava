package cis257_a4;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

//import javax.lang.model.element.VariableElement;

import a2_import.AstNode;
import a2_import.Lexer;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

/**
* FXML Controller class
*
* @author Sebastian
*/
public class fxcalculatorController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnEvaluate;

    @FXML 
    private Button btnDeleteDraft;

    @FXML
    private TextField txtExpression;

    @FXML
    private TextField txtResult;

    @FXML 
    private AnchorPane appWindow;

    private VBox vBoxVarTab = new VBox();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onClickEvaluate(ActionEvent event){

        
        vBoxVarTab.getChildren().clear();
        appWindow.getChildren().remove(vBoxVarTab);

        StringBuilder input = new StringBuilder(txtExpression.getText());

        Lexer lexer = new Lexer( input, AstNode.variableTable );

        AstNode result = lexer.startRule();

        if ( result.type == AstNode.ERROR ) {
            txtResult.setText(result.value);
        } else{
            try{
                //Handle input
                if ( (input.toString().length() <= 2)  ){           //Handle variable input e.g. > x     
                    if (! result.lookForKey(input.charAt(0))){   //if variable entered wasnt previously written in hashmap
                        txtResult.setText(("Variable you're calculating doesn't exist."));                                   //TODO popup
                    }
                    else {
                        result.nodeEvaluate();                  //if variable exists in hashmap, retrieve its value
                        txtResult.setText( Double.toString(result.getObjValue(input.charAt(0)) ));                 //TODO popup
                    }
                }
                else {
                    if ( result.getType() == '='){                   // if user enter types '='
                        result.nodeEvaluate();  //                  //then calculate but not print
                        //more things to do
                        
                    }else 
                    txtResult.setText(Double.toString(result.nodeEvaluate()) );
                    
                }
                appWindow.getChildren().add(addVarTab(AstNode.variableTable));
                //Print result
            } catch (ArithmeticException ex){
                txtResult.setText(ex.getMessage());
            }
        }
    }//onClickEvaluate

    
    private VBox addVarTab(HashMap<String, Double> variableTable){
        vBoxVarTab.setPadding(new Insets(10));
        vBoxVarTab.setLayoutX(120);
        vBoxVarTab.setLayoutY(140);
        vBoxVarTab.setSpacing(5);

        for (HashMap.Entry<String, Double> entry : variableTable.entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            
            vBoxVarTab.getChildren().add(new Label(key + " = " + value));
           
        }

        return vBoxVarTab;
    }



}
