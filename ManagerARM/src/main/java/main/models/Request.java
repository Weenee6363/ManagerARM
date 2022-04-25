package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Request
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Getter
    @Setter
    private Client client;

    @Column(name = "request_status")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private RequestStatus requestStatus;

    @Column
    @Getter
    @Setter
    private int period;

    @Column(name = "credit_amount")
    @Getter
    @Setter
    private int creditAmount;

}
