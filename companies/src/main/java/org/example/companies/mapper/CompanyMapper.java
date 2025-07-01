package org.example.companies.mapper;

import org.example.companies.dto.NewCompanyDto;
import org.example.companies.dto.UpdateCompanyDto;
import org.example.companies.model.Company;
import org.example.dto.CompanyDto;
import org.example.dto.UserDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Mapping(source = "company.id", target = "id")
    @Mapping(source = "company.name", target = "name")
    @Mapping(source = "company.budget", target = "budget")
    @Mapping(source = "users", target = "users")
    CompanyDto toDto(Company company, List<UserDto> users);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "budget", target = "budget")
    CompanyDto toDto(Company company);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "budget", target = "budget")
    Company toEntity(NewCompanyDto newCompanyDto);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCompany(@MappingTarget Company company, UpdateCompanyDto updateCompanyDto);
}
