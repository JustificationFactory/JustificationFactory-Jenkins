package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.avek.gui.Main;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.ContinuousAVar;
import fr.axonic.validation.exception.VerificationException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class BoundedParameter<T> extends SensitiveParameter<T> {
    private final static Logger LOGGER = Logger.getLogger(BoundedParameter.class);
    private final static int TEXT_FIELD_WIDTH = 70; // px

    private final HBox generalizationPane;
    private final TextField minEquivRange;
    private final TextField maxEquivRange;

    public <TT extends AVar<T> & ContinuousAVar<T>> BoundedParameter(int level, TT paramValue) {
        super(level, paramValue);

        generalizationPane = new HBox();

        minEquivRange = new TextField(paramValue.getValue().toString());
        minEquivRange.setMaxWidth(TEXT_FIELD_WIDTH);

        maxEquivRange = new TextField(paramValue.getValue().toString());
        maxEquivRange.setMaxWidth(TEXT_FIELD_WIDTH);

        generalizationPane.getChildren().add(minEquivRange);
        generalizationPane.getChildren().add(new Label(" - "));
        generalizationPane.getChildren().add(maxEquivRange);

        // GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
        // GridPane.setColumnIndex(levelMark, 1);
        // GridPane.setColumnIndex(this.paramTitle, 2);
        // GridPane.setColumnIndex(this.paramValue, 3);
        GridPane.setColumnIndex(generalizationPane, 4);


        // Pretty print dates
        if(paramValue.getValue() instanceof Calendar) {
            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern("dd/MM/yyyy HH:mm:ss");
            minEquivRange.setText(df.format(((Calendar)paramValue.getValue()).getTime()));
            maxEquivRange.setText(df.format(((Calendar)paramValue.getValue()).getTime()));
        }

        minEquivRange.textProperty().addListener((observable, oldval, newval) -> {
            try {
                minEquivRange.getStyleClass().remove("bad-input");
                switch(paramValue.getFormat().getType()) {
                    case NUMBER:
                        paramValue.setMin((T) Double.valueOf(newval));
                        break;
                    case DATE:
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        cal.setTime(textFormat.parse(newval));
                        paramValue.setMax((T) cal);
                        break;
                    default:
                        System.out.println("Cannot cast "+paramValue+", "+oldval+" > "+newval);
                        throw new Exception();
                }
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
                        paramValue.setMax((T) Double.valueOf(newval));
                        break;
                    case DATE:
                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat textFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        cal.setTime(textFormat.parse(newval));
                        paramValue.setMax((T) cal);
                        break;
                    default:
                        throw new Exception("Cannot cast "+paramValue+", "+oldval+" > "+newval);
                }
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
