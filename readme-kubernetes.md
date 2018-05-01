
## Installation Windows 10

Tutorial: https://rominirani.com/tutorial-getting-started-with-kubernetes-on-your-windows-laptop-with-minikube-3269b54a226

#### 1 - allow run scripts

Start Windows PowerShell with the "Run as Administrator" option. Only members of the Administrators group on the computer can change the execution policy.

Enable running unsigned scripts by entering:

```
PS> set-executionpolicy remotesigned
```

#### 2 - install VirtualBox
https://www.virtualbox.org/wiki/Downloads

#### 3 - install PowerShell Gallery

https://www.powershellgallery.com/

Inspect
```
PS> Save-Module -Name PowerShellGet -Path <path>
```
Install
```
PS> Install-Module -Name PowerShellGet -Force
```

#### 4 - Install kubectl

https://kubernetes.io/docs/tasks/tools/install-kubectl/#install-with-powershell-from-psgallery

```
PS> Install-Script -Name install-kubectl -Scope CurrentUser -Force
PS> install-kubectl.ps1 [-DownloadLocation <path>]
```

#### 5 - Install Minikube

https://github.com/kubernetes/minikube

Put it in same place as kubectl, and add it to path.

Check %HOMEPATH%\.minikube

Check versions: `minikube get-k8s-versions`

Start minikube and verifications:
```
PS> minikube start --vm-driver=virtualbox
PS> minikube status
PS> kubectl cluster-info
PS> kubectl version
PS> minikube ip
```

Launch the Dashboard:
```
minikube dashboard
```

##### 5.1 tip: use-context minikube

If you had noticed closely when we started the cluster, there is a statement in the output that says “Kubectl is now configured to use the cluster.” What this is supposed to do is to eventually set the current context for the kubectl utility so that it knows which cluster it is talking to. Behind the scenes in your %HOMEPATH%\.kube directory, there is a config file that contains information about your Kubernetes cluster and the details for connecting to your various clusters is present over there.

In short, we have to be sure that the kubectl is pointing to the right cluster. In our case, the cluster name is minikube.

In case you see an error like the one below (I got it a few times), then you need to probably set the context again.

```
kubectl get nodes
   error: You must be logged in to the server (the server has asked for the client to provide credentials)
```

The command for that is:

```
PS> kubectl config use-context minikube
switched to context "minikube".
```

#### 6 Running a pod

```
PS> kubectl run hello-nginx --image=nginx --port=80
deployment "hello-nginx" created
PS> kubectl describe pod hello-nginx
```

Expose as a service:
```
PS> kubectl expose deployment hello-nginx --type=NodePort
service "hello-nginx" exposed
PS> kubectl get services
PS> minikube service --url=true hello-nginx
```

##### 6.1 scaling the pod
```
PS> kubectl scale --replicas=3 deployment/hello-nginx
deployment "hello-nginx" scaled
```

#### 7 From docker to kubectl

https://kubernetes.io/docs/reference/kubectl/docker-cli-to-kubectl/

For hello-world, Kubernetes will be in Back-off restarting failed container (CrashLoopBackOff)

This is because the container starts and automatically exists.

```
PS> kubectl run hello --image=hello-world
PS> kubectl get pods
NAME                     READY     STATUS             RESTARTS   AGE
hello-7fb44678db-cq8nq   0/1       CrashLoopBackOff   2          44s
PS> kubectl logs -f hello-7fb44678db-cq8nq
```

#### 8 mongo (section 182)
```
kubectl run mongo --image=mongo --port=27017
kubectl expose deployment mongo --type=NodePort
```

To print the URI of the service exposed locally

```
minikube service mongo --url
```