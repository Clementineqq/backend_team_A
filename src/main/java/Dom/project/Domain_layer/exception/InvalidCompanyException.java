package Dom.project.Domain_layer.exception;

public class InvalidCompanyException extends RuntimeException {

    public InvalidCompanyException(String message) {
        super(message);
    }

    public static InvalidCompanyException companyCannotBeNull() {
        return new InvalidCompanyException("Company cannot be null");
    }

    public static InvalidCompanyException nameCannotBeEmpty() {
        return new InvalidCompanyException("Company name cannot be empty");
    }

    public static InvalidCompanyException innCannotBeEmpty() {
        return new InvalidCompanyException("INN cannot be empty");
    }

    public static InvalidCompanyException invalidInnFormat(String inn) {
        return new InvalidCompanyException("Invalid INN format: " + inn +
                ". INN must be 10 digits for legal entities or 12 digits for individual entrepreneurs");
    }

    public static InvalidCompanyException invalidKppFormat(String kpp) {
        return new InvalidCompanyException("Invalid KPP format: " + kpp +
                ". KPP must be 9 digits");
    }

    public static InvalidCompanyException addressCannotBeEmpty() {
        return new InvalidCompanyException("Legal address cannot be empty");
    }

    public static InvalidCompanyException invalidEmailFormat(String email) {
        return new InvalidCompanyException("Invalid email format: " + email);
    }

    public static InvalidCompanyException companyWithInnAlreadyExists(String inn) {
        return new InvalidCompanyException("Company with INN " + inn + " already exists");
    }

    public static InvalidCompanyException companyWithEmailAlreadyExists(String email) {
        return new InvalidCompanyException("Company with email " + email + " already exists");
    }

    public static InvalidCompanyException companyNotFound(Long id) {
        return new InvalidCompanyException("Company not found with id: " + id);
    }

    public static InvalidCompanyException companyNotFoundByInn(String inn) {
        return new InvalidCompanyException("Company not found with INN: " + inn);
    }

    public static InvalidCompanyException kppRequiredForLegalEntity() {
        return new InvalidCompanyException("KPP is required for legal entities");
    }

    public static InvalidCompanyException incompleteCompanyDetails() {
        return new InvalidCompanyException("Company details are incomplete");
    }
}
