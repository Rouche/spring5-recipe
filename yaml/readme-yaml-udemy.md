

## YAML Notes from Kubernetes UDEMY

#### versions
![kubeadm](../src/main/resources/static/images/k8s-udemy/version.png)

#### Basic structure, mendatory 4 properties

See: pod-definition.yml
```
apiVersion:
kind:
metadata:
spec:
```

- to run:
```kubectl create -f pod-definition.yml```

#### YAML linter

http://www.yamllint.com/

#### Coding Exercise 15

```
apiVersion: v1
kind: Pod
metadata:
  name: postgres
  labels:
    tier: db-tier
spec:
  containers:
    - name: postgres
      image: postgres
      env:
        - name: POSTGRES_PASSWORD
          value: mysecretpassword
```