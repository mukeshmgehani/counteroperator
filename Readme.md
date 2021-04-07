# Counter Operator using java operator SDK 

---
###Prerequisites

1. Maven latest version
2. Java 11 and Above
3. command prompt or shell
4. Docker
5. Kubernete cluster with Kubectl

---
 
### Please follow below steps to compile and run the Project in kubernetes

1.If you want to deploy and run on kubernetes then follow below steps.
**Note:- Your kubernet cluster should be up and running and showing command as executed on minikube** 

**a)** Go to your project directory.

**cmd>cd counteroperator**

**b)** Run below command for creating custom resource definition.

**cmd>kubectl apply -f kube/counterservice-crd.yaml**

**c)** you can see that CRD  got created and you can verify with below command and see all.

**cmd>kubectl get crds**

**d)** Run below command for creating Counter operator and deploying it with service account and roles.

**cmd>kubectl apply -f kube/deployment.yaml**

**e)** you can see that deployment,service account,cluster load binding  got created and configured. you can verify with below command and see all.

**cmd>kubectl get all**


**f)** Run below command for testing Counter operator. you can see nginx.mukesh.operator/nginx-custom-service configuredy.

**cmd>kubectl apply -f kube/test-nginx.yaml  --validate=false**

**Note:- you have followed the steps for counterservice as mentioned on below url.**
<https://github.com/mukeshmgehani/counterservice>

**g)** Run below command (as per minikube) to get the external IP for counter service and hit the URL.

**cmd>minikube service counterservice --url**

**h)** you can hit the endpoints and check using this url.

No. of counter  -- <Url from point .d/counter>

Increase counter -- <Url from point .d/counter/increase>

Decrease counter -- <Url from point .d/counter/decrease>

---