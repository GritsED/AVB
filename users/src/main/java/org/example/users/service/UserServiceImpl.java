package org.example.users.service;

import lombok.RequiredArgsConstructor;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserDto;
import org.example.users.exception.ConflictException;
import org.example.users.exception.NotFoundException;
import org.example.users.mapper.UserMapper;
import org.example.users.model.User;
import org.example.users.repository.UserRepository;
import org.example.users.util.PhoneNormalizer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto addUser(NewUserDto newUserDto) {
        String phone = newUserDto.getPhone();
        String normalizedPhone = checkPhone(phone);

        User entity = userMapper.toEntity(newUserDto);
        entity.setPhone(normalizedPhone);

        userRepository.save(entity);
        return userMapper.toDto(entity);
    }

    @Override
    public List<UserDto> getAllUsers(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        List<User> users = userRepository.findAll(pageable).getContent();

        return users.stream().map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = getUserOrThrow(id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UserDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = getUserOrThrow(id);

        String phone = updateUserDto.getPhone();
        if (phone != null && !phone.isEmpty()) {
            String normalizedPhone = checkPhone(phone);
            if (!normalizedPhone.equals(user.getPhone())) {
                user.setPhone(normalizedPhone);
            }
        }

//        Company company = null;
        if (updateUserDto.getCompanyId() != null) {
            /**
             *  проверка на существование компании или 404
             */
        }

        userMapper.updateUser(user, updateUserDto); // еще добавить компанию для маппинга
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        getUserOrThrow(id);
        userRepository.deleteById(id);
    }

    private String checkPhone(String phone) {
        String normalizedPhone = PhoneNormalizer.normalize(phone, "RU");
        if (userRepository.existsByPhone(phone)) {
            throw new ConflictException("Phone is already in use");
        }
        return normalizedPhone;
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }
}
