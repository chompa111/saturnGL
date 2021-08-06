# saturnGL
Graphic Library to build animations with Java


## Short example:

``` java

var svg =  new SVG("svgPath.svg");
add(svg);
Animation.strokeAndFill(svg,seconds(4)).execute()

```
<img src="https://user-images.githubusercontent.com/43425971/128118638-3a0f8d75-f596-4949-946f-1fdb032268bc.gif" alt="drawing" width="550"/>



## Saturn suports [Latex](https://en.wikipedia.org/wiki/LaTeX)

![latex](https://user-images.githubusercontent.com/43425971/128233754-3aa6fa40-8ff8-402c-8cc7-07d7610c6fcb.gif)


# First Steps

## 1.1 Create a class that extends Presentation

to create your first animation on Saturn, you need to:
- create a class that extends Presentation
- create a new instace of its class
- on the main function: call the method `buildPresentation()` that is the method were you will create your _animation code_ properly:


```java
public class Example extends Presentation {
    @Override
    public void setup(PresentationConfig presentationConfig) {
        
    }

    @Override
    public void buildPresentation() {

    }

    public static void main(String[] args) {
        new Example().buildPresentation();
    }
}
```

> the method `buildPresentation()` will contain all your instructions to build the animation, and the method `setup()` to config preferences about de video codec, fps and so on


## 2.1 First Animation

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
circle.move(200,0).execute()// first arg is the amount on X, the second on Y
```
<img src="https://user-images.githubusercontent.com/43425971/128455601-1ee5ab65-8b10-4d5c-8ca6-cd0e9db2c9f1.gif" alt="drawing" width="550"/>

- and change its color:

```java
circle.move(200,0).execute()
circle.changeColor(new Color(255,0,0)).execute(); // red color
```
<img src="https://user-images.githubusercontent.com/43425971/128456165-cb901a8f-e2a0-41e3-9d96-f64a40fdb58d.gif" alt="drawing" width="550"/>

What if we wanted to move and change color at the same time? Its a good moment to talk about Tasks on SaturnGL.
`Task` is some action that occurs in a given time, by the way  `move()` and `changeColor()` are examples of tasks that you already know.
On Saturn you can **compose** two or more tasks on a a single complex task. in our example we need to compose the task `move` and `changeColor` to another taks 
that execute it in paralel way. we could do that as following:

```java
var taskMove=circle.move(400,0);
var taskChangeColor=circle.changeColor(new Color(255,0,0));
var parallelTask = taskMove.parallel(taskChangeColor);

// or simplifying
var parallelTask=circle.move(400,0).paralel(circle.changeColor(new Color(255,0,0));

```

to execute the task job you need to call the `execute()` method.

```java
circle.move(400,0).paralel(circle.changeColor(new Color(255,0,0)).execute();

```
Finally!

<img src="https://user-images.githubusercontent.com/43425971/128457762-efba4e4f-1d2c-4881-82a6-37158c34d9ba.gif" alt="drawing" width="550"/>












