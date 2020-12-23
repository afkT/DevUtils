package afkt.project.model.bean;

/**
 * detail: ACV 文件实体类
 * @author Ttt
 */
public class ACVFileBean {

    // ACV 名
    public String acvName;
    // 文件地址
    public String acvPath;

    public ACVFileBean(
            String acvName,
            String acvPath
    ) {
        this.acvName = acvName;
        this.acvPath = acvPath;
    }
}