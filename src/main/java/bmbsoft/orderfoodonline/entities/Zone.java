package bmbsoft.orderfoodonline.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "zone", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class Zone implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "zone_id", unique = true, nullable = false)
    private Long zoneId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
    @Column(name = "name", length = 45)
    private String name;
    @Column(name = "code", unique = true, length = 45)
    private String code;
    @Column(name = "status")
    private Integer status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", length = 19)
    private Date createdDate;
    @Column(name = "created_by")
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", length = 19)
    private Date modifiedDate;
    @Column(name = "modified_by")
    private String modifiedBy;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
    private Set<Address> addresses = new HashSet<Address>(0);
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
    private Set<Restaurant> restaurants = new HashSet<Restaurant>(0);

    public Zone() {
    }

    public Zone(District district) {
        this.district = district;
    }

    public Zone(District district, String name, String code, Integer status, Date createdDate, String createdBy,
                    Date modifiedDate, String modifiedBy, Set<Address> addresses, Set<Restaurant> restaurants) {
        this.district = district;
        this.name = name;
        this.code = code;
        this.status = status;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.addresses = addresses;
        this.restaurants = restaurants;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }


    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }


    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
