# kube-config-example Project
Quarkus project with kubernetes configmap and secret management

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/kube-config-example-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.

## Related Guides

- RESTEasy JAX-RS ([guide](https://quarkus.io/guides/rest-json)): REST endpoint framework implementing JAX-RS and more

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)

## Install as a service on Kubernates with minikube

First off, follow steps below:

1. Run docker service
2. Run minikube

Now, for every rebuild of the app package as a docker image you must indicate the docker daemon inside minikube instance, otherwise minikube will not retrieve the latest docker image built from local.

Run:

```shell script
eval $(minikube docker-env)
```

Compile project with

```shell script
./mvnw clean package
```

Ensure property *quarkus.container-image.build* is set to *true* so docker image is automatically created, otherwise add option
```shell script
-Dquarkus.container-image.build=true
```
at every build.

Before installing deployment, run the following from the root project:
```shell script
kubectl apply -f src/main/kubernetes
```

The output should be:
```shell script
configmap/kube-config-configmap created
secret/kube-config-secret created
```

Deploy application with
```shell script
kubectl apply -f target/kubernetes/kubernetes.yml
```

Ensure pod is correctly installed with

```shell script
kubectl get po

NAME                                      READY     STATUS     RESTARTS   AGE
kube-config-example-<some-hash>           1/1       Running    0          67m
```

Expose deployment with the following command:

```shell script
kubectl port-forward service/kube-config-example 8080:80
```

Try API with curl

```shell script
curl localhost:8080/hello

{"configMapData":{"password":"mycmpassword","username":"starred"},"secretData":{"password":"mypassword","username":"myusername"}
```

### Hot deploy

It is also possible to run quarkus with remote dev mode so any update will be hot swapped and seen immediately.

Make sure the following lines are present in *Dockerfile.jvm* when compiling

```
ENV QUARKUS_LAUNCH_DEVMODE=true
ENV JAVA_ENABLE_DEBUG=true
```

With application already running on container, run this:

```shell script
mvn quarkus:remove-dev
```
