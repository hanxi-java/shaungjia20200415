package shuangjia.shuangjia.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
*@Author
*@Description  设备类
 *
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class Device {
    //order,排序用，对应device表order字段
    private  int id;

    private  String code;

    private String name;

    private  String remark;

    private  int departId;

    //设备ID，对应device表ID
    private  int deviceId;
    //private List<DeviceProperty> DeviceProperty;
    private  int parentId;

    private List<DataType> dataTypeList;

    private List<Devicetypedatamodel> devicetypedatamodel;

    public List<DataType> getDataTypeList() {
        return dataTypeList;
    }




    public void setDataTypeList(List<DataType> dataTypeList) {
        this.dataTypeList = dataTypeList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }
}
