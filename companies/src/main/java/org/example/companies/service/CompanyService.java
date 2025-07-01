package org.example.companies.service;

import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    CompanyDto addCompany(NewCompanyDto newCompanyDto);

    CompanyDto getCompany(Long id);

    void deleteCompany(Long id);

    List<CompanyDto> getAllCompanies(Integer from, Integer size);

    CompanyDto updateCompany(Long id, UpdateCompanyDto updateCompanyDto);
}
