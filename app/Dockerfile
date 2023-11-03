FROM ghcr.io/graalvm/native-image-community:17.0.9-muslib-ol9-20231024 AS build

RUN microdnf -y install findutils

WORKDIR /app
COPY . .
RUN ./gradlew nativeCompile

FROM scratch
WORKDIR /app
COPY --from=build /app/build/native/nativeCompile/graalvm-server ./
ENTRYPOINT [ "/app/graalvm-server" ]
