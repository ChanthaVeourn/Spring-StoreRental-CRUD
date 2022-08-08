FROM postgres:latest AS build
# configure time zone
ENV TZ=Asia/Phnom_Penh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
RUN dpkg-reconfigure --frontend noninteractive tzdata

FROM build AS development
COPY ./db-entrypoint.sh /docker-entrypoint.d/



FROM build AS production