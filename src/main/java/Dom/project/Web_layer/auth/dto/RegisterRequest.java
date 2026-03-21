package Dom.project.Web_layer.auth.dto;

import Dom.project.Domain_layer.model.Address;

public class RegisterRequest {
    private String email;
    private String name;
    private String surname;
    private String phone;
    private String password;
    private String id_company;
    private AddressDto address;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }

    public AddressDto getAddress() {
        return address;
    }

    public String getId_company() {
        return id_company;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId_company(String id_company) {
        this.id_company = id_company;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

}