[![](https://jitpack.io/v/chompa111/saturnGL.svg)](https://jitpack.io/#chompa111/saturnGL)
[![](https://raw.githubusercontent.com/gist/chompa111/1e9efe93340cd8fff7b52116641e1dcd/raw/f3ee54adaea75f37af89f72be759ac9bfe6d1fa2/docptbr.svg)](https://chompa111.github.io/saturn-doc/pt)
[![](https://raw.githubusercontent.com/gist/chompa111/c1258cab438c026b591cae561a631c2e/raw/050bab952ab5494ba101cd7bbe64683da483d6a4/docen.svg)](https://chompa111.github.io/saturn-doc)

![baner7](https://user-images.githubusercontent.com/43425971/131168286-6077a2c9-7572-4c39-97e1-6da6636addd2.png)


# SaturnGL
 *Graphic Library to build animations with Java*

## Short example:

``` java

var svg =  new SVG("svgPath.svg");
add(svg);
Animation.strokeAndFill(svg,seconds(4)).execute()

```
<img src="https://user-images.githubusercontent.com/43425971/128118638-3a0f8d75-f596-4949-946f-1fdb032268bc.gif" alt="drawing" width="550"/>



## Saturn suports [Latex](https://en.wikipedia.org/wiki/LaTeX)

![latex](https://user-images.githubusercontent.com/43425971/128233754-3aa6fa40-8ff8-402c-8cc7-07d7610c6fcb.gif)

# Import Saturn as `Maven`/`Gradle` dependency
### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
    maven { url "https://www.dcm4che.org/maven2/"}
}
```
```groovy
dependencies {
    implementation 'com.github.chompa111:saturnGL:1.0.2'
}
```

### Maven
``` xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
    <repository>
        <id>dcm4che.org</id>
        <url>https://www.dcm4che.org/maven2/</url>
    </repository>
</repositories>
```
``` xml
<dependency>
    <groupId>com.github.chompa111</groupId>
    <artifactId>saturnGL</artifactId>
    <version>1.0.2</version>
</dependency>
```






# First Steps 🪐

## 1) Create a class that extends Presentation

to create your first animation on Saturn, you need to:
- create a class that extends Presentation
- create a new instace of its class 
- on the main function: call the method `build()`


```java
public class Example extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        
    }

    @Override
    public void buildPresentation() {

    }

    public static void main(String[] args) {
        new Example().build();
    }
}
```

> ✔️ the method `buildPresentation()` will contain all your instructions to build the animation, and the method `setup()` those to config preferences about de video codec, fps and so on


## 2) First Animation

#### Our first animation will be a moving circle.

- to create a circle:

```java
var center = new Point(100,500);
var radius = new DoubleHolder(100);
var color = new Color(255,0,255); // this is a awt color
var circle = new Circle(center,radius,color); 

```

- add the circle to your presentation:

```java
add(circle);
```
- move your circle:
```java
circle.move(200,0).execute();// first arg is the amount on X, the second on Y
```
<img src="https://user-images.githubusercontent.com/43425971/128455601-1ee5ab65-8b10-4d5c-8ca6-cd0e9db2c9f1.gif" alt="drawing" width="550"/>

- and change its color:

```java
circle.move(200,0).execute();
circle.changeColor(new Color(255,0,0)).execute(); // red color
```
<img src="https://user-images.githubusercontent.com/43425971/128456165-cb901a8f-e2a0-41e3-9d96-f64a40fdb58d.gif" alt="drawing" width="550"/>

What if we wanted to move and change color at the same time? It's a good moment to talk about Tasks on SaturnGL.
`Task` is some action that occurs in a given time, by the way  `move()` and `changeColor()` are examples of tasks that you already know.
On Saturn you can **compose** two or more tasks on a a single complex task. In our example we need to compose the task `move` and `changeColor` to another task 
that executes it in parallel way. We could do that as following:

```java
var taskMove = circle.move(400,0);
var taskChangeColor = circle.changeColor(new Color(255,0,0));
var parallelTask = taskMove.parallel(taskChangeColor);

// or simplifying
var parallelTask=circle.move(400,0).parallel(circle.changeColor(new Color(255,0,0));

```

to execute the task job you need to call the `execute()` method.

```java
circle.move(400,0).parallel(circle.changeColor(new Color(255,0,0)).execute();

```
Finally!

<img src="https://user-images.githubusercontent.com/43425971/128457762-efba4e4f-1d2c-4881-82a6-37158c34d9ba.gif" alt="drawing" width="550"/>



## 3) Generating the _.mov_ video


Simply by executing the main method with the circle example, the library will generate a live preview on your screen, and in the end a .mov file with your animation inside the `/video` folder on the root of project.

you can set the fps of the generated video on the `setup()` method as following:


``` java
@Override
public void setup(PresentationConfig presentationConfig) {
    presentationconfig.setFps(75);        
}
```
> presentation config always has a default value for every config, in case 60 fps is the default value

you can disable the video generation to increase your preview fps while testing your code.

``` java
@Override
public void setup(PresentationConfig presentationConfig) {
    presentationconfig.setFps(75);
    presentationconfig.disableCodec(true);
}
```

