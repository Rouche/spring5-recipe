

## Basic structure

#### versions
![kubeadm](../src/main/resources/static/images/k8s-udemy/version.png)

#### mendatory 4 properties

```
apiVersion: v1
kind: Pod
metadata: #Structure is predefined
  name: myapp-pod
  labels:   #Can put anything under labels
    app: myapp
    type: front-end
    
spec:
  containers:     #Can be List/Array
    - name: nginx-container
      image: nginx
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