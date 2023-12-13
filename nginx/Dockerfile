FROM nginx:alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf.template
EXPOSE 80 443
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]
CMD ["nginx", "-g", "daemon off;"]
