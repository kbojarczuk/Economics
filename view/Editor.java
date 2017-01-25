package view;

import controller.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.HTMLEditor;

public class Editor extends BorderPane{
    private ControllerInterface controller;
    private HTMLEditor editor;
    
    /**
     * Create a Bordepane with a HTML Editor in the center and 3 buttons : open, save, save as; in the toolbar
     * @param controller
     * The controller that listens for button interaction
     */
    public Editor(ControllerInterface controller) {
        super();
        this.controller = controller;
        Button open = new Button("open");
        Button save = new Button("save");
        Button saveAs = new Button("save as");
        editor = new HTMLEditor();
        ToolBar toolBar = new ToolBar(open, save, saveAs);
        // event for button : notify controller
        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.openButton();
            }
        });
        
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.saveButton(editor.getHtmlText());
            }
        });
        
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.saveAsButton(editor.getHtmlText());
            }
        });
        
        ScrollPane scrollPane = new ScrollPane();
        
        scrollPane.setContent(editor);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefHeight(500.0);
        scrollPane.setPrefWidth(500.0);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        
        this.setCenter(scrollPane);
        this.setTop(toolBar);
    }
    
    /**
     * set the text of the editor with the string in parameter
     * @param text
     * the text to set as an HTML string
     */
    public void setText(String text){
        editor.setHtmlText(text);
    }
    
    /**
     * returns the text of the editor
     * @return
     * an HTML string containing the text
     */
    public String getText() {
        return editor.getHtmlText();
    }
}
