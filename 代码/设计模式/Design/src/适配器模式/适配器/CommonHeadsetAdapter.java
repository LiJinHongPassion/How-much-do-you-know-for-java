package 适配器模式.适配器;

import 适配器模式.目标.TypeC;
import 适配器模式.适配者.CommonHeadset;

/**
 * Adapter适配器 - 把适配对象和目标类关联起来，达到转换的目的
 */
public class CommonHeadsetAdapter implements TypeC {

    private CommonHeadset headset;

    public CommonHeadsetAdapter(CommonHeadset headset){
        this.headset = headset;
    }

    @Override
    public void useTypeCPort() {
        System.out.println("使用Type-C转接头");
        this.headset.listen();
    }
}