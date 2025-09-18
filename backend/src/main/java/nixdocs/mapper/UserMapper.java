package nixdocs.mapper;

import nixdocs.dto.UserDto;
import nixdocs.model.User;
import nixdocs.repository.UserEntity;

import java.util.List;
import java.util.UUID;

public class UserMapper {

    // Entity → Model
    public static User toModel(UserEntity entity) {
        return new User(
            new User.Id(entity.getId()),
            new User.Email(entity.getEmail()),
            new User.Name(entity.getName()),
            new User.Password(entity.getPassword())
        );
    }

    // Model → Entity
    public static UserEntity toEntity(User model) {
        return new UserEntity(
            model.id().value(),
            model.email().value(),
            model.name().value(),
            model.password().value()
        );
    }

    // DTO → Model
    public static User toModel(UserDto dto) {
        return new User(
            new User.Id(UUID.randomUUID()), // auto-generate ID
            new User.Email(dto.email()),
            new User.Name(dto.name()),
            new User.Password(dto.password())
        );
    }

    // Model → DTO
    static UserDto toDto(User user) {
        return new UserDto(
            user.id().value()
            , user.email().value(),
            user.name().value(),
            user.password().value()
        );
    }

    // DTO → Entity
    public static UserEntity toEntity(UserDto dto) {
        return new UserEntity(
            UUID.randomUUID(), // generamos ID aquí
            dto.email(),
            dto.name(),
            dto.password()
        );
    }

    // Entity → Model (list)
    public static List<User> toModelList(List<UserEntity> entities) {
        return entities.stream().map(UserMapper::toModel).toList();
    }

    public static List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                    .map(UserMapper::toDto)
                    .toList();
    }


}
