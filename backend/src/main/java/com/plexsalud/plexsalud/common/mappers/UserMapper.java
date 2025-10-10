package com.plexsalud.plexsalud.common.mappers;

import com.plexsalud.plexsalud.user.domain.models.User;
import com.plexsalud.plexsalud.user.infrastructure.persistance.entities.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null)
            return null;

        return new User()
                .setUuid(entity.getUuid())
                .setEmail(entity.getUsername())
                .setPassword(entity.getPassword())
                .setRole(entity.getRole());
    }

    public static UserEntity toEntity(User user) {
        if (user == null)
            return null;

        return new UserEntity()
                .setUuid(user.getUuid())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .setRole(user.getRole());
    }
}
