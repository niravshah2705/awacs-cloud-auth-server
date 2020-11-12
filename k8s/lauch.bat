kubectl get all

kubectl config view

kubectl delete all --all

kubectl apply -f adminer-deployment.yaml
kubectl apply -f adminer-service.yaml

kubectl apply -f db-deployment.yaml
kubectl apply -f db-service.yaml

kubectl apply -f jms-deployment.yaml
kubectl apply -f jms-service.yaml

kubectl apply -f authserver.yaml

kubectl get all
