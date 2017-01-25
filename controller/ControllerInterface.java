package controller;

public interface ControllerInterface {
    /**
     * Triggered when a "save" button is clicked.
     * Allows the controller to get access to the text of the Editor and save it to the current opened file
     * @param editorString
     * the editor's text as a String
     */
    public void saveButton(String editorString);
    /**
     * Triggered when a "open" button is clicked.
     * Allows the controller to load the content from a file and send it to the Editor.
     */
    public void openButton();
    /**
     * Triggered when a "save as" button is clicked.
     * Allows the Controller to select a different file from the one currently opened and save the Editor text.
     * @param editorString
     * the editor's text as a String
     */
    public void saveAsButton(String editorString);
    /**
     * Triggered when a "notes" button is clicked.
     * Allows the controller to display the editor.
     */
    public void showEditor();
}
