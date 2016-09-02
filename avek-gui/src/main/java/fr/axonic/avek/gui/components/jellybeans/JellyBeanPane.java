package fr.axonic.avek.gui.components.jellybeans;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Created by Nathaël N on 28/07/16.
 */
public class JellyBeanPane extends HBox {
    private Consumer<JellyBean> onRemoveJellyBean;
    private boolean areJellyBeansEditable;

    public JellyBeanPane() {
        this.getStyleClass().add("jellyBeanPane");
        setAlignment(Pos.CENTER);
        this.areJellyBeansEditable = false;

        this.getStylesheets().add("fr/axonic/avek/gui/components/jellybeans/JellyBean.css");
    }

    public <T,U> void addJellyBean(JellyBeanItem<T,U> item) {
        JellyBean<T,U> jb = new JellyBean<>();
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
            onRemoveJellyBean.accept(jbc);
        }
    }

    void onRemoveJellyBean(Consumer<JellyBean> function) {
        this.onRemoveJellyBean = function;

        for (Node n : getChildren()) {
            JellyBean<?,?> jb = (JellyBean) n;
            jb.setOnDelete(function == null ? null : this::removeJellyBean);
        }
    }

    public void setAllJellyBeansEditable(boolean b) {
        this.areJellyBeansEditable = b;

        for (Node n : getChildren()) {
            ((JellyBean) n).getItem().setEditable(b);
        }
    }

    boolean contains(JellyBeanItem jbi) {
        for (Node n : getChildren()) {
            if (((JellyBean)n).getItem().equals(jbi)) {
                return true;
            }
        }
        return false;
    }

    void remove(Object jbiName) {
        for (Node n : new ArrayList<>(getChildren())) {
            if (((JellyBean) n).getItem().getIdentifier().equals(jbiName)) {
                getChildren().remove(n);
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
