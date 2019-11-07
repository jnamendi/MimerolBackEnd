package bmbsoft.orderfoodonline.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_fcm")
public class UserFCM implements java.io.Serializable {
    private Long userFCMId;
    private Long userId;
    private String deviceId;
    private String token;
    private Date createdDate;
    private Date modifiedDate;

    public UserFCM() {
    }

    public UserFCM(Long userFCMId) {
        this.userFCMId = userFCMId;
    }

    public UserFCM(Long userFCMId, Long userId, String deviceId, String token, Date createdDate, Date modifiedDate) {
        this.userFCMId = userFCMId;
        this.userId = userId;
        this.deviceId = deviceId;
        this.token = token;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_fcm_id", unique = true, nullable = false)
    public Long getUserFCMId() {
        return userFCMId;
    }

    public void setUserFCMId(Long userFCMId) {
        this.userFCMId = userFCMId;
    }

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "device_id")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Column(name = "created_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modified_date", length = 19)
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
