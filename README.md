# logger
日志打印工具，可以打印出漂亮的日志，打印漂亮的Json,可保存日志到本地。
支持打印数据类型Collection、Map、Array、String

## 导入
1. 在build.gradle添加如下代码：<br>
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
2. 添加依赖关系
```
dependencies {
	implementation 'com.github.uguker:logger:2.0.0'
}
```
## 简单使用
1. 使用一
```
Logger.e("这是一个简单的日志");
```
<img src="https://github.com/uguker/logger/blob/master/screenshot/loge_1.png" height="160"><br><br>
2. 使用二
```
Logger.t("测试").e("这是一个简单的日志");  //"测试"表示当前Log的标识为"测试"
```
<img src="https://github.com/uguker/logger/blob/master/screenshot/loge_2.png" height="160"><br><br>
3. 使用三
```
Logger.t(3).e("这是一个简单的日志");    //3表示方法层数返回3层
```
<img src="https://github.com/uguker/logger/blob/master/screenshot/loge_3.png" height="200"><br><br>
4. 使用四
```
Logger.t("测试2", 2).e("这是一个简单的日志"); //2表示方法层数返回2层
```
<img src="https://github.com/uguker/logger/blob/master/screenshot/loge_4.png" height="180"><br>
## 配置
```
FormatStrategy strategy = new LogcatStrategy.Builder()
                .hasThread(false)       // 是否显示线程信息
                .maxLength(60)          // 每行日志可显示的最大长度
                .methodCount(2)         // 方法层数
                .methodOffset(1)        // 方法偏移
                .level(Level.D)         // 日志等级
                .langCn()                // 日志信息语言
                .tag("你好")            // 全局Tag
                .build();
Logger.addLogAdapter(new LogcatAdapter(strategy));
```
