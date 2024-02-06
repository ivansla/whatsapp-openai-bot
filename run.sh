docker run --name my-nginx \
   --mount type=bind,source=/home/ivansla/Tools/var/www,target=/usr/share/nginx/html,readonly \
   --mount type=bind,source=/home/ivansla/Tools/var/nginx/conf,target=/etc/nginx/conf,readonly \
   -p 80:80 \
   -d nginx