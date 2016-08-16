package fr.axonic.avek.gui.components.jellybeans;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

        this.getStylesheets().add("fr/axonic/avek/gui/components/jellybeans/JellyBean.css");
    }

    public <T> void addJellyBean(JellyBeanItem<T> item) {
        JellyBean<T> jb = new JellyBean<>();
        jb.set(item);

        getChildren().add(jb);

        item.setEditable(areJellyBeansEditable);
        if (onRemoveJellyBean != null) {
            jb.setOnDelete(this::removeJellyBean);
        }
    }

    private void removeJellyBean(JellyBean jbc) {
        getChildren().remove(jbc);
        if (onRemoveJellyBean != null) {
            onRemoveJellyBean.accept(jbc.getItem().getText());
        }
    }

    void onRemoveJellyBean(Consumer<String> function) {
        this.onRemoveJellyBean = function;

        for (Node n : getChildren()) {
            JellyBean<?> jb = (JellyBean) n;
            jb.setOnDelete(function == null ? null : this::removeJellyBean);
        }
    }

    public void setJellyBeansStateEditable(boolean b) {
        this.areJellyBeansEditable = b;

        for (Node n : getChildren()) {
            JellyBean jb = (JellyBean) n;
            jb.getItem().setEditable(b);
        }
    }

    boolean contains(String key) {
        for (Node n : getChildren()) {
            if (((JellyBean) n).getItem().getText().equals(key)) {
                return true;
            }
        }
        return false;
    }

    public void remove(String key) {
        for (Node n : new ArrayList<>(getChildren())) {
            if (((JellyBean) n).getItem().getText().equals(key)) {
                getChildren().remove(n);
                break;
            }
        }
    }

    /**
     *
     * @return Map&lt;JellyBean's label, JellyBean's state&gt;
     */
    public List<JellyBeanItem> getJellyBeans() {
        return getChildren().stream()
                            .map(n -> ((JellyBean) n).getItem())
                            .collect(Collectors.toList());
    }
}
