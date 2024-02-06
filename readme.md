install docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg
echo   "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
"$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" |   sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo docker build --tag=ivansla/my-repo:whatsapp-bot .
sudo docker run -p8443:8443 -dt --name whatsapp-bot ivansla/my-repo:whatsapp-bot
sudo docker push  ivansla/my-repo:whatsapp-bot

sudo docker run --name my-nginx \
--mount type=bind,source=/home/ivansla/Tools/var/www,target=/usr/share/nginx/html,readonly \
--mount type=bind,source=/home/ivansla/Tools/var/nginx/conf,target=/etc/nginx/conf,readonly \
-p 80:80 \
-d nginx

https://developers.facebook.com/apps/107596238979849/dashboard/?business_id=284103596902546

Let's Encrypt
be patient when enabling ports in network

dns must point to the machine where you are running certbot
if using azure you need to enable port 80 in networking for vm.

https://www.digitalocean.com/community/tutorials/how-to-use-certbot-standalone-mode-to-retrieve-let-s-encrypt-ssl-certificates-on-ubuntu-20-04
sudo snap install --classic certbot
sudo ln -s /snap/bin/certbot /usr/bin/certbot
sudo certbot certonly --standalone --key-type rsa --rsa-key-size 2048 -d ssl-example1.brazilsouth.cloudapp.azure.com --work-dir ~/ssl/ --config-dir ~/ssl/ --logs-dir ~/ssl/
sudo chown -R azureuser:azureuser ./ssl/


sudo certbot certonly --standalone -d refactorit.sk

sudo certbot certonly --standalone --key-type rsa --rsa-key-size 2048 -d mywhatsappbotexample.brazilsouth.cloudapp.azure.com --work-dir /home/azureuser/ssl/ --config-dir /home/azureuser/ssl/ --logs-dir /home/azureuser/ssl
https://whatsmychaincert.com/?refactorit.sk