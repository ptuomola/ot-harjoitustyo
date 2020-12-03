package org.tuomola.flightlogbook.ui.util;

import java.util.Date;
import javafx.scene.control.TableCell;

/**
 *
 * @author ptuomola
 */
public class DateCellFactory  {

    public static <T> TableCell<T, Date> createCell() {

        TableCell<T, Date> cell = new TableCell<T, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) {
                    setText(null);
                }
                else {
                    setText(FormatHelper.formatDate(item));
                }
            }
        };
        
        return cell;
    }        
}
