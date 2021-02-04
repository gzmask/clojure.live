# Development environment

## Command line two JVM approach

### start the shadow-cljs compilers

    clj -A:shadow-cljs watch frontend bootstrap-support
    clj -A:shadow-cljs cljs-repl frontend

### open localhost:8081 from the cljs-repl task JVM
### connect to the nrepl of the cljs-repl port


## server nrepl single JVM approach

### start the shadow-cljs nrepl server

	clj -A:shadow-cljs server

### repl commands to watch compiling builds

	(shadow/watch :frontend)
	(shadow/watch :bootstrap)

### piggy-back cljs-repl

	(shadow/repl :frontend)

### open localhost:8080 from the cljs-repl
