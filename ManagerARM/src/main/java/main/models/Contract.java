package main.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contract
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @OneToOne
    @JoinColumn(name = "request_id")
    @Getter
    @Setter
    private Request request;

    @Column(name = "signing_date")
    @Getter
    @Setter
    private Date signingDate;

    @Column(name = "signing_status")
    @Getter
    @Setter
    private boolean signingStatus;
}
