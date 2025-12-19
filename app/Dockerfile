FROM ghcr.io/graalvm/native-image-community:25.0.1-muslib-ol10-20251021 AS build

RUN microdnf -y install findutils

WORKDIR /app
COPY . .
#RUN ./gradlew nativeCompile -PnativeCompileExtraBuildArgs="--libc=musl,-march=x86-64-v3,--static"
RUN native-image \
    -Dorg.slf4j.simpleLogger.logFile=System.out \
    -Dorg.slf4j.simpleLogger.cacheOutputStream=true \
    -Dorg.slf4j.simpleLogger.showDateTime=false \
    -Duser.country=BE \
    -Duser.language=fr \
    -H:IncludeLocales=fr,en \
    -H:+ReportExceptionStackTraces \
    -R:MaxHeapSize=100m \
    --color=always \
    --initialize-at-build-time=io.ktor,kotlin \
    --initialize-at-build-time=kotlinx.coroutines \
    --initialize-at-build-time=kotlinx.io.Buffer \
    --initialize-at-build-time=kotlinx.io.Segment \
    --initialize-at-build-time=kotlinx.io.Segment$$Companion \
    --initialize-at-build-time=kotlinx.io.bytestring.ByteString \
    --initialize-at-build-time=kotlinx.io.bytestring.ByteString$$Companion \
    --initialize-at-build-time=org.slf4j.LoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.BasicMDCAdapter \
    --initialize-at-build-time=org.slf4j.helpers.NOPMDCAdapter \
    --initialize-at-build-time=org.slf4j.helpers.NOP_FallbackServiceProvider \
    --initialize-at-build-time=org.slf4j.helpers.NOPLoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.SubstituteLoggerFactory \
    --initialize-at-build-time=org.slf4j.helpers.SubstituteServiceProvider \
    --initialize-at-build-time=org.slf4j.simple.OutputChoice \
    --initialize-at-build-time=org.slf4j.simple.OutputChoice$$OutputChoiceType \
    --initialize-at-build-time=org.slf4j.simple.SimpleLogger \
    --initialize-at-build-time=org.slf4j.simple.SimpleLoggerConfiguration \
    --initialize-at-build-time=org.slf4j.simple.SimpleLoggerFactory \
    --initialize-at-build-time=org.slf4j.simple.SimpleServiceProvider \
    --initialize-at-run-time=io.ktor.serialization.kotlinx.json.JsonSupportKt \
    --future-defaults=all \
    --libc=musl \
    -march=x86-64-v3 \
    --static \
    -jar /app/build/libs/website-all.jar \
    /app/build/native/nativeCompile/graalvm-server

FROM scratch
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx100m" ]
