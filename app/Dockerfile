FROM ghcr.io/graalvm/native-image-community:24.0.2-muslib-ol10-20250715 AS build

RUN microdnf -y install findutils

WORKDIR /app
COPY . .
RUN ./gradlew nativeCompile -PnativeCompileExtraBuildArgs="--libc=musl,-march=x86-64-v3,--static"

FROM scratch
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx100m" ]
