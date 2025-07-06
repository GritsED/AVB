package org.example.companies.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.companies.dto.CompanyFullDto;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.service.CompanyService;
import org.example.dto.CompanyShortDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public CompanyFullDto addCompany(@RequestBody @Valid NewCompanyDto newCompanyDto) {
        log.info("Add company: {}", newCompanyDto);
        return companyService.addCompany(newCompanyDto);
    }

    @GetMapping("/{companyId}")
    public CompanyFullDto getCompany(@PathVariable Long companyId) {
        log.info("Get company with id: {}", companyId);
        return companyService.getCompany(companyId);
    }

    @GetMapping
    public List<CompanyFullDto> getAllCompanies(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("Get all companies with params: from {}, size {}", from, size);
        return companyService.getAllCompanies(from, size);
    }

    @GetMapping("/internal/{companyId}")
    public CompanyShortDto getCompanyShort(@PathVariable Long companyId) {
        log.info("[Internal] Get company with id: {}", companyId);
        return companyService.getCompanyShort(companyId);
    }

    @GetMapping("/internal")
    public List<CompanyShortDto> getCompaniesByIdsShort(@RequestParam("companyIds") List<Long> companyIds) {
        log.info("[Internal] Get companies by ids: {}", companyIds);
        return companyService.getCompaniesByIds(companyIds);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        log.info("Delete company with id: {}", companyId);
        companyService.deleteCompany(companyId);
    }

    @PatchMapping("/{companyId}")
    public CompanyFullDto updateCompany(@PathVariable Long companyId,
                                        @RequestBody UpdateCompanyDto updateCompanyDto) {
        log.info("Received update request for company {} with fields: {}", companyId, updateCompanyDto);
        return companyService.updateCompany(companyId, updateCompanyDto);
    }
}
