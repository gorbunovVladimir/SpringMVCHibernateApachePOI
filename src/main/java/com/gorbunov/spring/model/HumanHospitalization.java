package com.gorbunov.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import org.hibernate.annotations.Type;
import java.util.UUID;

/**
 * Created by Vl on 12.11.2016.
 */
@Entity
@Table(name="human_bed")
public class HumanHospitalization {

    @Id
    @Column(name="id_human_bed", updatable=false, unique=true, nullable=false)
    //@Type(type="pg-uuid")
    // private UUID id;
    @SequenceGenerator(name="human_bed_seq", sequenceName="human_bed_id_human_bed_seq",
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="human_bed_seq")
    private Integer id;

    @NotNull//@Size(min=2)
    @Column(name="name_human")
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    private Date bdate;

    @Column
    @Temporal (TemporalType.DATE)
    private Date edate;

    @Transient
    private Integer bedNumber;

    public HumanHospitalization() {
    }

    @Override
    public String toString() {
        return "HumanHospitalization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bdate=" + bdate +
                ", edate=" + edate +
                '}';
    }

    /*public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBdate() {
        return bdate;
    }

    public void setBdate(Date bdate) {
        this.bdate = bdate;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }
}
