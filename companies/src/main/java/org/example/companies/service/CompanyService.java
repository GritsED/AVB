package org.example.companies.service;

import org.example.companies.dto.CompanyFullDto;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.dto.CompanyShortDto;

import java.util.List;

public interface CompanyService {
    CompanyFullDto addCompany(NewCompanyDto newCompanyDto);

    CompanyFullDto getCompany(Long id);

    void deleteCompany(Long id);

    List<CompanyFullDto> getAllCompanies(Integer from, Integer size);

    CompanyFullDto updateCompany(Long id, UpdateCompanyDto updateCompanyDto);

    List<CompanyShortDto> getCompaniesByIds(List<Long> companyIds);

    CompanyShortDto getCompanyShort(Long companyId);
}
