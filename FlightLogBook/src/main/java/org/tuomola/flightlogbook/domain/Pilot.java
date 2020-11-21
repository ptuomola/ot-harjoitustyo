package org.tuomola.flightlogbook.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ptuomola
 */

@Entity
public class Pilot {
    
    @Id @GeneratedValue
    private int id;

    private String userName;
    private String email;
    private String fullName;
    private Date dateOfBirth;
    
    @OneToMany(fetch=FetchType.EAGER)
    private List<License> qualifications; 

    public Pilot() {
    }
    
    public Pilot(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<License> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<License> qualifications) {
        this.qualifications = qualifications;
    }

    @Override
    public String toString() {
        return "Pilot{" + "userName=" + userName + ", email=" + email + ", fullName=" + fullName + ", dateOfBirth=" + dateOfBirth + ", qualifications=" + qualifications + '}';
    }
    
    
}
