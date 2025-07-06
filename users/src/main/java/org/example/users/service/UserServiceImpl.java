package org.example.users.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyShortDto;
import org.example.dto.UserShortDto;
import org.example.exception.ConflictException;
import org.example.exception.NotFoundException;
import org.example.users.client.CompaniesClient;
import org.example.users.dto.NewUserDto;
import org.example.users.dto.UpdateUserDto;
import org.example.users.dto.UserFullDto;
import org.example.users.mapper.UserMapper;
import org.example.users.model.User;
import org.example.users.repository.UserRepository;
import org.example.users.util.PhoneNormalizer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final CompaniesClient companiesClient;

    @Override
    @Transactional
    public UserFullDto addUser(NewUserDto newUserDto) {
        String phone = newUserDto.getPhone();
        String normalizedPhone = checkPhone(phone);

        User entity = userMapper.toEntity(newUserDto);
        entity.setPhone(normalizedPhone);

        userRepository.save(entity);

        Long companyId = newUserDto.getCompanyId();
        CompanyShortDto companyShort = getCompanyShort(companyId);

        return userMapper.toDto(entity, companyShort);
    }

    @Override
    public List<UserFullDto> getAllUsers(Integer from, Integer size) {
        Pageable pageable = getPageable(from, size);
        List<User> users = userRepository.findAll(pageable).getContent();

        List<Long> companyIds = users.stream()
                .map(User::getCompanyId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<CompanyShortDto> shortDtos = companiesClient.getCompaniesByIdsShort(companyIds);

        Map<Long, CompanyShortDto> shortDtoMap = shortDtos.stream()
                .collect(Collectors.toMap(CompanyShortDto::getId, Function.identity()));

        return userMapper.toDtoList(users, shortDtoMap);
    }

    @Override
    public UserFullDto getUserById(Long id) {
        User user = getUserOrThrow(id);
        CompanyShortDto company = getCompanyShort(user.getCompanyId());
        return userMapper.toDto(user, company);
    }

    @Override
    @Transactional
    public UserFullDto updateUser(Long id, UpdateUserDto updateUserDto) {
        User user = getUserOrThrow(id);

        String phone = updateUserDto.getPhone();
        if (phone != null && !phone.isEmpty()) {
            String normalizedPhone = checkPhone(phone);
            if (!normalizedPhone.equals(user.getPhone())) {
                user.setPhone(normalizedPhone);
            }
        }

        CompanyShortDto company = updateUserDto.getCompanyId() != null ? getCompanyShort(updateUserDto.getCompanyId())
                : getCompanyShort(user.getCompanyId());

        userMapper.updateUser(user, updateUserDto);
        userRepository.save(user);
        return userMapper.toDto(user, company);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        getUserOrThrow(id);
        userRepository.deleteById(id);
    }

    @Override
    public List<UserShortDto> getUsersByCompanyIds(List<Long> companyIds) {
        return getUserByCompanyIds(companyIds);
    }

    @Override
    public List<UserShortDto> getUsersByCompanyId(Long companyId) {
        return getUserByCompanyIds(List.of(companyId));
    }

    private List<UserShortDto> getUserByCompanyIds(List<Long> companyIds) {
        List<User> userList = userRepository.findAllByCompanyIdIn(companyIds);
        return userMapper.toDtos(userList);
    }

    private static PageRequest getPageable(Integer from, Integer size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    private CompanyShortDto getCompanyShort(Long companyId) {
        try {
            return companiesClient.getCompanyShort(companyId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException(CompanyShortDto.class, companyId);
        }
    }

    private String checkPhone(String phone) {
        String normalizedPhone = PhoneNormalizer.normalize(phone, "RU");
        if (userRepository.existsByPhone(normalizedPhone)) {
            throw new ConflictException("Phone is already in use");
        }
        return normalizedPhone;
    }

    private User getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }
}
