FROM ghcr.io/graalvm/native-image-community:23.0.2-ol9-20250121 AS build

RUN microdnf -y install findutils

WORKDIR /app
COPY . .
RUN ./gradlew nativeCompile -PnativeCompileExtraBuildArgs="--libc=musl,-march=x86-64-v3,--static"

FROM scratch
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx100m" ]
