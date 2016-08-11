package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.ContinuousAVar;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class BoundedParameter extends SensitiveParameter {
    private final static Logger LOGGER = Logger.getLogger(BoundedParameter.class);

    private final HBox generalizationPane;
    private final TextField minEquivRange;
    private final TextField maxEquivRange;

    public <T extends AVar & ContinuousAVar> BoundedParameter(int level, T paramValue) {
        super(level, paramValue);

        generalizationPane = new HBox();

        minEquivRange = new TextField(paramValue.getValue().toString());
        maxEquivRange = new TextField(paramValue.getValue().toString());
        // Pretty print dates
        if(paramValue.getValue() instanceof Calendar) {
            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern("dd/MM/yyyy HH:mm:ss");
            minEquivRange.setText(df.format(((Calendar)paramValue.getValue()).getTime()));
            maxEquivRange.setText(df.format(((Calendar)paramValue.getValue()).getTime()));
        }

        // Compute boxes size
        final double width = new Text(paramValue.getValue().toString()+"___")
                .getLayoutBounds().getWidth() // This big is the Text in the TextField (+ 3 char length)
                + minEquivRange.getPadding().getLeft() + minEquivRange.getPadding().getRight(); // Add the padding of the TextField

        minEquivRange.setMaxWidth(width);
        maxEquivRange.setMaxWidth(width);

        generalizationPane.getChildren().add(minEquivRange);
        generalizationPane.getChildren().add(new Label(" - "));
        generalizationPane.getChildren().add(maxEquivRange);

        // GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
        // GridPane.setColumnIndex(levelMark, 1);
        // GridPane.setColumnIndex(this.paramTitle, 2);
        // GridPane.setColumnIndex(this.paramValue, 3);
        GridPane.setColumnIndex(generalizationPane, 4);

        minEquivRange.textProperty().addListener((observable, oldval, newval) -> {
            try {
                minEquivRange.getStyleClass().remove("bad-input");
                switch(paramValue.getFormat().getType()) {
                    case NUMBER:
                        minEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                        paramValue.setMin(Double.valueOf(newval));
                        break;
                    case DATE:
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        minEquivRange.setTooltip(new Tooltip("DATE required\nexample: '2016/12/31 23:59:59'"));
                        cal.setTime(textFormat.parse(newval));
                        paramValue.setMax(cal);
                        break;
                    default:
                        throw new Exception("Cannot cast "+paramValue+", "+oldval+" > "+newval);
                }
                minEquivRange.setTooltip(null); // If no error, remove tooltip
            } catch (Exception e) {
                minEquivRange.getStyleClass().add("bad-input");
                LOGGER.warn(e.getMessage());
            }
        });
        maxEquivRange.textProperty().addListener((observable, oldval, newval) -> {
            try {
                maxEquivRange.getStyleClass().remove("bad-input");
                switch(paramValue.getFormat().getType()) {
                    case NUMBER:
                        maxEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                        paramValue.setMax(Double.valueOf(newval));
                        break;
                    case DATE:
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        maxEquivRange.setTooltip(new Tooltip("DATE required\nexample: '2016/12/31 23:59:59'"));
                        cal.setTime(textFormat.parse(newval));
                        paramValue.setMax(cal);
                        break;
                    default:
                        throw new Exception("Cannot cast "+paramValue+", "+oldval+" > "+newval);
                }
                maxEquivRange.setTooltip(null); // If no error, remove tooltip
            } catch (Exception e) {
                maxEquivRange.getStyleClass().add("bad-input");
                LOGGER.warn(e.getMessage());
            }
        });
    }

    @Override
    protected void onClickMarkedUtil(ActionEvent event) {
        super.onClickMarkedUtil(event);

        boolean b = markedUtil.isSelected();
        minEquivRange.setDisable(!b);
        maxEquivRange.setDisable(!b);
    }

    @Override
    public Set<Node> getElements() {
        Set<Node> elts = super.getElements();
        elts.add(generalizationPane);

        return elts;
    }
}
