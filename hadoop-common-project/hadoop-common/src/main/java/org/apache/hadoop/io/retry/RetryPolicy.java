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
package org.apache.hadoop.io.retry;

import org.apache.hadoop.classification.InterfaceStability;


/**
 * <p>
 * Specifies a policy for retrying method failures.
 * Implementations of this interface should be immutable.
 * </p>
 */
@InterfaceStability.Evolving
public interface RetryPolicy {
  
  /**
   * Returned by {@link RetryPolicy#shouldRetry(Exception, int, int, boolean)}.
   */
  @InterfaceStability.Evolving
  public enum RetryAction {
    FAIL,
    RETRY,
    FAILOVER_AND_RETRY
  }
  
  /**
   * <p>
   * Determines whether the framework should retry a
   * method for the given exception, and the number
   * of retries that have been made for that operation
   * so far.
   * </p>
   * @param e The exception that caused the method to fail
   * @param retries The number of times the method has been retried
   * @param failovers The number of times the method has failed over to a
   *   different backend implementation
   * @param isMethodIdempotent <code>true</code> if the method is idempotent
   *   and so can reasonably be retried on failover when we don't know if the
   *   previous attempt reached the server or not
   * @return <code>true</code> if the method should be retried,
   *   <code>false</code> if the method should not be retried
   *   but shouldn't fail with an exception (only for void methods)
   * @throws Exception The re-thrown exception <code>e</code> indicating
   *   that the method failed and should not be retried further
   */
  public RetryAction shouldRetry(Exception e, int retries, int failovers,
      boolean isMethodIdempotent) throws Exception;
}
