package com.plexsalud.plexsalud.user.domain.models;

import java.util.UUID;

public class User {

        private UUID uuid;
        private String email;
        private String password;
        private Role role;

        public UUID getUuid() {
                return uuid;
        }

        public User setUuid(UUID uuid) {
                this.uuid = uuid;
                return this;
        }

        public String getEmail() {
                return email;
        }

        public User setEmail(String email) {
                this.email = email;
                return this;
        }

        public String getPassword() {
                return password;
        }

        public User setPassword(String password) {
                this.password = password;
                return this;
        }

        public Role getRole() {
                return role;
        }

        public User setRole(Role role) {
                this.role = role;
                return this;
        }
}
