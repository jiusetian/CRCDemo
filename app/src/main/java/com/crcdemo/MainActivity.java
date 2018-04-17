package com.crcdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.crcdemo.util.CrcOperateUtil;
import com.crcdemo.util.HexCompressWrap;

public class MainActivity extends AppCompatActivity {

    // 显示CRC校验的结果
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = (TextView) findViewById(R.id.resultText);

        // 测试CRC校验
        testCheckCRC();

    }

    /***
     * 测试为数组添加CRC校验
     */
    public byte[] testAddCRC()
    {
        byte[] id = HexCompressWrap.packHexCompress("123456", 3);

        byte[] number = HexCompressWrap.packHexCompress("12345678", 4);

        byte[] seqID = HexCompressWrap.packHexCompress("2016040506", 5);

        // 最终提交至服务器的byte数据，未添加crc校验
        byte[] submit = CrcOperateUtil.concatAll(id, number, seqID);

        // 给submit数组添加两位CRC校验
        submit = CrcOperateUtil.setParamCRC(submit);

        return submit;
    }

    /**
     * 对传输后数组进行CRC校验
     */
    public void testCheckCRC()
    {
        // 获取接收到的crc校验数组
        byte[] reByte = testAddCRC();

        //测试打印byte数组
        String str="&";
        byte[] testByte=str.getBytes();
        byte b= (byte) (testByte[0]<<1);
        //for (int i=0;i<reByte.length;i++){
            //bytes.append(reByte[i]);
            Log.d("tag", "数组= "+b);
       // }

        if (CrcOperateUtil.isPassCRC(reByte, 2))
        {
            resultText.setText("CRC校验通过");
        }
        else
        {
            resultText.setText("CRC校验失败");
        }
    }
}
