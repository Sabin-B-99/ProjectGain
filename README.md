<h1 style="text-align: center;"> ProjectGain </h1>
<hr>

A **desktop application** developed to help you manage your fitness activities.

This application lets you create, read, update and delete your workout routine.
You can set timer for each workout on a routine, and start your workout. 
The timing details will also be persisted along with the routine. After you start your
workout the timer will continue until automatically until the end of the routine. You will 
be notified with sound cues when a workout set ends.

### Motivation

I basically wanted to create a desktop timer for the workout programs provided by 
[DAREBEE](https://www.darebee.com/programs.html), a fitness website I came across 
during the Covid-19 lockdowns. 

There were a lot of workout timer for phones. But I spend most of the time on my computer and I had all my workout programs 
saved on my computer. And it was too much of a hassle for me to manage the workout programs in my phone, and 
switch between YouTube and timer app in between sets. With a desktop app I could just start timer on one screen and workout video on the other. 
I actually wanted to embed a YouTube link directly above the timer on the app itself to take the full advantage of the desktop screen size. 
Will probably do that in the future. 

### Demo

A quick video demonstration of the application.

[//]: # (TODO: Add demonstration video. And remove this comment.)

### Quick Start

1. Make sure you have [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/downloads/) or higher version installed.
2. Install [Maven](https://maven.apache.org/index.html). You can follow [this](https://www.baeldung.com/install-maven-on-windows-linux-mac) guide.
3. Run the following command: 

```
mvn clean javafx:run
```


### Technical Details
* **Language and framework:** Java (JDK version 17), JavaFX
* **Database:** MySQL

### TODOs:
* Add multi-threading to perform activities, like database operations, in background so the UI doesn't freeze for long background processes.
* Add feature to let user embed workout video links (YouTube) while creating routines. And show the video along with timer.
* Add CSS to improve the application visually.

