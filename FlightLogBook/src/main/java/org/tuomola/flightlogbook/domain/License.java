package org.tuomola.flightlogbook.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author ptuomola
 */

@Data
@Entity
public class License {

    @Id @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String rating;
    
    private Date issueDate;
    private Date expiryDate;
}
