package my.demo.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
public class StatefulEntity extends BaseEntity {

    private static final long serialVersionUID = 8457026993523262967L;

    /**
     * 正常状态
     */
    public static final Integer NORMAL_STATUS = 1;

    /**
     * 删除状态
     */
    public static final Integer DELETE_STATUS = 0;

    /**
     * 临时状态
     */
    public static final Integer TEMP_STATUS = -1;

    /**
     * 状态
     */
    @Column(name = "status", length = 6)
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
