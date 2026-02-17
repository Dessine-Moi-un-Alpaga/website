ARG WORKDIR=/app

FROM ghcr.io/graalvm/native-image-community:25.0.2-muslib-ol10-20260120 AS build

ARG JAR_FILENAME=website-all.jar
ARG WORKDIR
WORKDIR ${WORKDIR}
COPY ./native-build.sh .
COPY ./build/libs/${JAR_FILENAME} .
RUN ./native-build.sh ${JAR_FILENAME} 16m


FROM scratch

ARG WORKDIR
WORKDIR ${WORKDIR}
COPY --from=build ${WORKDIR}/website .
ENTRYPOINT [ "/app/website", "-Xmx16m", "-XX:MissingRegistrationReportingMode=Warn" ]
