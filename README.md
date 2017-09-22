
#   @Deprecated
# 现在编译Uiautomator可以使用androidStudio 我这个Eclipse插件的方式跟AndroidStudio比已经没有任何优势了。


# Uiautomator_Eclispe_Build_Run_plugins 插件安装和配置指南
> *  Uiautomator自动化框架的缺点是编译和运行起来非常麻烦，需要手动执行CMD指令
> * 这个插件主要是为了解决Uiautomator框架编译和运行
> *  Uiautomator_Eclispe_Build_Run_plugins 是一个在Eclipse的插件
> * 只用点击一下就用来编译和运行自动框架（Uiautomator）的插件

>![示例](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E4%BB%8B%E7%BB%8D.png?raw=true)

##  如何使用Uiautomator_Eclispe_Build_Run_plugins 插件。

### 1. 配置编译环境

#### 配置JAVA环境变量  
  请先安装JDK。然后将JDK路径配置到环境变量里面。（如何配置JAVA环境变量这里就不述说了）

  例如：

    D:\java\jdk1.7.0_79\bin  

    D:\java\jdk1.7.0_79\jre

  请一定在环境变量 PATH上添加这两个路径。

  配置完成后请验证一下JAVA环境变量是否配置成功如下图：打开CMD.exe 输入 java -verison



  ![java](https://raw.githubusercontent.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/183176ebbec5fd52f275bd91e1f277ada73aa0b6/image/JAVA%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F%E9%AA%8C%E8%AF%81.png)

  出现上面的界面代表JAVA环境变量配置成功


#### 配置Android sdk环境变量

例如：

D:\devtools\android-sdk-windows\platform-tools

D:\devtools\android-sdk-windows\tools

这个两个路径 请添加到环境变量Path下 在验证一下android sdk环境变量是否验证成功

打开CMD.exe

输入adb

输入android  -version 显示如下图所示则配置Android Sdk环境成功

 ![android](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/Android_ADB%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F.png?raw=true)

![android](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/Android_android%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F%E9%85%8D%E7%BD%AE.png?raw=true)


#### 配置Ant 环境变量
请先下载Ant

例如

D:\tools\apache-ant-1.9.5\bin

这个路径请添加到环境变量PATH路径下然后验证Ant环境变量是否成功

![ant](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/Ant%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F.png?raw=true)


到了这里我们的环境配置就完成了。

#### 2.如何使用这个插件.
  例如现在我们有一个APK,它有一个BUG,当你点击按钮总共100次后就会发生崩溃.

  那么我们复现这个BUG的时候,我们就可以去手动点击100次按钮

  但是这么做效率太低了(而且实际的复杂的BUG也不可能这么简单复现出来.)

  我们完全可以使用自动化达到点击按钮100次的效果.

  ##### 介绍

 * Uiautomator这个自动化框架，可以做黑盒测试，不需要APK的源码。

 * 它可以根据文本信息定位到控件，也可以通过资源ID定位到控件（网上有一种说法是Uiautomator不能使用资源ID定位控件是错误的）


 1. 首先拷贝插件com.no.uiautomator_1.0.0.1.jar到Eclipse目录下的plugins目录，如下图：

   ![JAVA](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E6%8F%92%E4%BB%B6%E5%9B%BE%E6%A0%87.png?raw=true)

  ![javaProject](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E5%A4%8D%E5%88%B6jar%E5%88%B0Eclipse%E7%9A%84plugins%E7%9B%AE%E5%BD%95%E4%B8%8B.png?raw=true)
 然后打开Eclispe

 2. 然后我们新建一个JAVA工程文件。如下图



 ![javaProject](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E6%96%B0%E5%BB%BA%E7%94%A8%E6%9D%A5%E6%B5%8B%E8%AF%95%E7%9A%84JAVA%E5%B7%A5%E7%A8%8B.png?raw=true)


 然后选中工程右键弹出工程配置项

 ![pro](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E5%8F%B3%E9%94%AE%E9%80%89%E4%B8%AD%E5%B7%A5%E7%A8%8B%E9%80%89%E6%8B%A9Properties.png?raw=true)

 我们选择编码模式为UTF-8 点击Apply，为什么选择UTF-8是因为：
 >如果不选择UTF-8,当你使用UiObject uiObject = new UiObject(new UiSelector().text("第二个按钮"))去匹配控件的时候，你就一直找不到这个中文的控件。

 如下图

  ![javaProject](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E8%AF%B7%E9%80%89%E6%8B%A9UTF-8.png?raw=true)

* 添加JUnit 4测试库

然后我们选择JAVA Build Path 再选择Libraries->
再选择Add Library->JUnit->Next->JUnit 4->finish
如下图所示：

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E7%BB%99JAVA%E5%B7%A5%E7%A8%8B%E6%B7%BB%E5%8A%A0Junit4%E5%BA%93.png?raw=true)

