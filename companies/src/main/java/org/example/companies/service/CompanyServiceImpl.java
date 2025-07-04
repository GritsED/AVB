package org.example.companies.service;

import lombok.RequiredArgsConstructor;
import org.example.companies.client.UsersClient;
import org.example.companies.dto.CompanyFullDto;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.mapper.CompanyMapper;
import org.example.companies.model.Company;
import org.example.companies.repository.CompanyRepository;
import org.example.dto.CompanyShortDto;
import org.example.dto.UserShortDto;
import org.example.exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UsersClient usersClient;

    @Override
    @Transactional
    public CompanyFullDto addCompany(NewCompanyDto newCompanyDto) {
        Company company = companyMapper.toEntity(newCompanyDto);
        Company save = companyRepository.save(company);

        return companyMapper.toDto(company, Collections.emptyList());
    }

    @Override
    public CompanyFullDto getCompany(Long id) {
        Company company = getCompanyOrThrow(id);
        List<UserShortDto> users = getUsersByCompanyId(id);
        return companyMapper.toDto(company, users);
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        getCompanyOrThrow(id);
        companyRepository.deleteById(id);
    }

    @Override
    public List<CompanyFullDto> getAllCompanies(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        List<Company> companyList = companyRepository.findAll(pageable).getContent();

        if (companyList.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> companyIds = companyList.stream()
                .map(Company::getId)
                .toList();

        List<UserShortDto> users = usersClient.getUsersByCompanyIds(companyIds, from, size);
        Map<Long, List<UserShortDto>> usersMap = users.stream().collect(Collectors.groupingBy(UserShortDto::getCompanyId));
        return companyList.stream().map(company -> {
                    List<UserShortDto> usersForCompany = usersMap.getOrDefault(company.getId(), Collections.emptyList());
                    return companyMapper.toDto(company, usersForCompany);
                })
                .toList();
    }

    @Override
    @Transactional
    public CompanyFullDto updateCompany(Long id, UpdateCompanyDto updateCompanyDto) {
        Company company = getCompanyOrThrow(id);
        companyMapper.updateCompany(company, updateCompanyDto);
        companyRepository.save(company);
        List<UserShortDto> users = getUsersByCompanyId(id);
        return companyMapper.toDto(company, users);
    }

    @Override
    public List<CompanyShortDto> getCompaniesByIds(List<Long> companyIds) {
        List<Company> companies = companyRepository.findAllById(companyIds);
        return companies.stream()
                .map(companyMapper::toShortDto)
                .toList();
    }

    @Override
    public CompanyShortDto getCompanyShort(Long companyId) {
        Company company = getCompanyOrThrow(companyId);
        return companyMapper.toShortDto(company);
    }

    private List<UserShortDto> getUsersByCompanyId(Long id) {
        return usersClient.getUsersByCompanyId(id, 0, 10);
    }

    private Company getCompanyOrThrow(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Company.class, id));
    }
}
