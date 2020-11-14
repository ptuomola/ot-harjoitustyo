package org.tuomola.flightlogbook.domain;

import java.util.Date;

/**
 *
 * @author ptuomola
 */
public class License {
    private String rating;
    private Date issueDate;
    private Date expiryDate;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


}
