This module contains redux code (actions, reducer, state) and 
UI code created with [ReduxFX-View](https://github.com/netopyr/reduxfx)
for a simple todo list application. 

However, this module does not contain the startup code for
the application. For the startup code there are two other modules:

- [redux-java](../redux-java)
- [reduxfx](../reduxfx)

The *redux-java* module uses [jvm-redux-api](https://github.com/jvm-redux/jvm-redux-api) 
and [redux-java](https://github.com/glung/redux-java) as libraries as redux implementations.

The *reduxfx* module uses [reduxfx-store](https://github.com/netopyr/reduxfx)
as redux implementation. 

Both libraries (redux-java and reduxfx-store) are similar in that they both use 
reducers, actions and a state and therefore can reuse the same classes from this module.
They are different in how they "execute" the reducers. While redux-java uses
an api close to the original redux.js and implements it with relatively simple functional
java code, reduxfx-store implements the redux-flow with reactive streams.