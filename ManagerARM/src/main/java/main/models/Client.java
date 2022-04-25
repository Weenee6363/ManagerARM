package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Column(name = "full_name")
    @Getter
    @Setter
    private String fullName;

    @Column(name = "passport_details")
    @Getter
    @Setter
    private String passportDetails;

    @Column(name = "marital_status")
    @Getter
    @Setter
    private String maritalStatus;

    @Column(name = "registration_address")
    @Getter
    @Setter
    private String registrationAddress;

    @Column(name = "phone_number")
    @Getter
    @Setter
    private String phoneNumber;

    @Column(name = "employment_information")
    @Getter
    @Setter
    private String employmentInformation;

}
