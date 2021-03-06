package org.tuomola.flightlogbook.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Domain object representing a License.
 * @author ptuomola
 */

@Data
@Entity
public class License {

    @Id @GeneratedValue
    @EqualsAndHashCode.Exclude private int id;

    @Column(nullable = false)
    private String rating;
    
    private Date issueDate;
    private Date expiryDate;
}
