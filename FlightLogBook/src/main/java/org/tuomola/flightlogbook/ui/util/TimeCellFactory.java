package org.tuomola.flightlogbook.ui.util;

import java.time.Instant;
import javafx.scene.control.TableCell;

/**
 * Cell factory for formatting cells with Instant values. 
 * @author ptuomola
 */
public class TimeCellFactory  {

    public static <T> TableCell<T, Instant> createCell() {

        TableCell<T, Instant> cell = new TableCell<T, Instant>() {
            @Override
            protected void updateItem(Instant item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(FormatHelper.formatTime(item));
                }
            }
        };
        
        return cell;
    }        
}
