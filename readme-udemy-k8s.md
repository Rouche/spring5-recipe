
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
    - User/Password: osboxes.org
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
    apt-get install docker
    ```
