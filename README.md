# HuaWeiWeatherView
<br>
这里根据华为手机天气、手机管家等界面特效<br>
！[image](https://github.com/YISHUIH/HuaWeiWeatherView/raw/master/hwv.gif)
<br>
仿出的圆形刻度盘和水波加速球效果。
```java
```
```Java
public static void main(String[]args){} //Java
```
```Java
```
你可以通过
```
```
```Java
mv.change(200);//动态绘制刻度
```
```Java
```

动态绘制刻度圆盘。
<br>
通过调用。

```
```
```Java
mv.moveWaterLine();//让水球动起来 //Java
```
```Java
```

让水球动起来
<br>
通过接口回调，动态同步设置背景颜色。
```
```
```Java<br>
 mv.setOnAngleColorListener(onAngleColorListener);
 
 private MyView.OnAngleColorListener onAngleColorListener=new MyView.OnAngleColorListener() {
        @Override
        public void onAngleColorListener(int red, int green) {
            Color color=new Color();
            int c=color.argb(150, red, green, 0);
            //界面背景色
            ll.setBackgroundColor(c);
        }
    };
```
```Java
```

代码高亮
----------
在三个反引号后面加上编程语言的名字，另起一行开始写代码，最后一行再加上三个反引号。
```java
```
```Java
public static void main(String[]args){} //Java
```
```c
int main(int argc, char *argv[]) //C
```
```Bash
echo "hello GitHub" #Bash
```
```javascript
document.getElementById("myH1").innerHTML="Welcome to my Homepage"; //javascipt
```
```cpp
string &operator+(const string& A,const string& B) //cpp
```

