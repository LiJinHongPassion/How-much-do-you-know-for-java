package 适配器模式;

import 适配器模式.适配器.CommonHeadsetAdapter;
import 适配器模式.适配者.CommonHeadset;

/**
 * @author lijinhong
 * @date 20.12.24
 */
public class Test {
    public static void main(String[] args) {
        CommonHeadset headset = new CommonHeadset();
        CommonHeadsetAdapter headsetAdapter = new CommonHeadsetAdapter(headset);
        headsetAdapter.useTypeCPort();
    }
}




