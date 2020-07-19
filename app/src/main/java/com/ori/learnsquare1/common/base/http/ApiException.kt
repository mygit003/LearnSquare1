package com.ori.learnsquare1.common.base.http

class ApiException(var code: Int, override var message: String) : RuntimeException()