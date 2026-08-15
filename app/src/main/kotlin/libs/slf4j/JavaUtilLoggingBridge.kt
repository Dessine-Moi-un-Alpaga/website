package com.dessinemoiunalpaga.website.libs.slf4j

import org.slf4j.bridge.SLF4JBridgeHandler

internal fun bridgeJavaUtilLoggingToSlf4j() {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
}
