
## UDEMY course Kubernetes

https://www.udemy.com/learn-kubernetes/learn

#### Section 3 - Setup VirtualBox

- Setup VirtualBox : https://www.virtualbox.org/

- Download OS Image : https://www.osboxes.org/  
    - Select VirtualBox Image (Ubuntu)  
    - Extract to disk

- Create a new VM  
    - Use existing disk (Extracted file)  
    - 2Gig Ram

- Change Network Adapter  
    - Settings -> Network Adapter -> Bridged  
    - Take a Snapshot
    
- SSH application : https://mobaxterm.mobatek.net/  
    - start the VM
    - User/Password: osboxes / osboxes.org
    - open terminal
    - ifconfig to get the IP
    - SSH into the machine
        - if connection refused, check in terminal
            ```
            service ssh status
            sudo su
            apt-get update
            apt-get install openssh-server
            service ssh status
            ```
            
#### Section 4 - Install Docker

- SSH in machine
    ```
    apt-get install -y docker.io
    ```

#### Section 5 reference
Kubernetes Documentation Site: https://kubernetes.io/docs/

Kubernetes Documentation Concepts: https://kubernetes.io/docs/concepts/

Kubernetes Documentation Setup: https://kubernetes.io/docs/setup/pick-right-solution/

Kubernetes Documentation - Minikube Setup: https://kubernetes.io/docs/getting-started-guides/minikube/

#### Section 6 kubeadm

![kubeadm](./src/main/resources/static/images/k8s-udemy/kubeadm.png)

Install kubeadm: https://kubernetes.io/docs/setup/independent/install-kubeadm/

- create 3 VM in VirtualBox (kube-master, kube-node1, kube-node2)
    - Do section 3 then shutdown
    - 2Gig, 2CPU, Network bridged
    - Clone X2 : reinitialize mac adress, linked clone
    - Set host unique names  
    ```
    sudo su
    nano /etc/hostname
    nano /etc/hosts
    shutdown now
    ```
    - Assign static IP adresses : Global tools -> Host Network Manager
        - Optionally create a new Host network
        - uncheck DHCP server
        - Each Machine -> Settings -> Network -> Adapter 2
        ![kubeadm](./src/main/resources/static/images/k8s-udemy/network.png)
        - ```ifconfig``` : take the new interface without IP
        - ```ifconfig INTERFACE IP``` (enp0s8, 192.168.56.2)
        - ```nano /etc/network/interfaces```
        ![kubeadm](./src/main/resources/static/images/k8s-udemy/configip.png)
        - ```reboot```
    - Disable swap
        - ```swapoff -a```
        - ```nano /etc/fstab``` comment swap line

#### Section 13 reference
Oracle VirtualBox:  https://www.virtualbox.org/

Link to download VM images: http://osboxes.org/

Link to kubeadm installation instructions: https://kubernetes.io/docs/setup/independent/install-kubeadm/

Note: If you run into network issues try using Flannel networking instead of Calico.

#### Section 15 (part 2, 3, 4 in steps image)
- install docker
```
apt-get update
apt-get install -y docker.io
```
- prepare environment @Important: install curl, kubelet, kubeadm, kubectl
```
apt-get update && apt-get install -y apt-transport-https curl
apt install curl
curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
****COPY FROM HERE
cat <<EOF >/etc/apt/sources.list.d/kubernetes.list
deb http://apt.kubernetes.io/ kubernetes-xenial main
EOF****TO HERE
apt-get update
apt-get install -y kubelet kubeadm kubectl
```
- init master and cluster
In documentation:  
(2/4) You need to choose a Pod Network Plugin in the next step.  
Depending on what third-party provider you choose, you might have to set the ```--pod-network-cidr``` to something provider-specific.  
The tabs below will contain a notice about what flags on ```kubeadm init``` are required  
(3/4) With Flannel: 

    ```kubeadm init --pod-network-cidr=10.244.0.0/16 --apiserver-advertise-address=192.168.56.5```
    
    Copy the join command.

    ```export KUBECONFIG=/etc/kubernetes/admin.conf```

- Install Flannel pod Network
    ```
    sysctl net.bridge.bridge-nf-call-iptables=1
    kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/v0.9.1/Documentation/kube-flannel.yml
    ```
    Verify
    ```
    kubectl get pods --all-namespaces
    ```
    
- Join nodes with command
```
kubeadm join 192.168.56.5:6443 --token xqryaj.gbf630g8buzwkzj8 --discovery-token-ca-cert-hash sha256:fec392a8f842781c929c201109f5f98a982fc2393dacb0466f2e70c665e05b50
```

#### Section 17 google cloud
If you haven't created an account already, you can get FREE 12 Months subscription to Google Cloud Platform. Check it out here:

https://cloud.google.com/free/

Kubernetes on Google Cloud: https://cloud.google.com/kubernetes-engine/docs/

#### Section 18
https://labs.play-with-k8s.com/

#### Section 20 Pods
Commands:
```
kubectl describe pods
kubectl get pods -o wide
```

Pod Overview: https://kubernetes.io/docs/concepts/workloads/pods/pod-overview/



# Linguee:

- Node : Machine (Physical or Virtual)
- Pod : Single applicartion instance. Smallest object you can create.
    * Pod have 1-1 relationship with container (app)
    * Pod can have multiple containers but different kind
    * Different container in same Pod share localhost address.
    