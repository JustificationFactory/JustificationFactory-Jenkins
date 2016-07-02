package fr.axonic.avek.gui.view.jellyBean;

import fr.axonic.avek.gui.model.IResultElement;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by cduffau on 02/07/16.
 */
public class JellyBeansSelector extends HBox {

    @FXML
    private Button addJellyBeanButton;
    @FXML
    private FlowPane jellyBeansPane;
    @FXML
    private ComboBox<IResultElement> comboBoxJellyBean;

    private Set<JellyBean> jellyBeans;

    public JellyBeansSelector(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/jellyBean/jellyBeansSelector.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    protected void initialize() {
        jellyBeans = new HashSet<>();
        updateJellyBeanChoice();
    }

    @FXML
    void onAddJellyBeanClicked(ActionEvent event) {

        // Verify if JellyBeanController already created (this result is already selected)
        IResultElement choice = comboBoxJellyBean.getValue();
        if(jellyBeans.contains(choice))
            return;

        try {

            JellyBean jb2 = new JellyBean();
            jb2.setResultElement(choice);
            jb2.setMainController(this);
            jellyBeansPane.getChildren().add(jb2);
            jellyBeans.add(jb2);
            updateJellyBeanChoice();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Set<JellyBean> getJellyBeans() {
        return jellyBeans;
    }


    /**
     * Set Combobox entries for already selected effects to 'selectedResult' style, and others to 'notSelectedResult'
     * (typically, set selected results entries in a grey color, and let the others black)
     */
    private void updateJellyBeanChoice() {
        comboBoxJellyBean.setCellFactory(
                new Callback<ListView<IResultElement>, ListCell<IResultElement>>() {
                    @Override public ListCell<IResultElement> call(ListView<IResultElement> param) {
                        final ListCell<IResultElement> cell = new ListCell<IResultElement>() {
                            @Override public void updateItem(IResultElement item, boolean empty) {
                                super.updateItem(item, empty);
                                if(item != null) {
                                    setText(item.getName());
                                    getStyleClass().remove("selectedResult");
                                    getStyleClass().remove("notSelectedResult");
                                    getStyleClass().add(jellyBeans.contains(item)?"selectedResult":"notSelectedResult");
                                }
                            }
                        };
                        return cell;
                    }
                });
    }

    public void removeJellyBean(JellyBean jbc) {
        jellyBeans.remove(jbc);
        jellyBeansPane.getChildren().remove(jbc);
        updateJellyBeanChoice();
    }

    public void setJellyBeansChoice(ObservableList<IResultElement> items) {
        this.comboBoxJellyBean.setItems(items);
    }
}
