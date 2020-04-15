FROM node:13.10.1-stretch
WORKDIR /usr/src/app

RUN apt-get update
RUN npm install -g @angular/cli@latest
COPY . /usr/src/app
RUN npm install

CMD ng serve --host 0.0.0.0