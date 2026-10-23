package com.pharmacy.inventory.dto;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long id;
    private String email;
    private String fullName;
    private String role;
    private String department;

    public AuthResponse() {}

    public AuthResponse(String token, String tokenType, Long id, String email, String fullName, String role, String department) {
        this.token = token;
        this.tokenType = (tokenType != null) ? tokenType : "Bearer";
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.department = department;
    }

    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }

    public static class AuthResponseBuilder {
        private String token;
        private String tokenType = "Bearer";
        private Long id;
        private String email;
        private String fullName;
        private String role;
        private String department;

        public AuthResponseBuilder token(String token) { this.token = token; return this; }
        public AuthResponseBuilder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public AuthResponseBuilder id(Long id) { this.id = id; return this; }
        public AuthResponseBuilder email(String email) { this.email = email; return this; }
        public AuthResponseBuilder fullName(String fullName) { this.fullName = fullName; return this; }
        public AuthResponseBuilder role(String role) { this.role = role; return this; }
        public AuthResponseBuilder department(String department) { this.department = department; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, id, email, fullName, role, department);
        }
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
