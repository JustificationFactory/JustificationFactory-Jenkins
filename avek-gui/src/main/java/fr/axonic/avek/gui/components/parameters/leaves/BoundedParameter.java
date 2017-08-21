package fr.axonic.avek.gui.components.parameters.leaves;

import fr.axonic.base.AContiniousDate;
import fr.axonic.base.AContinuousNumber;
import fr.axonic.base.engine.AVar;
import fr.axonic.base.engine.ContinuousAVar;
import fr.axonic.validation.exception.VerificationException;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nathaël N on 21/07/16.
 */
public class BoundedParameter extends SensitiveParameter {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoundedParameter.class);
    private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final HBox generalizationPane;
    final TextField minEquivRange;
    final TextField maxEquivRange;

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
        minEquivRange.textProperty().addListener((observable, oldVal, newVal) -> {
            try {
                // remove style of bad input, doing nothing if last value wasn't bad
                minEquivRange.getStyleClass().remove("bad-input");
                if(paramValue instanceof AContinuousNumber) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    minEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                    // Setting the minimum value of parameter (will throw exception if bad input)
                    //noinspection unchecked
                    paramValue.setMin((U) Double.valueOf(newVal));
                }
                else if(paramValue instanceof AContiniousDate) {
                    // Setting tooltip in case of error (will be removed if correct input)

                    minEquivRange.setTooltip(new Tooltip("DATE required\nexample: '31/12/2016 23:59:59'"));

                    // Setting the minimum value of parameter (will throw exception if bad input)
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat textFormat = new SimpleDateFormat(DATE_FORMAT);
                    cal.setTime(textFormat.parse(newVal));
                    //noinspection unchecked
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
                LOGGER.warn(e.getMessage()+" ("+minEquivRange.getText()+")");
            }
        });

        // When maximum (in text field) is edited by user
        // check-it and write it in red if value not respecting format
        // else save-it in parameter if is a correct value
        maxEquivRange.textProperty().addListener((observable, oldVal, newVal) -> {
            try {
                // remove style of bad input, doing nothing if last value wasn't bad
                maxEquivRange.getStyleClass().remove("bad-input");
                if(paramValue instanceof AContinuousNumber) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    maxEquivRange.setTooltip(new Tooltip("NUMBER required\nexample: '12345.6789'"));
                    // Setting the maximum value of parameter (will throw exception if bad input)
                    //noinspection unchecked
                    paramValue.setMax((U) Double.valueOf(newVal));
                }
                else if(paramValue instanceof AContiniousDate) {
                    // Setting tooltip in case of error (will be removed if correct input)
                    maxEquivRange.setTooltip(new Tooltip("DATE required\nexample: '31/12/2016 23:59:59'"));

                    // Setting the maximum value of parameter (will throw exception if bad input)
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat textFormat = new SimpleDateFormat(DATE_FORMAT);
                    cal.setTime(textFormat.parse(newVal));
                    //noinspection unchecked
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
                LOGGER.warn(e.getMessage()+" ("+maxEquivRange.getText()+")");
            }
        });


        // Compute boxes size
        final double width = new Text(minEquivRange.getText()+"___")
                .getLayoutBounds().getWidth() // This big is the Text in the TextField (+ 3 char length)
                + minEquivRange.getPadding().getLeft() + minEquivRange.getPadding().getRight(); // Add the padding of the TextField

        minEquivRange.setMaxWidth(width);
        maxEquivRange.setMaxWidth(width);
        minEquivRange.setMinWidth(width);
        maxEquivRange.setMinWidth(width);
        minEquivRange.setPrefWidth(width);
        maxEquivRange.setPrefWidth(width);

        // Adding text fields to pane
        generalizationPane.getChildren().add(minEquivRange);
        generalizationPane.getChildren().add(new Label(" - "));
        generalizationPane.getChildren().add(maxEquivRange);

        // GridPane.setColumnIndex(markedUtil, 0); // Done in superclass
        // GridPane.setColumnIndex(paneTitle, 1);
        // GridPane.setColumnIndex(separator, 2);
        // GridPane.setColumnIndex(paramValue, 3);
        GridPane.setColumnIndex(generalizationPane, 4);
    }

    @Override
    protected void setSelected(boolean selected) {
        super.setSelected(selected);

        boolean b = markedUtil.isSelected();
        // if unchecked, so disable component edition
        for(Node n : generalizationPane.getChildren())
            n.setDisable(!b);
    }

    @Override
    public List<Node> getNodeLine() {
        List<Node> list = super.getNodeLine();
        list.add(generalizationPane);
        return list;
    }
}
