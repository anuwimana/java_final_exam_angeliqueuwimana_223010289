package com.hms;

public class RoomModel {
    private int roomID;
    private String name;
    private String description;
    private String category;
    private double priceOrValue;
    private String status;

    public RoomModel(int roomID, String name, String description, String category, double priceOrValue, String status) {
        this.roomID = roomID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.priceOrValue = priceOrValue;
        this.status = status;
    }

    // Getters and setters
    public int getRoomID() { return roomID; }
    public void setRoomID(int roomID) { this.roomID = roomID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPriceOrValue() { return priceOrValue; }
    public void setPriceOrValue(double priceOrValue) { this.priceOrValue = priceOrValue; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
