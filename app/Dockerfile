FROM ghcr.io/graalvm/native-image-community:25.0.2-muslib-ol10-20260120 AS build
ARG JAR_FILENAME=website-all.jar
WORKDIR /app
COPY ./native-build.sh .
COPY ./build/libs/${JAR_FILENAME} .
RUN ./native-build.sh ${JAR_FILENAME} 16m

FROM scratch
ARG WORKDIR=/app
WORKDIR ${WORKDIR}
COPY --from=build ${WORKDIR}/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx16m", "-XX:MissingRegistrationReportingMode=Warn" ]
