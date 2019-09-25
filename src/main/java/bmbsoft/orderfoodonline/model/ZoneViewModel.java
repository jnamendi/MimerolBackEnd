package bmbsoft.orderfoodonline.model;

import java.sql.Date;

public class ZoneViewModel {
    private Long zoneId;
    private DistrictViewModel district;
    private String name;
    private String code;
    private Integer status;
    private String createdBy;
    private Date createDate;

    public ZoneViewModel() {
    }

    public ZoneViewModel(Long zoneId, DistrictViewModel district, String name, String code, Integer status, String createdBy, Date createDate) {
        this.zoneId = zoneId;
        this.district = district;
        this.name = name;
        this.code = code;
        this.status = status;
        this.createdBy = createdBy;
        this.createDate = createDate;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public DistrictViewModel getDistrict() {
        return district;
    }

    public void setDistrict(DistrictViewModel district) {
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
