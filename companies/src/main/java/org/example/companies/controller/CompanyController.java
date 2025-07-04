package org.example.companies.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.companies.dto.CompanyFullDto;
import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.service.CompanyService;
import org.example.dto.CompanyShortDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public CompanyFullDto addCompany(@RequestBody @Valid NewCompanyDto newCompanyDto) {
        return companyService.addCompany(newCompanyDto);
    }

    @GetMapping("/{companyId}")
    public CompanyFullDto getCompany(@PathVariable Long companyId) {
        return companyService.getCompany(companyId);
    }

    @GetMapping
    public List<CompanyFullDto> getAllCompanies(@RequestParam(defaultValue = "0") @Min(0) Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return companyService.getAllCompanies(from, size);
    }

    @GetMapping("/internal/{companyId}")
    public CompanyShortDto getCompanyShort(@PathVariable Long companyId) {
        return companyService.getCompanyShort(companyId);
    }

    @GetMapping("/internal")
    public List<CompanyShortDto> getCompaniesByIdsShort(@RequestParam("companyIds") List<Long> companyIds) {
        return companyService.getCompaniesByIds(companyIds);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        companyService.deleteCompany(companyId);
    }

    @PatchMapping("/{companyId}")
    public CompanyFullDto updateCompany(@PathVariable Long companyId,
                                        @RequestBody UpdateCompanyDto updateCompanyDto) {
        return companyService.updateCompany(companyId, updateCompanyDto);
    }
}
