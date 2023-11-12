FROM ghcr.io/graalvm/native-image-community:21.0.1-ol8-20231024 AS build

RUN microdnf -y install findutils

WORKDIR /app
COPY . .
RUN ./gradlew nativeCompile

FROM scratch
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server", "-Xmx200m" ]
