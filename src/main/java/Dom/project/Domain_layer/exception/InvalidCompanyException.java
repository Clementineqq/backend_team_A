package Dom.project.Domain_layer.exception;


public class InvalidCompanyException extends DomainException {

    private static final String ERROR_CODE = "INVALID_COMPANY";

    public InvalidCompanyException(String message) {
        super(message, ERROR_CODE);
    }

    public InvalidCompanyException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }

    public InvalidCompanyException(String message, String field, Object invalidValue) {
        super(message, ERROR_CODE, field, invalidValue);
    }

    public static InvalidCompanyException companyCannotBeNull() {
        return new InvalidCompanyException("Company cannot be null", "company", null);
    }

    public static InvalidCompanyException nameCannotBeEmpty() {
        return new InvalidCompanyException("Company name cannot be empty", "name", null);
    }

    public static InvalidCompanyException invalidNameLength() {
        return new InvalidCompanyException("Company name must be between 2 and 255 characters", "name", null);
    }

    public static InvalidCompanyException innCannotBeEmpty() {
        return new InvalidCompanyException("INN cannot be empty", "inn", null);
    }

    public static InvalidCompanyException invalidInnFormat(String inn) {
        return new InvalidCompanyException(
                "Invalid INN format: " + inn + ". INN must be 10 digits for legal entities or 12 digits for individual entrepreneurs",
                "inn", inn);
    }

    public static InvalidCompanyException invalidKppFormat(String kpp) {
        return new InvalidCompanyException(
                "Invalid KPP format: " + kpp + ". KPP must be 9 digits",
                "kpp", kpp);
    }

    public static InvalidCompanyException addressCannotBeNull() {
        return new InvalidCompanyException("Legal address cannot be null", "legalAddress", null);
    }

    public static InvalidCompanyException invalidEmailFormat(String email) {
        return new InvalidCompanyException("Invalid email format: " + email, "email", email);
    }

    public static InvalidCompanyException companyWithInnAlreadyExists(String inn) {
        return new InvalidCompanyException("Company with INN " + inn + " already exists", "inn", inn);
    }

    public static InvalidCompanyException companyWithEmailAlreadyExists(String email) {
        return new InvalidCompanyException("Company with email " + email + " already exists", "email", email);
    }

    public static InvalidCompanyException companyNotFound(Long id) {
        return new InvalidCompanyException("Company not found with id: " + id, "id", id);
    }

    public static InvalidCompanyException companyNotFoundByInn(String inn) {
        return new InvalidCompanyException("Company not found with INN: " + inn, "inn", inn);
    }

    public static InvalidCompanyException kppRequiredForLegalEntity() {
        return new InvalidCompanyException("KPP is required for legal entities", "kpp", null);
    }

    public static InvalidCompanyException incompleteCompanyDetails() {
        return new InvalidCompanyException("Company details are incomplete. Name, INN and Legal Address are required", null, null);
    }
}