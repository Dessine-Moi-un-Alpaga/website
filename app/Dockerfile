FROM ghcr.io/graalvm/native-image-community:25.0.2-muslib-ol10-20260120 AS build
ARG JAR_FILENAME=website-all.jar
WORKDIR /app
COPY ./build/libs/${JAR_FILENAME} .
RUN native-image \
    -Dorg.slf4j.simpleLogger.logFile=System.out \
    -Dorg.slf4j.simpleLogger.cacheOutputStream=true \
    -Dorg.slf4j.simpleLogger.showDateTime=false \
    -Duser.country=BE \
    -Duser.language=fr \
    -H:IncludeLocales=fr,en \
    -H:+ReportExceptionStackTraces \
    -H:+UnlockExperimentalVMOptions \
    -H:+CompactingOldGen \
    -R:MaxHeapSize=16m \
    --color=always \
    --exclude-config ${JAR_FILENAME} META-INF\/native-image\/org.webjars\/webjars-locator-lite\/resource-config.json \
    --initialize-at-build-time=io.ktor,kotlin \
    --initialize-at-build-time=kotlinx.coroutines \
    --initialize-at-build-time=kotlinx.io.Buffer \
    --initialize-at-build-time=kotlinx.io.Segment \
    --initialize-at-build-time=kotlinx.io.Segment\$Companion \
    --initialize-at-build-time=kotlinx.io.bytestring.ByteString \
    --initialize-at-build-time=kotlinx.io.bytestring.ByteString\$Companion \
    --initialize-at-build-time=org.slf4j.LoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.BasicMDCAdapter \
    --initialize-at-build-time=org.slf4j.helpers.NOPMDCAdapter \
    --initialize-at-build-time=org.slf4j.helpers.NOP_FallbackServiceProvider \
    --initialize-at-build-time=org.slf4j.helpers.NOPLoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.SubstituteLoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.SubstituteServiceProvider \
    --initialize-at-build-time=org.slf4j.simple.OutputChoice \
    --initialize-at-build-time=org.slf4j.simple.OutputChoice\$OutputChoiceType \
    --initialize-at-build-time=org.slf4j.simple.SimpleLogger \
    --initialize-at-build-time=org.slf4j.simple.SimpleLoggerConfiguration \
    --initialize-at-build-time=org.slf4j.simple.SimpleLoggerFactory \
    --initialize-at-build-time=org.slf4j.simple.SimpleServiceProvider \
    --initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt \
    --future-defaults=all \
    --libc=musl \
    -march=x86-64-v3 \
    --static \
    -jar ${JAR_FILENAME} \
    graalvm-server

FROM scratch
ARG WORKDIR=/app
WORKDIR ${WORKDIR}
COPY --from=build ${WORKDIR}/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx16m", "-XX:MissingRegistrationReportingMode=Warn" ]
