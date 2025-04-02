package com.example.wishlist.model;

public class Admin {
    private String adminName;
    private int adminId;
    private String adminPassword;
    private String adminEmail;
    private int userId;

    // No-arg constructor
    public Admin() {}

    // Constructor with all fields
    public Admin(String adminName, int adminId, String adminPassword, String adminEmail, int userId) {
        this.adminName = adminName;
        this.adminId = adminId;
        this.adminPassword = adminPassword;
        this.adminEmail = adminEmail;
        this.userId = userId;
    }

    // Getters
    public String getAdminName() {
        return adminName;
    }

    public int getAdminId() {
        return adminId;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public int getUserId() {
        return userId;
    }

    // Setters
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminName='" + adminName + '\'' +
                ", adminId='" + adminId + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", userId=" + userId +
                '}';
    }
}