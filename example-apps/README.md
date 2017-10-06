There is an example app of a todo-application that shows
the usage of the redux devTool.
As the devTool is intended to be independent from specific implementations
of the Redux pattern, the example code is split up into different modules:

**todolist-common**: contains the applications code. 
This includes actions, reducers, the state and the UI components.
This module is independent of the actual redux implementation. 
However, for it's UI it uses the Virtual-SceneGraph approach from
ReduxFX. This only concerns the UI part of the app. The state-management
is independent from ReduxFX

**redux-java**: contains a startup class to bootstrap the application
with redux-java. This module uses *todolist-common* as dependency.

**reduxfx**: contains a startup class to bootstrap the application
with ReduxFX. This module uses *todolist-common* as dependency.

Both libraries (redux-java and reduxfx-store) are similar in that they both use 
reducers, actions and a state and therefore can reuse the same classes from the *todolist-common* module.
They are different in how they "execute" the reducers. While redux-java uses
an api close to the original redux.js and implements it with relatively simple functional
java code, reduxfx-store implements the redux-flow with reactive streams.