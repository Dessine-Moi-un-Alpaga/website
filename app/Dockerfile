ARG WORKDIR=/app

FROM ghcr.io/graalvm/native-image-community:25i5-25.0.5.0.3-muslib-ol10-20261103 AS build

ARG JAR_FILENAME=website-all.jar
ARG WORKDIR
WORKDIR ${WORKDIR}
COPY ./build/libs/${JAR_FILENAME} .
RUN native-image -R:MaxHeapSize=16m -jar ${JAR_FILENAME}


FROM scratch

ARG WORKDIR
WORKDIR ${WORKDIR}
COPY --from=build ${WORKDIR}/website .
ENTRYPOINT [ "/app/website", "-Xmx16m", "-XX:MissingRegistrationReportingMode=Warn", "-Duser.country=BE", "-Duser.language=fr" ]
