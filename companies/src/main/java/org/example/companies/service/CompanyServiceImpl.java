package org.example.companies.service;

import lombok.RequiredArgsConstructor;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.mapper.CompanyMapper;
import org.example.companies.model.Company;
import org.example.companies.repository.CompanyRepository;
import org.example.dto.CompanyDto;
import org.example.exception.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    @Transactional
    public CompanyDto addCompany(NewCompanyDto newCompanyDto) {
        Company company = companyMapper.toEntity(newCompanyDto);
        Company save = companyRepository.save(company);

        /**
         * из юзер клиента подгрузить список сотрудников по айди компании и добавить его
         */
        return companyMapper.toDto(company, Collections.emptyList());
    }

    @Override
    public CompanyDto getCompany(Long id) {
        Company company = getCompanyOrThrow(id);
        /**
         * из юзер клиента подгрузить список сотрудников по айди компании и добавить его
         */
        return companyMapper.toDto(company, Collections.emptyList());
    }

    @Override
    @Transactional
    public void deleteCompany(Long id) {
        getCompanyOrThrow(id);
        companyRepository.deleteById(id);
    }

    @Override
    public List<CompanyDto> getAllCompanies(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from > 0 ? from / size : 0, size);
        /**
         * добавить юзеров
         */
        List<Company> companyList = companyRepository.findAll(pageable).getContent();
        return companyList.stream().map(companyMapper::toDto).toList();
    }

    @Override
    @Transactional
    public CompanyDto updateCompany(Long id, UpdateCompanyDto updateCompanyDto) {
        Company company = getCompanyOrThrow(id);
        /**
         * добавить юзеров
         */
        companyMapper.updateCompany(company, updateCompanyDto);
        companyRepository.save(company);
        return companyMapper.toDto(company);
    }

    private Company getCompanyOrThrow(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Company.class, id));
    }
}
