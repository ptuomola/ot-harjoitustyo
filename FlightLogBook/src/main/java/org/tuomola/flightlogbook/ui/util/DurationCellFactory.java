package org.tuomola.flightlogbook.ui.util;

import java.time.Duration;
import javafx.scene.control.TableCell;

/**
 * Cell factory for formatting cells with Duration values. 
 * @author ptuomola
 */
public class DurationCellFactory  {

    public static <T> TableCell<T, Duration> createCell() {

        TableCell<T, Duration> cell = new TableCell<T, Duration>() {
            @Override
            protected void updateItem(Duration item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(FormatHelper.formatDuration(item));
                }
            }
        };
        
        return cell;
    }        
}
