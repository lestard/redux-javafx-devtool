# Redux-JavaFX-Devtool

A Devtool for Redux-like libraries (i.e. [ReduxFX](https://github.com/netopyr/reduxfx) or [Redux-Java](https://github.com/glung/redux-java)) for JavaFX.
The goal is to provide similar development features like the [redux-dev-tools](https://github.com/zalmoxisus/redux-devtools-extension) do for redux.js.

This project is in early alpha state. 
It uses [ReduxFX](https://github.com/netopyr/reduxfx) for it's ui and internal state management.
However, due to the fact that ReduxFX itself is in an early stage at the moment, this project
uses snapshot versions of ReduxFX or even features from unmerged PullRequests.
To build the project it's likely that you will have to checkout and build (`mvn install`) the ReduxFX code
so that it is available in your local maven repo. 

This project is also a driving force for me to improve or add new features to ReduxFX 
itself when I need them to create the devtool. 
For this reason, sometimes you may even need to checkout [my fork of ReduxFX](https://github.com/lestard/redux-javafx-devtool) 
to build the project. This is the case when I use some features I added to my fork already 
that aren't merged in the upstream ReduxFX repo yet.
