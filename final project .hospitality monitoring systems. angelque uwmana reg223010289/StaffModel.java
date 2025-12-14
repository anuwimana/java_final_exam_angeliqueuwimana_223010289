
package com.hms;

import java.util.Date;

public class StaffModel {
    private int staffID;
    private String name;
    private String identifier;
    private String status;
    private String location;
    private String contact;
    private Date assignedSince;

    public StaffModel(int staffID, String name, String identifier, String status, String location, String contact, Date assignedSince) {
        this.staffID = staffID;
        this.name = name;
        this.identifier = identifier;
        this.status = status;
        this.location = location;
        this.contact = contact;
        this.assignedSince = assignedSince;
    }

    // Getters and setters
    public int getStaffID() { return staffID; }
    public void setStaffID(int staffID) { this.staffID = staffID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIdentifier() { return identifier; }
    public void setIdentifier(String identifier) { this.identifier = identifier; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public Date getAssignedSince() { return assignedSince; }
    public void setAssignedSince(Date assignedSince) { this.assignedSince = assignedSince; }
}