# Redux-JavaFX-Devtool

A Devtool for Redux-like libraries (i.e. [ReduxFX](https://github.com/netopyr/reduxfx) or [Redux-Java](https://github.com/glung/redux-java)) for JavaFX.
The goal is to provide similar development features like the [redux-dev-tools](https://github.com/zalmoxisus/redux-devtools-extension) do for redux.js.

## Usage

### 1. Create a redux application

The first step is to create a redux application with JavaFX. 
The redux-javafx-devtool is designed in a way that is
independent from a specific redux implementation library but it
comes with integration for 
[ReduxFX](https://github.com/netopyr/reduxfx) and 
[Redux-Java](https://github.com/glung/redux-java).

A good start is the tutorial in the [ReduxFX readme](https://github.com/netopyr/reduxfx#how-to-add-reduxfx-to-your-project).

### 2. Add the dependency to your project

To use the devtool you have to add the dependency to your project.
You can find it on [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Credux-javafx-devtool).

There are 2 dependencies that you typically need:
- The devtool itself
- A connector for a specific redux implementation like ReduxFX or redux-java. 

**Maven**:
```
<dependency>
    <groupId>eu.lestard.redux-javafx-devtool</groupId>
    <artifactId>devtool</artifactId>
    <version>0.1.0</version>
</dependency>

<!-- for ReduxFX -->
<dependency>
    <groupId>eu.lestard.redux-javafx-devtool</groupId>
    <artifactId>reduxfx-connector</artifactId>
    <version>0.1.0</version>
</dependency>

<!-- for redux-java -->
<dependency>
    <groupId>eu.lestard.redux-javafx-devtool</groupId>
    <artifactId>jvm-redux-connector</artifactId>
    <version>0.1.0</version>
</dependency>
```

**Gradle**:
```
compile 'eu.lestard.redux-javafx-devtool:devtool:0.1.0'

// for ReduxFX
compile 'eu.lestard.redux-javafx-devtool:reduxfx-connector:0.1.0'

// for redux-java
compile 'eu.lestard.redux-javafx-devtool:jvm-redux-connector:0.1.0'
```

### 3. Add setup code

To setup the devtool you have to hook it into the setup of the redux store.
This is typically done in your main application class. 

#### Setup ReduxFX

You can find a running example in [example-apps/reduxfx](https://github.com/lestard/redux-javafx-devtool/tree/master/example-apps/reduxfx).
This repository contains only a startup class with the reduxFX-specific code.
The rest of the application's code is not specific to reduxFX and is shared between other example apps.
You can find the rest of the application in [example-apps/todolist-common](https://github.com/lestard/redux-javafx-devtool/tree/master/example-apps/todolist-common).

Both ReduxFX and the redux-javafx-devtool are in an early development
stage. We know that the setup is quite complex and we will try to simplify
this with upcoming releases. 
Therefore it's likely that the process shown in the following code example
will change in the future.

Without the devtool a typically ReduxFX setup looks like this (based on version 0.4.0):

```
...
import io.reactivex.Flowable;

import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;

public class MyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an initial state instance
        AppState initialState = new AppState();
        
        // Create a store instance with the initial state and your apps reducer
        ReduxFXStore<AppState> store = new ReduxFXStore<>(
            initialState,
            (state, action) -> Update.of(Reducer.reduce(state, action))    
        );
        
        // Create a ReduxFX View instance
        ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);
        
        // Connect the store and the view
        view.connect(store.getStatePublisher(), store.createActionSubscriber());
        
    }
}
```

To add the devtool change the code like this:

```
import io.reactivex.Flowable;

import com.netopyr.reduxfx.store.ReduxFXStore;
import com.netopyr.reduxfx.updater.Update;
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;

import eu.lestard.redux_javafx_devtool.ReduxFXDevToolConnector;
import eu.lestard.redux_javafx_devtool.ReduxFXDevTool;


public class MyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppState initialState = new AppState();
        
        
        // Create the DevTool
        ReduxFXDevTool<AppState> devTool = ReduxFXDevTool.create();
        
        // Create a devTool-connector for ReduxFX.
        ReduxFXDevToolConnector<AppState> reduxfxDevToolConnector = new ReduxFXDevToolConnector<>();
        
        // Connect the devTool with the connector
        devTool.connect(reduxfxDevToolConnector);
        
        // Create a store instance with the initial state and your apps reducer
        ReduxFXStore<AppState> store = new ReduxFXStore<>(
            initialState,
            (state, action) -> Update.of(Reducer.reduce(state, action)),
            
            // add the devTool connector as middleware
            reduxfxDevToolConnector
            
        );
        
        ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);
        
        // connect the devTool with the view. 
        // instead of using the statePublisher from the actual store, we are using
        // the one from the devTool connector. This enables time-travel debugging
        view.connect(reduxfxDevToolConnector.getStatePublisher(), store.createActionSubscriber());
        
        
        
        // to initialize the devTool we need to publish a initial action.
        // You can use any type of action for this purpose. 
        // In this example we are using a simple string but you can also create a more meaningful action class for this.
        Object initAction = "init";
        Flowable.just(initAction).subscribe(store.createActionSubscriber());
        
        
        // open the devTool UI. 
        devTool.openDevToolWindow(primaryStage)
    }
}
```

#### Setup redux-java / jvm-redux

[redux-java]() is an implementation of the [jvm-redux-api](). 
You can find a running example in [example-apps/redux-java](https://github.com/lestard/redux-javafx-devtool/tree/master/example-apps/redux-java).
This repository contains only a startup class with the redux-java-specific code.
The rest of the application's code is not specific to redux-java and is shared between other example apps.
You can find the rest of the application in [example-apps/todolist-common](https://github.com/lestard/redux-javafx-devtool/tree/master/example-apps/todolist-common).

Without the devtool a redux-java setup can look like this. 
The UI in this example uses the Virtual-SceneGraph approach from ReduxFX:

```
...
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

import redux.api.Store;

public class MyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an initial state instance
        AppState initialState = new AppState();
        
        // Create a store instance with the initial state and your apps reducer
        Store<AppState> store = com.glung.redux.Store.createStore(
            (state, action) -> Reducer.reduce(state, action),
            initialState    
        );
        
        // Create a ReduxFX View instance
        ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);
        
        // Create a statePublisher that is needed to connect to the ReduxFX VirtualSceneGraph UI
        final Flowable<AppState> statePublisher = Flowable.create(
            emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
            BackpressureStrategy.BUFFER
        );
        
        // Create a actionSubscriber that is needed to connect to the ReduxFX VirtualSceneGraph UI
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        actionSubscriber.subscribe(store::dispatch);
        
        // Connect the store and the view
        view.connect(statePublisher, actionSubscriber);
    }
}
```

To add the devtool change the code like this:

```
...
import com.netopyr.reduxfx.vscenegraph.ReduxFXView;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

import eu.lestard.redux_javafx_devtool.JvmReduxDevToolConnector;
import eu.lestard.redux_javafx_devtool.ReduxFXDevTool;

import redux.api.Store;

public class MyApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create a devTool instance
        final ReduxFXDevTool<AppState> devTool = ReduxFXDevTool.create();
    
        // Create a devTool connector for jvmRedux
    	final JvmReduxDevToolConnector<AppState> jvmReduxDevToolConnector = new JvmReduxDevToolConnector<>();
    
        // Connect the devTool with the connector
    	devTool.connect(jvmReduxDevToolConnector);
    		
        AppState initialState = new AppState();
        
        Store<AppState> store = com.glung.redux.Store.createStore(
            (state, action) -> Reducer.reduce(state, action),
            initialState,
            
            // add the connector as store-enhancer
            jvmReduxDevToolConnector
            
        );
        
        ReduxFXView<AppState> view = ReduxFXView.createStage(MainView::view, primaryStage);
        
        final Flowable<AppState> statePublisher = Flowable.create(
            emitter -> store.subscribe(() -> emitter.onNext(store.getState())),
            BackpressureStrategy.BUFFER
        );
        
        final PublishProcessor<Object> actionSubscriber = PublishProcessor.create();
        actionSubscriber.subscribe(store::dispatch);
        
        view.connect(statePublisher, actionSubscriber);
        
        // to initialize the devTool we need to publish a initial action.
        // You can use any type of action for this purpose. 
        // In this example we are using a simple string but you can also create a more meaningful action class for this.
        Object initAction = "init";
        store.dispatch(initAction);
        
        // open the devTool UI. 
        devTool.openDevToolWindow(primaryStage)
    }
}
```