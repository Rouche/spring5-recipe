

## Personnal notes with fabric8
###### While doing spring5 lectures

#### default undeploy *Not Working*

If you wish to fabric8:undeploy on the Ctrl+C keypress you can pass in the fabric8.onExit goal:
```
mvn fabric8:run -Dfabric8.onExit=undeploy
```

#### templates for yml goes in src/main/fabric8
```
deployment.yml
profiles.yaml
sa.yml
service.yml
```

#### Dockerfile

see pom.xml, dockerFileDir needs to be set

Put option in a separate profile to not fail CI

Run multiple profile in wondows:
```
mvn clean fabric8:build -P'default,k8s'
```

#### docker run trick

To not loose a centos run. Execute tail

```
docker run -d centos tail -f /dev/null
```
