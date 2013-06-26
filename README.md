# NML/NSI Topology Service (NTS)

***This is still work under heavey developement, it's not yet ready to be used yet!***

The following instructions are really about setting up a dev environment.


## Installation

### Requirements
* Java&trade; 1.6 or higher.
* Maven2: it's going to install all dependencies except the following one!
* Simple Lookup Service (sLS):

```bash
  # To install sLS
  svn checkout http://simple-lookup-service.googlecode.com/svn/ sLS
  cd sLS/trunck/lookup
  mvn install
```



## Running

***This is just a skeleton under heavey developement***

```bash
  # to run unit tests
  mvn test
  
  # to the run the service
  ./run.sh
```