* 添加android.jar和uiautomator.jar库到工程

点击Add External JARs:

![Add](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/Add_External%20Jars.png?raw=true)

选择你的Android SDK目录下的platforms文件夹，

请选择android目录也可以选择其他的，请记住你选择的android-xx文件是多少。比如我选择的就是23。如下图

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E8%AF%B7%E9%80%89%E6%8B%A9android_sdk%E8%B7%AF%E5%BE%84%E4%B8%8B.png?raw=true)

那么这个时候我们配置库就完成了：配置完成如下图：

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E5%BA%93%E9%85%8D%E7%BD%AE%E5%AE%8C%E6%88%90%E7%9A%84%E6%83%85%E5%86%B5.png?raw=true)


好了这个时候我们就可以编写代码了

### 编写Uiautomator代码


我们新建一个类叫类名叫Test，包名叫com.test.uiautomator。如下图

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E6%96%B0%E5%BB%BA%E7%B1%BB.png?raw=true)

#### 设置Uiautomator 的Id(这个不用每次都设置，只要设置一次就可以了，刚刚我们选择的是android-23,)

打开CMD.exe 输入android list 可以看到有很多ID如下图

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/Android%20list.png?raw=true)

然后找到刚才我们选择的android-23的地方看下这个它的ID是多少

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E6%9F%A5%E7%9C%8B%E4%BD%A0%E9%80%89%E6%8B%A9%E7%9A%84ID.png?raw=true)

如上图我的android-23 的 id 是 11（请注意每个人的id可能一样，请一定要注意这个问题）


右键点击Test.java类，然后点击Setting Uiautomator
![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E9%85%8D%E7%BD%AEUiautomator%E7%9A%84Id.png?raw=true)

然后在弹出的对话框中输入11 点击确定

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E5%BC%B9%E5%87%BA%E6%A1%86%E8%AE%BE%E7%BD%AEID.png?raw=true)

然后我们编写Test的代码如下
```java
package com.test.uiautomator;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Test extends UiAutomatorTestCase {

	public void testclick() {
		UiObject uiObject = new UiObject(new UiSelector().text("FirstButton"));
		for (int i = 0; i < 100; i++) {
			try {
				uiObject.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
```
然后安装测试的APK   Uiautomator.apk

用USB连接你的手机，
然后右键点击Test.java

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E7%BC%96%E8%AF%91%E8%BF%90%E8%A1%8C.png?raw=true)

github目录介绍

![junit](https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins/blob/master/image/%E5%B7%A5%E7%A8%8B%E7%9B%AE%E5%BD%95%E4%BB%8B%E7%BB%8D.png?raw=true)


github目录：https://github.com/jikun2008/Uiautomator_Eclispe_Build_Run_plugins

我觉得我写的这篇文章配置Uiautomator的Eclipse编译插件过于复杂，我想以后更新一个视频来教大家使用这个插件
