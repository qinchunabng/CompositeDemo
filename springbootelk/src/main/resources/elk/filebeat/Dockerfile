FROM store/elastic/filebeat:7.5.1
MAINTAINER qcb
COPY ./src/main/resources/elk/filebeat/filebeat.yml /usr/share/filebeat/filebeat.yml
USER root
RUN chown root:filebeat /usr/share/filebeat/filebeat.yml
USER filebeat