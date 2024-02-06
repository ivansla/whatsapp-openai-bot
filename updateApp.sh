export JAVA_HOME=/opt/jdk17
mvn clean verify
sudo docker build --tag=ivansla/my-repo:whatsapp-bot .
sudo docker push ivansla/my-repo:whatsapp-bot

ssh 4.228.203.225 << EOF
 sudo docker container stop whatsapp-bot
 sudo docker container rm whatsapp-bot
 sudo docker pull ivansla/my-repo:whatsapp-bot
 sudo docker run -p8443:8443 -dt --name whatsapp-bot ivansla/my-repo:whatsapp-bot
EOF




