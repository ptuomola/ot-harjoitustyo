package org.tuomola.flightlogbook.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

/**
 *
 * @author ptuomola
 */

@Data
@Entity
public class Pilot {
    
    @Id @GeneratedValue
    private int id;

    private String userName;
    private String password;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    
    @OneToMany(fetch = FetchType.EAGER)
    private List<License> qualifications; 

    public String getCompositeName() {
        StringBuilder buf = new StringBuilder();
        buf.append(userName);
        
        if (fullName != null && !fullName.equals("")) {
            buf.append(" (");
            buf.append(fullName);
            buf.append(")");
        }
               
        return buf.toString();
    }
    
    
}
