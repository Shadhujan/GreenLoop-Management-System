package model;

public class Client {

    private String clientId;
    private String clientName;
    private String businessName;
    private String phone;
    private String email;
    private String address;

    public Client(String clientId, String clientName, String businessName, String phone, String email, String address) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.businessName = businessName;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }
}