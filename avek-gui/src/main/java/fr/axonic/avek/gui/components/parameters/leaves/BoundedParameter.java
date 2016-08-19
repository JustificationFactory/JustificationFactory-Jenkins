package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.AContiniousDate;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.ContinuousAVar;
import fr.axonic.validation.exception.VerificationException;
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
    public static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final HBox generalizationPane;
    private final TextField minEquivRange;
    private final TextField maxEquivRange;

    public <T extends AVar<U> & ContinuousAVar<U>, U> BoundedParameter(int level, T paramValue) {
        super(level, paramValue);

        generalizationPane = new HBox();

        // For default, set generalization min and max equals to current value
        try {
            if(paramValue.getMin() == null)
                paramValue.setMin(paramValue.getValue());

            if(paramValue.getMax() == null)
                paramValue.setMax(paramValue.getValue());
        } catch(VerificationException ve) {
            throw new RuntimeException();
        }

        // Write default values in text fields
        minEquivRange = new TextField(paramValue.getMin().toString());
        maxEquivRange = new TextField(paramValue.getMax().toString());

        // Pretty print if values are dates
        if(paramValue.getValue() instanceof Calendar) {
            SimpleDateFormat df = new SimpleDateFormat();
            df.applyPattern(DATE_FORMAT);
            minEquivRange.setText(df.format(((Calendar)paramValue.getMin()).getTime()));
            maxEquivRange.setText(df.format(((Calendar)paramValue.getMax()).getTime()));
        }

        // When minimum (in text field) is edited by user
        // check-it and write it in red if value not respecting format
        // else save-it in parameter if is a correct value
        minEquivRange.textProperty().addListener((observable, oldval, newval) -> {
            try {
                // remove style of bad input, doing nothing if last value wasn't bad
                minEquivRange.getStyleClass().remove("bad-input");
                if(paramValue instanceof AContinuousNumber) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    minEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                    // Setting the minimum value of parameter (will throw exception if bad input)
                    paramValue.setMin((U) Double.valueOf(newval));
                }
                else if(paramValue instanceof AContiniousDate) {
                    // Setting tooltip in case of error (will be removed if correct input)

                    minEquivRange.setTooltip(new Tooltip("DATE required\nexample: '31/12/2016 23:59:59'"));

                    // Setting the minimum value of parameter (will throw exception if bad input)
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat textFormat = new SimpleDateFormat(DATE_FORMAT);
                    cal.setTime(textFormat.parse(newval));
                    paramValue.setMax((U) cal);
                }
                else {
                    // RuntimeException because it isn't user fault but a developer problem
                    throw new RuntimeException("Unknown type for bounded parameter: "+paramValue.getClass()+" for "+paramValue);
                }

                // If no exception thrown, input was correct, so removing tooltip
                minEquivRange.setTooltip(null);
            } catch (Exception e) {
                // Input wasn't correct
                minEquivRange.getStyleClass().add("bad-input");
                LOGGER.warn(e.getMessage());
            }
        });

        // When maximum (in text field) is edited by user
        // check-it and write it in red if value not respecting format
        // else save-it in parameter if is a correct value
        maxEquivRange.textProperty().addListener((observable, oldval, newval) -> {
            try {
                // remove style of bad input, doing nothing if last value wasn't bad
                maxEquivRange.getStyleClass().remove("bad-input");
                if(paramValue instanceof AContinuousNumber) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    maxEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                    // Setting the maximum value of parameter (will throw exception if bad input)
                    paramValue.setMax((U) Double.valueOf(newval));
                }
                else if(paramValue instanceof AContiniousDate) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    maxEquivRange.setTooltip(new Tooltip("DATE required\nexample: '31/12/2016 23:59:59'"));

                    // Setting the maximum value of parameter (will throw exception if bad input)
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat textFormat = new SimpleDateFormat(DATE_FORMAT);
                    cal.setTime(textFormat.parse(newval));
                    paramValue.setMax((U) cal);
                }
                else {
                    // RuntimeException because it isn't user fault but a developer problem
                    throw new RuntimeException("Unknown type for bounded parameter: "+paramValue.getClass()+" for "+paramValue);
                }

                // If no exception thrown, input was correct, so removing tooltip
                maxEquivRange.setTooltip(null); // If no error, remove tooltip
            } catch (Exception e) {
                // Input wasn't correct
                maxEquivRange.getStyleClass().add("bad-input");
                LOGGER.warn(e.getMessage());
            }
        });


        // Compute boxes size
        final double width = new Text(paramValue.getValue().toString()+"___")
                .getLayoutBounds().getWidth() // This big is the Text in the TextField (+ 3 char length)
                + minEquivRange.getPadding().getLeft() + minEquivRange.getPadding().getRight(); // Add the padding of the TextField

        minEquivRange.setMaxWidth(width);
        maxEquivRange.setMaxWidth(width);

        // Adding text fields to pane
        generalizationPane.getChildren().add(minEquivRange);
        generalizationPane.getChildren().add(new Label(" - "));
        generalizationPane.getChildren().add(maxEquivRange);

        // GridPane.setColumnIndex(markedUtil, 0); // Already done by superclass
        // GridPane.setColumnIndex(levelMark, 1);
        // GridPane.setColumnIndex(this.paramTitle, 2);
        // GridPane.setColumnIndex(this.paramValue, 3);
        GridPane.setColumnIndex(generalizationPane, 4);

    }

    @Override
    protected void onClickMarkedUtil(ActionEvent event) {
        super.onClickMarkedUtil(event);

        boolean b = markedUtil.isSelected();
        // if unchecked, so disable component edition
        for(Node n : generalizationPane.getChildren())
            n.setDisable(!b);
    }

    @Override
    public Set<Node> getElements() {
        Set<Node> elts = super.getElements();
        elts.add(generalizationPane);

        return elts;
    }
}
