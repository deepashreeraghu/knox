/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.knox.gateway.shell;

import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.concurrent.Executors;

@Deprecated
public class Hadoop extends KnoxSession {

  public Hadoop(ClientContext clientContext) throws KnoxShellException, URISyntaxException {
    super(clientContext);
    this.executor = Executors.newCachedThreadPool();
    this.base = clientContext.url();

    try {
      client = createClient(clientContext);
    } catch (KnoxShellException | GeneralSecurityException e) {
      throw new HadoopException("Failed to create HTTP client.", e);
    }
  }

  public static Hadoop login( String url, Map<String,String> headers ) throws URISyntaxException {
    Hadoop instance = new Hadoop(ClientContext.with(url));
    instance.setHeaders(headers);
    return instance;
  }

  public static Hadoop login( String url, String username, String password ) throws URISyntaxException {
    return new Hadoop(ClientContext.with(username, password, url));
  }

  public static Hadoop loginInsecure(String url, String username, String password) throws URISyntaxException {
    return new  Hadoop(ClientContext.with(username, password, url)
            .connection().secure(false).end());
  }
}
