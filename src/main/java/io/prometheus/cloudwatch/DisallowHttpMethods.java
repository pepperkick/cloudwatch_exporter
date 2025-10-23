package io.prometheus.cloudwatch;

import java.util.EnumSet;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

class DisallowHttpMethods extends Handler.Wrapper {
  private final EnumSet<HttpMethod> disallowedMethods;

  public DisallowHttpMethods(EnumSet<HttpMethod> disallowedMethods) {
    this.disallowedMethods = disallowedMethods;
  }

  @Override
  public boolean handle(Request request, Response response, Callback callback) throws Exception {
    HttpMethod httpMethod = HttpMethod.fromString(request.getMethod());
    if (disallowedMethods.contains(httpMethod)) {
      Response.writeError(request, response, callback, HttpStatus.METHOD_NOT_ALLOWED_405);
      return true;
    }
    return super.handle(request, response, callback);
  }
}
