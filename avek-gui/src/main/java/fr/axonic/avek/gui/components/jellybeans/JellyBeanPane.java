package fr.axonic.avek.gui.components.jellybeans;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class JellyBeanPane extends HBox {

    private Consumer<String> onRemoveJellyBean;
    private boolean areJellyBeansEditable;

    public JellyBeanPane() {
        this.getStyleClass().add("jellyBeanPane");
        setAlignment(Pos.CENTER);
        this.areJellyBeansEditable = false;

        this.getStylesheets().add("css/components/JellyBean.css");
    }

    public void addJellyBean(String label, List<String> stateList) {
        JellyBean jb = new JellyBean();
        jb.setStates(stateList);
        jb.setText(label);

        getChildren().add(jb);

        jb.setEditable(areJellyBeansEditable);
        if (onRemoveJellyBean != null) {
            jb.setOnDelete(this::removeJellyBean);
        }
    }

    private void removeJellyBean(JellyBean jbc) {
        getChildren().remove(jbc);
        if (onRemoveJellyBean != null) {
            onRemoveJellyBean.accept(jbc.getText());
        }
    }

    void onRemoveJellyBean(Consumer<String> function) {
        this.onRemoveJellyBean = function;

        for (Node n : getChildren()) {
            JellyBean jb = (JellyBean) n;
            jb.setOnDelete(function == null ? null : this::removeJellyBean);
        }
    }

    public void setJellyBeansStateEditable(boolean b) {
        this.areJellyBeansEditable = b;

        for (Node n : getChildren()) {
            JellyBean jb = (JellyBean) n;
            jb.setEditable(b);
        }
    }

    boolean contains(String key) {
        for (Node n : getChildren()) {
            if (((JellyBean) n).getText().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String key) {
        for (Node n : new ArrayList<>(getChildren())) {
            if (((JellyBean) n).getText().equals(key)) {
                getChildren().remove(n);
                break;
            }
        }
    }

    /**
     *
     * @return Map&lt;JellyBean's label, JellyBean's state&gt;
     */
    public Map<String, String> getJellyBeans() {
        Map<String, String> map = new HashMap<>();
        for(Node n : getChildren())
            map.put(((JellyBean)n).getText(),
                    ((JellyBean)n).getState());

        return map;
    }
}
