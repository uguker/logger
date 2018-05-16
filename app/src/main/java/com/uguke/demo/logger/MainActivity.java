package com.uguke.demo.logger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.uguke.logger.Logger;
import com.uguke.logger.adapter.LogcatAdapter;
import com.uguke.logger.constant.Language;
import com.uguke.logger.constant.Level;
import com.uguke.logger.constant.Table;
import com.uguke.logger.strategy.LogcatStrategy;
import com.uguke.logger.strategy.FormatStrategy;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Logger.e("这是一个简单的日志");
//        Logger.t("测试").e("这是一个简单的日志");  //"测试"表示当前Log的标识为"测试"
//        Logger.t(3).e("这是一个简单的日志");    //3表示方法层数返回3层
//        Logger.t("测试2", 2).e("这是一个简单的日志"); //2表示方法层数返回2层
//
//        Config config = new Config();       //方式一
//
//        config = Logger.getConfig();        //方式二
//
//        config.setTable(Table.SOLID)        //表格样式
//                //.setTable(new Table("┏", "┓", "┗", "┛", "┣", "┫", "┃", "━", "━", "\u3000\u3000", 50))
//                //自定义Table
//                //.setSavePath(savepath)    //本地保存的地址
//                .setMethodCount(0)          //方法层数
//                .setLineLength(60)          //每行日志可显示的最大长度为30（范围为：30~300）
//                .setLevel(Level.ERROR)      //LOG等级,设置后只显示Error及之后等级的LOG
//                //.setLevel(2)              //LOG等级,作用同上
//                .setOpenLogger(true)        //设置开启或关闭日志打印，true打开，不控制本地保存日志
//                .setSaveLocal(true)         //设置开启或关闭本地保存日志，true打开
//                .setTag("全局TAG")           //设置全局TAG;
//                .setShowThread(false)       //设置是否显示线程信息，true为显示
//                .setShowTime(false);     //设置是否显示日期，true为显示
//        Logger.setConfig(config);
//        Logger.t("当前TAG").e("配置之后");
//        Logger.t("当前TAG").e("配置之后%d", 10);

        FormatStrategy strategy = LogcatStrategy.newBuilder()
                //.showThread(false)
                .maxLength(40)
                .methodCount(2)
                .methodOffset(1)
                .level(Level.VERBOSE)
                .language(Language.CN)
                .table(Table.DOUBLE)
                .tag("你好")
                .build();
        //PrinterImp p = new PrinterImp(new AndroidAdapter(config));
        Logger.addLogAdapter(new LogcatAdapter(strategy));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Logger.e( "你是傻逼\n你是傻逼\n你是傻你是傻逼你你你是傻逼你是傻逼你是傻逼你是傻逼你是傻逼你是傻逼是傻逼你是傻逼你是傻逼你是傻逼是傻逼你是傻逼你是傻逼逼\n你是傻逼\n你是傻逼\n你是傻逼\n%s", "SS");

            }
        }).start();

//        Map map = new HashMap();
//        map.put(null,0);
//        map.put(0,null);
//        Log.e("数据", "" + map.containsKey(null));
//        //map.remove(null);
//        Log.e("数据", "" + map.containsKey(null));
//        Logger.e(map);
//
//        List<Integer> list = new ArrayList<>();
//        list.add(null);
//        list.add(0);
//        list.add(1);
//        list.add(null);
//        list.add(2);
//        Logger.e(list);
//        int [] i = new int[2];
//        //Logger.e(i);
//        SparseIntArray array;
//        SparseArray<String> arrays = new SparseArray<>();
//        arrays.put(0, null);
//        arrays.put(1, "a");
//        Logger.e(arrays);
//
//
//        int [] a = new int[] {1,2,300};
//        Logger.e(a);
//
//        boolean [] aa = new boolean[]{true,true,true};
//        Logger.e(aa);
   }
}
