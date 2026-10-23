package com.pharmacy.inventory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String department;
    private String phone;
    private boolean active = true;
    private LocalDateTime createdAt;

    public User() {}

    public User(Long id, String email, String password, String fullName, Role role,
                String department, String phone, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.department = department;
        this.phone = phone;
        this.active = active;
        this.createdAt = createdAt;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String email;
        private String password;
        private String fullName;
        private Role role;
        private String department;
        private String phone;
        private boolean active = true;
        private LocalDateTime createdAt;

        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public UserBuilder role(Role role) { this.role = role; return this; }
        public UserBuilder department(String department) { this.department = department; return this; }
        public UserBuilder phone(String phone) { this.phone = phone; return this; }
        public UserBuilder active(boolean active) { this.active = active; return this; }
        public UserBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public User build() {
            return new User(id, email, password, fullName, role, department, phone, active, createdAt);
        }
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
