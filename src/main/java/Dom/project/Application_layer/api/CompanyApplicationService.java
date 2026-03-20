package Dom.project.Application_layer.api;

import org.springframework.stereotype.Service;

import Dom.project.Web_layer.api.dto.CompanyProfileDto;

@Service   
public class CompanyApplicationService {
    
    // временные заглушки 
    public CompanyProfileDto getCompanyById(Long companyId) {
        return null;  // Или выбросить исключение
    }
    
    public CompanyProfileDto updateCompanyProfile(CompanyProfileDto profileDto) {
        return profileDto;
    }
}